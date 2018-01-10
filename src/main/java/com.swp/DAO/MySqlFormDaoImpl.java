package com.swp.DAO;

import com.swp.Entity.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Qualifier("mysql")
public class MySqlFormDaoImpl implements FormDAO {

    //JDBCTEMPATE
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //ROW MAPPERS

    public class FormRowMapper implements RowMapper<Form> {
        @Override
        public Form mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Form(
                    resultSet.getInt("id"),
                    resultSet.getInt("layoutId"),
                    resultSet.getInt("userId"),
                    resultSet.getInt("orderNo"),
                    resultSet.getString("title"),
                    resultSet.getDate("dateCreated"),
                    resultSet.getDate("dateAccepted"),
                    getAnswersByReportId(resultSet.getInt("id"))
            );
        }
    }

    public class FieldRowMapper implements RowMapper<Field> {
        @Override
        public Field mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Field(
                    resultSet.getInt("id"),
                    resultSet.getInt("layoutId"),
                    resultSet.getString("title"),
                    resultSet.getString("defaultValue"),
                    resultSet.getInt("typeID"),
                    resultSet.getInt("orderNumber"),
                    resultSet.getInt("required")
            );
        }
    }

    public class LayoutRowMapper implements RowMapper<Layout> {
        @Override
        public Layout mapRow(ResultSet resultSet, int i) throws SQLException {
            final String sql = "SELECT COUNT(*) FROM Forms WHERE layoutid = ?";

            return new Layout(
                   resultSet.getInt("id"),
                   resultSet.getString("title"),
                    jdbcTemplate.queryForObject(sql, Integer.class, resultSet.getInt("id"))
            );
        }
    }

    public class FieldAnswerRowMapper implements RowMapper<FieldAnswer> {
        @Override
        public FieldAnswer mapRow(ResultSet resultSet, int i) throws SQLException {
            return new FieldAnswer(
                    resultSet.getInt("fieldId"),
                    resultSet.getInt("formId"),
                    resultSet.getString("answer")
            );
        }
    }

    public class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User(
                    resultSet.getInt("id"),
                    resultSet.getString("username")
            );
        }
    }

    public class AccessRightsRowMapper implements RowMapper<AccessRights> {
        @Override
        public AccessRights mapRow(ResultSet resultSet, int i) throws SQLException {
            return new AccessRights(
                    resultSet.getInt("userId"),
                    resultSet.getInt("layoutId")
            );
        }
    }

    //METHODS
    @Override
    public Collection<Form> getReports(Map<String, String> params) {
        //String sql = "SELECT * FROM Forms" + this.paramsToSqlString(params);

        String sql = new SQLBuilder("SELECT * FROM Forms", params)
                .sqlWhere(new String[]{"layoutid", "userid"})
                .sqlSearch()
                .sqlSort()
                .sqlPagination()
                .getValue();

        Collection<Form> forms = jdbcTemplate.query(sql, new FormRowMapper());
        return forms;
    }

    @Override
    public Form getReportsById(int formId) {
        final String sql = "SELECT * FROM Forms WHERE id = ?";

        Form form = jdbcTemplate.queryForObject(sql, new FormRowMapper(), formId);
        return form;
    }

    @Override
    public void deleteReportById(int formId) {
        final String formDeleteSql = "DELETE FROM Forms WHERE id = ?";

        final String answersDeleteSql = "DELETE FROM FieldAnswers WHERE formID = ?";

        jdbcTemplate.update(answersDeleteSql, formId);
        jdbcTemplate.update(formDeleteSql, formId);
    }

    @Override
    public void acceptReportById(int formId) {
        final String sql = "UPDATE Forms SET dateAccepted = CURRENT_DATE () WHERE ID = ?";

        jdbcTemplate.update(sql, formId);
    }

    @Override
    public Collection<Field> getFieldsByReportId(int formId) {
        final String layoutIdSql = "SELECT layoutid FROM Forms WHERE id = ?";

        int layoutId = jdbcTemplate.queryForObject(layoutIdSql, Integer.class, formId);

        final String sql = "SELECT * FROM Fields WHERE layoutid = ? ORDER BY orderNumber";

        Collection<Field> fields = jdbcTemplate.query(sql, new FieldRowMapper(), layoutId);

        return fields;
    }

    @Override
    public Collection<FieldAnswer> getAnswersByReportId(int formId) {
        final String sql = "SELECT * FROM FieldAnswers WHERE formID = ?";

        Collection<FieldAnswer> answers = jdbcTemplate.query(sql, new FieldAnswerRowMapper(), formId);

        return answers;
    }

    @Override
    public Collection<Layout> getTemplates(Map<String, String> params) {
        final String sql = new SQLBuilder("SELECT * FROM Layouts", params)
                .sqlSearch()
                .sqlSort()
                .sqlPagination()
                .getValue();

        Collection<Layout> layouts = jdbcTemplate.query(sql, new LayoutRowMapper());

        return layouts;
    }

    @Override
    public Layout getTemplateById(int layoutid) {
        final String sql = "SELECT * FROM Layouts WHERE id = ?";

        Layout layout = jdbcTemplate.queryForObject(sql, new LayoutRowMapper(), layoutid);
        return layout;
    }

    @Override
    public Collection<Field> getFieldsByTemplateId(int layoutId) {
        final String sql = "SELECT * FROM Fields WHERE layoutid = ?";

        Collection<Field> fields = jdbcTemplate.query(sql, new FieldRowMapper(), layoutId);

        return fields;
    }

    @Override
    public Collection<User> getAllUsers() {
        final String sql = "SELECT * FROM Users";

        Collection<User> users = jdbcTemplate.query(sql, new UserRowMapper());

        return users;
    }

    @Override
    public void createUser(String username) {
        final String sql = "INSERT INTO Users (username) VALUES (?)";

        jdbcTemplate.update(sql, username);
    }

    @Override
    public User getUserById(int id) {
        final String sql = "SELECT * FROM Users WHERE id = ?";

        User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);

        return user;
    }

    @Override
    public void deleteUserById(int id) {
        final String sql = "DELETE FROM Users WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        final String sql = "DELETE FROM Users WHERE username = ?";

        jdbcTemplate.update(sql, username);
    }

    @Override
    public Collection<AccessRights> getUserAccessRights(int id) {
        final String sql = "SELECT * FROM AccessRights WHERE userid = ?";

        Collection<AccessRights> accessRights = jdbcTemplate.query(sql, new AccessRightsRowMapper(), id);

        return accessRights;
    }

    @Override
    public void grantUserAccessRights(int id, Map<String, String> params) {
        final String sql = "INSERT INTO AccessRights (userID, layoutID) VALUES (?,?)";

        if(params.containsKey("layoutid")) {
            jdbcTemplate.update(sql, id, params.get("layoutid"));
        }
    }

    @Override
    public void deleteUserAccessRights(int id, Map<String, String> params) {
        final String sql = "DELETE FROM AccessRights WHERE userid = ? AND layoutid = ?";

        if(params.containsKey("layoutid")) {
            jdbcTemplate.update(sql, id, params.get("layoutid"));
        }
    }

    @Override
    public Collection<Form> getReportsByUser(int id, Map<String, String> params) {

        final String sql = new SQLBuilder("SELECT * FROM Forms WHERE userId = ?", params)
                .sqlWhere(new String[]{"layoutid"})
                .sqlSearch()
                .sqlPagination()
                .sqlSort()
                .getValue();

        Collection<Form> forms = jdbcTemplate.query(sql, new FormRowMapper(), id);

        return forms;
    }

    @Override
    public void createReport(int id, Form form) {
        final String formSql = "INSERT INTO Forms (layoutId, userId, orderNo, title, dateCreated) VALUES (?,?,?,?,?)";
        //TODO fix this somehow!
        final String orderNoSql = "SELECT formCount FROM FormCounts WHERE layoutId = ?";
        final String formIdSql = "SELECT LAST_INSERT_ID()";

        final int orderNo = jdbcTemplate.queryForObject(orderNoSql, Integer.class, form.getLayoutID()) + 1;
        jdbcTemplate.update(formSql, form.getLayoutID(), id, orderNo, form.getTitle(), form.getDateCreated());

        final int formId = jdbcTemplate.queryForObject(formIdSql, Integer.class);

        form.getAnswers().forEach(
                (ans) -> {
                    final String answerSql = "INSERT INTO FieldAnswers (fieldId, formId, answer) VALUES (?,?,?)";
                    jdbcTemplate.update(answerSql, ans.getFieldID(), formId, ans.getAnswer());
                }
        );
    }

    @Override
    public Collection<Layout> getTemplatesByUser(int id, Map<String, String> params) {
        //TODO Add parameter support

        final String sql = "SELECT * FROM Layouts WHERE id IN (:ids)";

        final Collection<AccessRights> accessRights = this.getUserAccessRights(id);

        Set<Integer> ids = new HashSet();
        accessRights.forEach((ar) -> ids.add(ar.getLayoutID()));

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);

        Collection<Layout> layouts;

        try {
             layouts = namedParameterJdbcTemplate.query(sql, parameters, new LayoutRowMapper());
        } catch(Exception e) {
            return null;
        }

        return layouts;
    }

    public int getUserIdByUsername(int id) {
        final String sql = "SELECT id FROM Users WHERE id = ?";

        int userId = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return userId;
    }

    public String paramsToSqlString(Map<String, String> params) {

        String sql = "";
        int pageSize = 20;

        if(params.containsKey("pagesize")) {
            pageSize = Integer.valueOf(params.get("pagesize"));
        }

        if(params.containsKey("layoutid")) {
            sql = " WHERE layoutid = " + params.get("layoutid");
        }

        if(params.containsKey("sort")) {
            sql = sql + " ORDER BY " + "(" + params.get("sort") + ")";
        }

        if(params.containsKey("page")) {
            String[] pages = params.get("page").split(",");

            if(pages.length == 2){
                int startPage = Integer.parseInt(pages[0]) - 1;
                int endPage = Integer.parseInt(pages[1]);
                sql = sql + " LIMIT " + startPage*pageSize + "," + (endPage - startPage)*pageSize;
            }

            if(pages.length == 1){
                int page = Integer.parseInt(pages[0]) - 1;
                sql = sql + " LIMIT " + page*pageSize + "," + pageSize;
            }
        }


        return sql;
    }

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
                this.setValue(this.getValue() + " ORDER BY " + "(" + params.get("sort") + ")");
            }

            return this;
        }



    }
}

