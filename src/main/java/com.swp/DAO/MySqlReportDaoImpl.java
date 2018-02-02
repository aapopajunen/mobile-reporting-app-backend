package com.swp.DAO;

import com.swp.Entity.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Qualifier("mysql")
public class MySqlReportDaoImpl implements ReportDAO {

    //JDBCTEMPATE
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //ROW MAPPERS

    public class ReportRowMapper implements RowMapper<Report> {
        @Override
        public Report mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Report(
                    resultSet.getInt("id"),
                    resultSet.getInt("templateId"),
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
                    resultSet.getInt("templateId"),
                    resultSet.getString("title"),
                    resultSet.getString("defaultValue"),
                    resultSet.getInt("typeID"),
                    resultSet.getInt("orderNumber"),
                    resultSet.getInt("required")
            );
        }
    }

    public class TemplateRowMapper implements RowMapper<Template> {
        @Override
        public Template mapRow(ResultSet resultSet, int i) throws SQLException {
            final String amountOdReportsSql = "SELECT COUNT(*) FROM Reports WHERE templateid = ?";

            return new Template(
                   resultSet.getInt("id"),
                   resultSet.getString("title"),
                    resultSet.getInt("reportCount"),
                    jdbcTemplate.queryForObject(amountOdReportsSql, Integer.class, resultSet.getInt("id"))
            );
        }
    }

    public class FieldAnswerRowMapper implements RowMapper<FieldAnswer> {
        @Override
        public FieldAnswer mapRow(ResultSet resultSet, int i) throws SQLException {
            return new FieldAnswer(
                    resultSet.getInt("fieldId"),
                    resultSet.getInt("reportId"),
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
                    resultSet.getString("username"),
                    resultSet.getInt("templateId")
            );
        }
    }

    //METHODS
    private Collection<FieldAnswer> getAnswersByReportId(int reportId) {
        final String sql = "SELECT * FROM FieldAnswers WHERE reportID = ?";

        Collection<FieldAnswer> fieldAnswers = jdbcTemplate.query(sql, new FieldAnswerRowMapper(),reportId);

        return fieldAnswers;
    }

    public void deleteReportById(int reportId) {
        final String reportDeleteSql = "DELETE FROM Reports WHERE id = ?";

        final String answersDeleteSql = "DELETE FROM FieldAnswers WHERE reportID = ?";

        jdbcTemplate.update(answersDeleteSql, reportId);
        jdbcTemplate.update(reportDeleteSql, reportId);
    }

    public void acceptReportById(int reportId) {
        final String sql = "UPDATE Reports SET dateAccepted = CURRENT_DATE () WHERE ID = ?";

        jdbcTemplate.update(sql, reportId);
    }


    @Override
    public User checkLoginCredentials(LoginCredentials loginCredentials) {
        final String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try {
            final User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(),loginCredentials.getUsername(), loginCredentials.getPassword());
            return user;
        } catch(Exception e) {
            throw new BadCredentialsException("Bad credentials.");
        }
    }



    // POST /users/{username}
    @Override
    public void createUser(String username) {
        final String sql = "INSERT INTO Users (username) VALUES (?)";

        jdbcTemplate.update(sql, username);
    }

    // GET /users/{userid}
    @Override
    public User getUserById(int id) {
        final String sql = "SELECT * FROM Users WHERE id = ?";

        User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);

        return user;
    }

    // DELETE /users/{userid}
    @Override
    public void deleteUserById(int id) {
        final String sql = "DELETE FROM Users WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }


    // DELETE /users{userid}
    @Override
    public void deleteUserByUsername(String username) {
        final String sql = "DELETE FROM Users WHERE username = ?";

        jdbcTemplate.update(sql, username);
    }

    // GET /users/{username}/rights
    @Override
    public Collection<AccessRights> getUserAccessRights(String username) {
        final String sql = "SELECT * FROM AccessRights WHERE username = ?";

        Collection<AccessRights> accessRights = jdbcTemplate.query(sql, new AccessRightsRowMapper(), username);

        return accessRights;
    }

    // POST /users/{username}/rights
    @Override
    public void grantUserAccessRights(String username, Map<String, String> params) {
        final String sql = "INSERT INTO AccessRights (username, templateID) VALUES (?,?)";

        if(params.containsKey("templateid")) {
            jdbcTemplate.update(sql, username, params.get("templateid"));
        }
    }

    // DELETE /users/{username}/rights
    @Override
    public void deleteUserAccessRights(String username, Map<String, String> params) {
        final String sql = "DELETE FROM AccessRights WHERE username = ? AND templateid = ?";

        if(params.containsKey("templateid")) {
            jdbcTemplate.update(sql, username, params.get("templateid"));
        }
    }


    // GET /users/{username}/reports
    @Override
    public Collection<Report> getReportsByUser(String username, Map<String, String> params) {

        final String sql = new SQLBuilder("SELECT * FROM Reports WHERE username = ?", params)
                .sqlSearch()
                .sqlSort()
                .sqlPagination()
                .getValue();

        Collection<Report> reports = jdbcTemplate.query(sql, new ReportRowMapper(), username);

        return reports;
    }

    // GET /users/{username}/reports/{reportId}
    @Override
    public Report getUserReportById(String username, int reportId, Map<String, String> params) {
        final String sql = "SELECT * FROM Reports AS R JOIN Users AS U ON U.username = R.username AND U.username = ? AND R.ID = ?";
        Report report = jdbcTemplate.queryForObject(sql, new ReportRowMapper(), username, reportId);
        return report;
    }

    // GET /users/{username}/reports/{reportid}/fields
    @Override
    public Collection<Field> getUserReportFieldsById(String username, int reportId) {
        final String templateIdSql= "SELECT templateID FROM Reports WHERE username = ? AND ID = ?";

        int templateId = jdbcTemplate.queryForObject(templateIdSql, Integer.class, username, reportId);

        final String sql = "SELECT * FROM Fields WHERE templateID = ?";

        Collection<Field> fields = jdbcTemplate.query(sql, new FieldRowMapper(), templateId);

        return fields;
    }

    // GET /users/{username}/reports/{reportId}/answers
    @Override
    public Collection<FieldAnswer> getUserAnswersByReportId(String username, int reportId) {
        final String reportIdSql = "SELECT ID FROM Reports WHERE username = ? AND ID = ?";

        int reportID = jdbcTemplate.queryForObject(reportIdSql, Integer.class, username, reportId);

        final String sql = "SELECT * FROM FieldAnswers WHERE reportID = ?";

        Collection<FieldAnswer> answers = jdbcTemplate.query(sql, new FieldAnswerRowMapper(), reportID);
        return answers;
    }

    // POST /users/{username}/reports
    @Override
    public void createReport(String username, Report report) {
        final String reportSql = "INSERT INTO Reports (templateId, username, orderNo, title, dateCreated) VALUES (?,?,?,?,?)";

        final String orderNoSql = "SELECT reportCount FROM Templates WHERE id = ?";

        final String reportIdSql = "SELECT LAST_INSERT_ID()";

        final int orderNo = jdbcTemplate.queryForObject(orderNoSql, Integer.class, report.getTemplateID()) + 1;
        jdbcTemplate.update(reportSql, report.getTemplateID(), username, orderNo, report.getTitle(), report.getDateCreated());

        final int reportId = jdbcTemplate.queryForObject(reportIdSql, Integer.class);

        report.getAnswers().forEach(
                (ans) -> {
                    final String answerSql = "INSERT INTO FieldAnswers (fieldId, reportId, answer) VALUES (?,?,?)";
                    jdbcTemplate.update(answerSql, ans.getFieldID(), reportId, ans.getAnswer());
                }
        );
    }

    // /users/{username}/templates
    @Override
    public Collection<Template> getTemplatesByUser(String username, Map<String, String> params) {

        final String sql = new SQLBuilder("SELECT * FROM Templates WHERE id IN (:ids)", params)
                .sqlSearch()
                .sqlPagination()
                .sqlSort()
                .getValue();

        final Collection<AccessRights> accessRights = this.getUserAccessRights(username);

        Set<Integer> ids = new HashSet();
        accessRights.forEach((ar) -> ids.add(ar.getTemplateID()));

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);

        Collection<Template> templates;

        try {
             templates = namedParameterJdbcTemplate.query(sql, parameters, new TemplateRowMapper());
        } catch(Exception e) {
            return null;
        }

        return templates;
    }


    // GET /users/{username}/templates/{templateId}
    @Override
    public Template getUserTemplateById(String username, int templateId) {
        final String sql = "SELECT * FROM Templates JOIN AccessRights AS A ON ID = templateID AND A.username = ? AND templateID = ?";
        Template template = jdbcTemplate.queryForObject(sql, new TemplateRowMapper(), username, templateId);
        return template;
    }

    // GET /users/{username}/templates/{templateId}/fields
    @Override
    public Collection<Field> getUserTemplateFieldsById(String username, int templateId) {
        final String templateIdSql = "SELECT templateID FROM AccessRights WHERE username = ? AND templateID = ?";

        int templateID = jdbcTemplate.queryForObject(templateIdSql, Integer.class, username, templateId);

        final String sql = "SELECT * FROM Fields WHERE templateID = ?";

        Collection<Field> fields = jdbcTemplate.query(sql, new FieldRowMapper(), templateID);

        return fields;
    }

    // GET /users/{username}/templates/{templateId}/reports
    @Override
    public Collection<Report> getUserReportsByTemplateId(String username, int templateId, Map<String, String> params) {
        final String sql = new SQLBuilder("SELECT * FROM Reports WHERE username = ? and templateID = ?", params)
                .sqlSearch()
                .sqlPagination()
                .sqlSort()
                .getValue();

        Collection<Report> reports = jdbcTemplate.query(sql, new ReportRowMapper(), username, templateId);
        return reports;
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

