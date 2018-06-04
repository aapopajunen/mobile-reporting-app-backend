package com.swp.DAO;

import com.mysql.cj.api.jdbc.Statement;
import com.swp.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Qualifier("mysqlnew")
public class MySqlFormDaoImpl implements FormDAO {

    //JDBCTEMPATE
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //ROW MAPPERS
    public class FormRowMapper implements RowMapper<Form> {
        @Override
        public Form mapRow(ResultSet resultSet, int i) throws SQLException {
            Form form = new Form(
                    resultSet.getInt("form_id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("template_id"),
                    resultSet.getString("title"),
                    resultSet.getDate("date_created"),
                    resultSet.getDate("date_accepted")
            );

            final String stringAnswerSql = "SELECT * FROM string_answer WHERE form_id = ?";
            Collection<StringAnswer> stringAnswers = jdbcTemplate.query(stringAnswerSql, new StringAnswerRowMapper(), form.getForm_id());

            final String optionAnswerSql = "SELECT * FROM option_answer WHERE form_id = ?";
            Collection<OptionAnswer> optionAnswers = jdbcTemplate.query(optionAnswerSql, new OptionAnswerRowMapper(), form.getForm_id());

            form.setString_answers(stringAnswers);
            form.setOption_answers(optionAnswers);

            return form;
        }
    }

    public class StringAnswerRowMapper implements RowMapper<StringAnswer> {
        @Override
        public StringAnswer mapRow(ResultSet resultSet, int i) throws SQLException {
            return new StringAnswer(
                    resultSet.getInt("string_answer_id"),
                    resultSet.getInt("form_id"),
                    resultSet.getInt("field_id"),
                    resultSet.getString("value")
            );
        }
    }

    public class OptionAnswerRowMapper implements RowMapper<OptionAnswer> {
        @Override
        public OptionAnswer mapRow(ResultSet resultSet, int i) throws SQLException {
            OptionAnswer optionAnswer = new OptionAnswer(
                    resultSet.getInt("option_answer_id"),
                    resultSet.getInt("form_id"),
                    resultSet.getInt("field_option_id")
            );

            optionAnswer.setSelected(true);

            return optionAnswer;
        }
    }

    public class FieldRowMapper implements RowMapper<Field> {
        @Override
        public Field mapRow(ResultSet resultSet, int i) throws SQLException {
            Field field = new Field(
                    resultSet.getInt("field_id"),
                    resultSet.getInt("template_id"),
                    resultSet.getInt("order_number"),
                    resultSet.getString("title"),
                    resultSet.getBoolean("required"),
                    FieldType.valueOf(resultSet.getString("type")),
                    resultSet.getString("default_value")
            );

            final String fieldOptionSql = "SELECT * FROM field_option WHERE field_id = ?";
            Collection<FieldOption> fieldOptions = jdbcTemplate.query(fieldOptionSql, new FieldOptionRowMapper(), field.getField_id());

            if(!fieldOptions.isEmpty()) {
                field.setField_options(fieldOptions);
            }

            return field;
        }
    }

    public class FieldOptionRowMapper implements RowMapper<FieldOption> {
        @Override
        public FieldOption mapRow(ResultSet resultSet, int i) throws SQLException {
            return new FieldOption(
                    resultSet.getInt("field_option_id"),
                    resultSet.getInt("field_id"),
                    resultSet.getString("value"),
                    resultSet.getBoolean("default_value")
            );
        }
    }

