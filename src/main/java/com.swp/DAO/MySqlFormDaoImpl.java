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
                    getAnswersByFormId(resultSet.getInt("id"))
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
                   resultSet.getString("title")
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
        final String formDeleteSql = "DELETE FROM Forms WHERE id = ?";

        final String answersDeleteSql = "DELETE FROM FieldAnswers WHERE formID = ?";

        jdbcTemplate.update(answersDeleteSql, formId);
        jdbcTemplate.update(formDeleteSql, formId);
    }

    @Override
    public void acceptFormById(int formId) {
        final String sql = "UPDATE Forms SET dateAccepted = CURRENT_DATE () WHERE ID = ?";

        jdbcTemplate.update(sql, formId);
    }

    @Override
    public Collection<Field> getFieldsByFormId(int formId) {
        final String layoutIdSql = "SELECT layoutid FROM Forms WHERE id = ?";

        int layoutId = jdbcTemplate.queryForObject(layoutIdSql, Integer.class, formId);

        final String sql = "SELECT * FROM Fields WHERE layoutid = ? ORDER BY orderNumber";

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
    public Collection<Form> getFormsByUser(int id, Map<String, String> params) {
        //TODO Add parameter support

        final String sql = "SELECT * FROM Forms WHERE userId = ?";

        Collection<Form> forms = jdbcTemplate.query(sql, new FormRowMapper(), id);

        return forms;
    }

    @Override
    public void createForm(int id, Form form) {
        final String formSql = "INSERT INTO Forms (layoutId, userId, orderNo, title, dateCreated) VALUES (?,?,?,?,?)";
        //TODO fix this somehow!
        final String orderNoSql = "SELECT MAX(orderNo) FROM Forms WHERE layoutId = ?";
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
    public Collection<Layout> getLayoutsByUser(int id, Map<String, String> params) {
        //TODO Add parameter support

        final String sql = "SELECT * FROM Layouts WHERE id IN (:ids)";

        final Collection<AccessRights> accessRights = this.getUserAccessRights(id);

        Set<Integer> ids = new HashSet();
        accessRights.forEach((ar) -> ids.add(ar.getLayoutID()));

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);

        Collection<Layout> layouts = namedParameterJdbcTemplate.query(sql, parameters, new LayoutRowMapper());

        return layouts;
    }

    public int getUserIdByUsername(int id) {
        final String sql = "SELECT id FROM Users WHERE id = ?";

        int userId = jdbcTemplate.queryForObject(sql, Integer.class, id);

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

