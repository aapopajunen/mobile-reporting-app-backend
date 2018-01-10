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
public class MySqlReportDaoImpl implements ReportDAO {

    //JDBCTEMPATE
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //ROW MAPPERS

    public class FormRowMapper implements RowMapper<Report> {
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

    public class LayoutRowMapper implements RowMapper<Template> {
        @Override
        public Template mapRow(ResultSet resultSet, int i) throws SQLException {
            final String sql = "SELECT COUNT(*) FROM Reports WHERE templateid = ?";

            return new Template(
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
                    resultSet.getInt("templateId")
            );
        }
    }

    //METHODS
    @Override
    public Collection<Report> getReports(Map<String, String> params) {
        //String sql = "SELECT * FROM Reports" + this.paramsToSqlString(params);

        String sql = new SQLBuilder("SELECT * FROM Reports", params)
                .sqlWhere(new String[]{"templateid", "userid"})
                .sqlSearch()
                .sqlSort()
                .sqlPagination()
                .getValue();

        Collection<Report> reports = jdbcTemplate.query(sql, new FormRowMapper());
        return reports;
    }

    @Override
    public Report getReportsById(int reportId) {
        final String sql = "SELECT * FROM Reports WHERE id = ?";

        Report report = jdbcTemplate.queryForObject(sql, new FormRowMapper(), reportId);
        return report;
    }

    @Override
    public void deleteReportById(int reportId) {
        final String reportDeleteSql = "DELETE FROM Reports WHERE id = ?";

        final String answersDeleteSql = "DELETE FROM FieldAnswers WHERE reportID = ?";

        jdbcTemplate.update(answersDeleteSql, reportId);
        jdbcTemplate.update(reportDeleteSql, reportId);
    }

    @Override
    public void acceptReportById(int reportId) {
        final String sql = "UPDATE Reports SET dateAccepted = CURRENT_DATE () WHERE ID = ?";

        jdbcTemplate.update(sql, reportId);
    }

    @Override
    public Collection<Field> getFieldsByReportId(int reportId) {
        final String templateIdSql = "SELECT templateid FROM Reports WHERE id = ?";

        int templateId = jdbcTemplate.queryForObject(templateIdSql, Integer.class, reportId);

        final String sql = "SELECT * FROM Fields WHERE templateid = ? ORDER BY orderNumber";

        Collection<Field> fields = jdbcTemplate.query(sql, new FieldRowMapper(), templateId);

        return fields;
    }

    @Override
    public Collection<FieldAnswer> getAnswersByReportId(int reportId) {
        final String sql = "SELECT * FROM FieldAnswers WHERE reportID = ?";

        Collection<FieldAnswer> answers = jdbcTemplate.query(sql, new FieldAnswerRowMapper(), reportId);

        return answers;
    }

    @Override
    public Collection<Template> getTemplates(Map<String, String> params) {
        final String sql = new SQLBuilder("SELECT * FROM Templates", params)
                .sqlSearch()
                .sqlSort()
                .sqlPagination()
                .getValue();

        Collection<Template> templates = jdbcTemplate.query(sql, new LayoutRowMapper());

        return templates;
    }

    @Override
    public Template getTemplateById(int templateid) {
        final String sql = "SELECT * FROM Templates WHERE id = ?";

        Template template = jdbcTemplate.queryForObject(sql, new LayoutRowMapper(), templateid);
        return template;
    }

    @Override
    public Collection<Field> getFieldsByTemplateId(int templateId) {
        final String sql = "SELECT * FROM Fields WHERE templateid = ?";

        Collection<Field> fields = jdbcTemplate.query(sql, new FieldRowMapper(), templateId);

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
        final String sql = "INSERT INTO AccessRights (userID, templateID) VALUES (?,?)";

        if(params.containsKey("templateid")) {
            jdbcTemplate.update(sql, id, params.get("templateid"));
        }
    }

    @Override
    public void deleteUserAccessRights(int id, Map<String, String> params) {
        final String sql = "DELETE FROM AccessRights WHERE userid = ? AND templateid = ?";

        if(params.containsKey("templateid")) {
            jdbcTemplate.update(sql, id, params.get("templateid"));
        }
    }

    @Override
    public Collection<Report> getReportsByUser(int id, Map<String, String> params) {

        final String sql = new SQLBuilder("SELECT * FROM Reports WHERE userId = ?", params)
                .sqlWhere(new String[]{"templateid"})
                .sqlSearch()
                .sqlPagination()
                .sqlSort()
                .getValue();

        Collection<Report> reports = jdbcTemplate.query(sql, new FormRowMapper(), id);

        return reports;
    }

    @Override
    public void createReport(int id, Report report) {
        final String reportSql = "INSERT INTO Reports (templateId, userId, orderNo, title, dateCreated) VALUES (?,?,?,?,?)";
        //TODO fix this somehow!
        final String orderNoSql = "SELECT reportCount FROM ReportCounts WHERE templateId = ?";
        final String reportIdSql = "SELECT LAST_INSERT_ID()";

        final int orderNo = jdbcTemplate.queryForObject(orderNoSql, Integer.class, report.getTemplateID()) + 1;
        jdbcTemplate.update(reportSql, report.getTemplateID(), id, orderNo, report.getTitle(), report.getDateCreated());

        final int reportId = jdbcTemplate.queryForObject(reportIdSql, Integer.class);

        report.getAnswers().forEach(
                (ans) -> {
                    final String answerSql = "INSERT INTO FieldAnswers (fieldId, reportId, answer) VALUES (?,?,?)";
                    jdbcTemplate.update(answerSql, ans.getFieldID(), reportId, ans.getAnswer());
                }
        );
    }

    @Override
    public Collection<Template> getTemplatesByUser(int id, Map<String, String> params) {
        //TODO Add parameter support

        final String sql = "SELECT * FROM Templates WHERE id IN (:ids)";

        final Collection<AccessRights> accessRights = this.getUserAccessRights(id);

        Set<Integer> ids = new HashSet();
        accessRights.forEach((ar) -> ids.add(ar.getTemplateID()));

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);

        Collection<Template> templates;

        try {
             templates = namedParameterJdbcTemplate.query(sql, parameters, new LayoutRowMapper());
        } catch(Exception e) {
            return null;
        }

        return templates;
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

        if(params.containsKey("templateid")) {
            sql = " WHERE templateid = " + params.get("templateid");
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