    public class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("username")
            );
        }
    }

    public class TemplateRowMapper implements RowMapper<Template> {
        @Override
        public Template mapRow(ResultSet resultSet, int i) throws SQLException {
            Template template = new Template(
                   resultSet.getInt("template_id"),
                   resultSet.getString("title")
            );

            final String fieldsSql = "SELECT * FROM field WHERE template_id = ?";
            Collection<Field> fields = jdbcTemplate.query(fieldsSql, new FieldRowMapper(), template.getTemplate_id());

            template.setFields(fields);

            return template;
        }
    }

    //METHODS

    @Override
    public User checkLoginCredentials(LoginCredentials loginCredentials) {
        final String sql = "SELECT * FROM user WHERE BINARY username = ? AND BINARY password = ?";

        try {
            final User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(),loginCredentials.getUsername(), loginCredentials.getPassword());
            return user;
        } catch(Exception e) {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        final String sql = "SELECT user_id, username FROM user ORDER BY username";

        Collection<User> users = jdbcTemplate.query(sql, new UserRowMapper());

        return users;
    }

    // GET /users/{username}/forms
    @Override
    public Collection<Form> getFormsByUser(String username, Map<String, String> params) {
        final String sql = new SQLBuilder("SELECT form.* FROM form INNER JOIN user ON form.user_id = user.user_id " +
                "AND user.username = ?", params)
                .sqlSearch()
                .sqlSort()
                .sqlPagination()
                .value;

        Collection<Form> forms = jdbcTemplate.query(sql, new FormRowMapper(), username);

        return forms;
    }

    // GET /users/{username}/forms/{formId}
    @Override
    public Form getUserFormById(String username, int formId, Map<String, String> params) {
        final String sql = "SELECT r.* FROM form AS r INNER JOIN user AS u ON " +
                "r.user_id = u.user_id AND u.username = username AND r.form_id = ?";
        Form form = jdbcTemplate.queryForObject(sql, new FormRowMapper(), username, formId);
        return form;
    }

    // POST /users/{username}/forms
    // A BEAST OF A FUNCTION
    @Override
    public void createForm(String username, Form form) {
        final String insertFormSql =
                "INSERT INTO form (user_id, template_id, title, date_created) " +
                "VALUES (?,?,?,?);";

        final String insertStringAnswersSql =
                "INSERT INTO string_answer (form_id, field_id, value) " +
                "VALUES (?,?,?)";

        final String insertOptionAnswersSql =
                "INSERT INTO option_answer (form_id, field_option_id) " +
                "VALUES (?,?)";


        // INSERT FORM AND RETRIEVE ITS ID
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public java.sql.PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final java.sql.PreparedStatement ps =
                        connection.prepareStatement(insertFormSql,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, form.getUser_id());
                ps.setInt(2, form.getTemplate_id());
                ps.setString(3, form.getTitle());
                ps.setDate(4, form.getDate_created());
                return ps;
            }
        }, key);


        // INSERT STRING ANSWERS
        for(StringAnswer sa : form.getString_answers()) {
            jdbcTemplate.update(
                    insertStringAnswersSql,
                    key.getKey().intValue(),
                    sa.getField_id(),
                    sa.getValue()
            );
        }

        //INSERT OPTION ANSWERS
        for(OptionAnswer oa : form.getOption_answers()) {
            if(oa.isSelected()) {
                jdbcTemplate.update(
                        insertOptionAnswersSql,
                        key.getKey().intValue(),
                        oa.getField_option_id()
                );
            }
        }
    }

    // /users/{username}/templates
    @Override
    public Collection<Template> getTemplatesByUser(String username, Map<String, String> params) {
        final String sql =
                "SELECT t.* FROM template AS t " +
                "JOIN access_rights AS ar " +
                "JOIN user AS u " +
                "ON t.template_id = ar.template_id AND u.user_id = ar.user_id AND u.username = ?";

        Collection<Template> templates = jdbcTemplate.query(sql, new TemplateRowMapper(), username);

        return templates;
    }

    // GET /users/{username}/templates/{templateId}
    @Override
    public Template getUserTemplateById(String username, int templateId) {
        final String sql =
                "SELECT t.* FROM template AS t " +
                "JOIN access_rights AS ar " +
                "JOIN user AS u " +
                "ON t.template_id = ar.template_id AND u.user_id = ar.user_id AND u.username = ? AND t.template_id = ?";

        Template template = jdbcTemplate.queryForObject(sql, new TemplateRowMapper(), username, templateId);
        return template;
    }

    // GET /users/{username}/templates/{templateId}/empty
    @Override
    public Form getEmptyTemplate(String username, int templateId) {
        int userId = jdbcTemplate.queryForObject("SELECT user_id FROM user WHERE username = ?", Integer.class, username);

        Template template = this.getUserTemplateById(username, templateId);

        Form form = new Form();

        form.setTemplate_id(template.getTemplate_id());
        form.setUser_id(userId);

        List<StringAnswer> stringAnswers = new ArrayList<>();
        List<OptionAnswer> optionAnswers = new ArrayList<>();

        for(Field f : template.getFields()) {
            if(f.getType().hasNoOptions()) {
                StringAnswer stringAnswer = new StringAnswer();
                stringAnswer.setField_id(f.getField_id());
                stringAnswer.setValue(f.getDefault_value());
                stringAnswers.add(stringAnswer);
            } else {
                for(FieldOption fo : f.getField_options()){
                    OptionAnswer optionAnswer = new OptionAnswer();
                    optionAnswer.setField_option_id(fo.getField_option_id());
                    optionAnswer.setSelected(fo.getDefault_value());
                    optionAnswers.add(optionAnswer);
                }
            }
        }
        form.setString_answers(stringAnswers);
        form.setOption_answers(optionAnswers);

        return form;
    }

    // GET /users/{username}/templates/{templateId}/forms
    @Override
    public Collection<Form> getUserFormsByTemplateId(String username, int templateId, Map<String, String> params) {
        final String sql = new SQLBuilder(
                "SELECT r.* FROM form AS r JOIN user AS u " +
                "ON r.user_id = u.user_id AND u.username = ? AND r.template_id = ?", params)
                .sqlSearch()
                .sqlSort()
                .sqlPagination()
                .value;

        Collection<Form> forms = jdbcTemplate.query(sql, new FormRowMapper(), username, templateId);
        return forms;
    }

    // Class For Building SQL Querys fast!
    public class SQLBuilder {

        private String value;
        private Map<String, String> params;

        public SQLBuilder(String value, Map<String, String> params) {
            this.value = value;
            this.params = params;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


        //WHERE, SEARCH, SORT, PAGE

        public SQLBuilder sqlWhere(String[] columns) {
            List<String> temp = new ArrayList<String>();

            for(String column : columns) {
                if(params.containsKey(column)) {
                    temp.add(column + " = " + params.get(column));
                }
            }

            if(!temp.isEmpty()) {
                this.setValue(this.getValue() + " WHERE " + String.join(" AND " ,temp));
            }

            return this;
        }

        public SQLBuilder sqlPagination() {
            int pageSize = 20;

            if(params.containsKey("pagesize")) {
                pageSize = Integer.valueOf(params.get("pagesize"));
            }

            if(params.containsKey("page")) {
                String[] pages = params.get("page").split(",");

                if(pages.length == 2){
                    int startPage = Integer.parseInt(pages[0]) - 1;
                    int endPage = Integer.parseInt(pages[1]);
                    this.setValue(this.getValue() + " LIMIT " + startPage*pageSize + "," + (endPage - startPage)*pageSize);
                }

                if(pages.length == 1){
                    int page = Integer.parseInt(pages[0]) - 1;
                    this.setValue(this.getValue() + " LIMIT " + page*pageSize + "," + pageSize);
                }
            }

            return this;
        }

        public SQLBuilder sqlSearch() {
            String[] searchParams = null;

            if(params.containsKey("search")){
                searchParams = params.get("search").split(",");
            }

            if(searchParams != null && searchParams.length == 2){
                this.setValue("SELECT * FROM (" + this.getValue() + ") AS Temp WHERE " + searchParams[0] + " LIKE " + "\"%" + searchParams[1] + "%\"");
            }

            return this;
        }

        public SQLBuilder sqlSort() {
            if(params.containsKey("sort")) {
                this.setValue(this.getValue() + " ORDER BY " + params.get("sort"));
            }

            return this;
        }



    }
}

