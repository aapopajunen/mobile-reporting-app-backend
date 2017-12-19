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

    public static class FormRowMapper implements RowMapper<Form> {
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
                    "/forms/" + resultSet.getInt("id") + "/answers"
            );
        }
    }

    public static class FieldRowMapper implements RowMapper<Field> {
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

    public static class LayoutRowMapper implements RowMapper<Layout> {
        @Override
        public Layout mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Layout(
                   resultSet.getInt("id"),
                   resultSet.getString("title"),
                    "/forms?layoutid=" + resultSet.getInt("id"),
                    "/layouts/" + resultSet.getInt("id") + "/fields"
            );
        }
    }

    public static class FieldAnswerRowMapper implements RowMapper<FieldAnswer> {
        @Override
        public FieldAnswer mapRow(ResultSet resultSet, int i) throws SQLException {
            return new FieldAnswer(
                    resultSet.getInt("fieldId"),
                    resultSet.getInt("formId"),
                    resultSet.getString("answer")
            );
        }
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User(
                    resultSet.getInt("id"),
                    resultSet.getString("username")
            );
        }
    }

    public static class AccessRightsRowMapper implements RowMapper<AccessRights> {
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
    public Collection<Form> getAllForms(Map<String, String> params) {
        String sql = "SELECT * FROM Forms" + this.paramsToSqlString(params);

        Collection<Form> forms = jdbcTemplate.query(sql, new FormRowMapper());
        return forms;
    }

    @Override
    public Form getFormById(int formId) {
        final String sql = "SELECT * FROM Forms WHERE id = ?";

        Form form = jdbcTemplate.queryForObject(sql, new FormRowMapper(), formId);
        return form;
    }

    @Override
    public void deleteFormById(int formId) {
        final String sql = "DELETE FROM Forms WHERE id = ?";

        jdbcTemplate.update(sql, formId);
    }

    @Override
    public Collection<Field> getFieldsByFormId(int formId) {
        final String layoutIdSql = "SELECT layoutid FROM Forms WHERE id = ?";

        int layoutId = jdbcTemplate.queryForObject(layoutIdSql, Integer.class, formId);

        final String sql = "SELECT * FROM Fields WHERE layoutid =" + layoutId + " ORDER BY ordernumber";

        Collection<Field> fields = jdbcTemplate.query(sql, new FieldRowMapper(), layoutId);

        return fields;
    }

    @Override
    public Collection<FieldAnswer> getAnswersByFormId(int formId) {
        final String sql = "SELECT * FROM FieldAnswers WHERE formID = ?";

        Collection<FieldAnswer> answers = jdbcTemplate.query(sql, new FieldAnswerRowMapper(), formId);

        return answers;
    }

    @Override
    public Collection<Layout> getAllLayouts() {
        final String sql = "SELECT * FROM Layouts";

        Collection<Layout> layouts = jdbcTemplate.query(sql, new LayoutRowMapper());

        return layouts;
    }

    @Override
    public Layout getLayoutById(int layoutid) {
        final String sql = "SELECT * FROM Layouts WHERE id = ?";

        Layout layout = jdbcTemplate.queryForObject(sql, new LayoutRowMapper(), layoutid);
        return layout;
    }

    @Override
    public Collection<Field> getFieldsByLayoutId(int layoutId) {
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
    public User getUserByUsername(String username) {
        final String sql = "SELECT * FROM Users WHERE username = ?";

        User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);

        return user;
    }

    @Override
    public void deleteUser(String username) {
        final String sql = "DELETE FROM Users WHERE username = ?";

        jdbcTemplate.update(sql, username);
    }

    @Override
    public Collection<AccessRights> getUserAccessRights(String username) {
        final String sql = "SELECT * FROM AccessRights WHERE userid = ?";

        int userId = this.getUserIdByUsername(username);

        Collection<AccessRights> accessRights = jdbcTemplate.query(sql, new AccessRightsRowMapper(), userId);

        return accessRights;
    }

    @Override
    public void grantUserAccessRights(String username, Map<String, String> params) {
        //TODO Add parameter support

        final String sql = "INSERT INTO AccessRights (userID, layoutID) VALUES (?,?)";

        int userId = this.getUserIdByUsername(username);

        if(params.containsKey("layoutid")) {
            jdbcTemplate.update(sql, userId, params.get("layoutid"));
        }
    }

    @Override
    public void deleteUserAccessRights(String username, Map<String, String> params) {
        //TODO
    }

    @Override
    public Collection<Form> getFormsByUser(String username, Map<String, String> params) {
        //TODO Add parameter support

        final String sql = "SELECT * FROM Forms WHERE userId = ?";

        final int userId = this.getUserIdByUsername(username);

        Collection<Form> forms = jdbcTemplate.query(sql, new FormRowMapper(), userId);

        return forms;
    }

    @Override
    public void createForm(Form form) {
        //TODO
    }

    @Override
    public Collection<Layout> getLayoutsByUser(String username, Map<String, String> params) {
        //TODO Add parameter support

        final String sql = "SELECT * FROM Layouts WHERE id IN (:ids)";

        final Collection<AccessRights> accessRights = this.getUserAccessRights(username);

        Set<Integer> ids = new HashSet();
        accessRights.forEach((ar) -> ids.add(ar.layoutId));

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);

        Collection<Layout> layouts = namedParameterJdbcTemplate.query(sql, parameters, new LayoutRowMapper());

        return layouts;
    }


    public int getUserIdByUsername(String username) {
        final String sql = "SELECT id FROM Users WHERE username = ?";

        int userId = jdbcTemplate.queryForObject(sql, Integer.class, username);

        return userId;
    }

    public String paramsToSqlString(Map<String, String> params) {

        String sql = "";

        for(Map.Entry<String, String> entry : params.entrySet()){
            if(entry.getKey().equals("layoutid")) {
                sql = sql + " WHERE layoutid = " + entry.getValue();
            }
        }

        return sql;
    }
}

