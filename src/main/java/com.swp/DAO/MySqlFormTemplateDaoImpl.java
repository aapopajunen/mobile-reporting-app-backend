package com.swp.DAO;

import com.swp.Entity.FormTemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
@Qualifier("mysql")
public class MySqlFormTemplateDaoImpl implements FormTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static class StudentRowMapper implements RowMapper<FormTemplate> {
        @Override
        public FormTemplate mapRow(ResultSet resultSet, int i) throws SQLException {
            FormTemplate formTemplate = new FormTemplate();
            formTemplate.setId(resultSet.getInt("id"));
            formTemplate.setName(resultSet.getString("name"));
            return formTemplate;
        }
    }

    @Override
    public Collection<FormTemplate> getAllFormTemplates() {
        //SELECT column_name(s) FROM table_name
        final String sql = "SELECT id, name FROM FormTemplates";
        List<FormTemplate> formTemplates = jdbcTemplate.query(sql, new StudentRowMapper());
        return formTemplates;
    }

    @Override
    public FormTemplate getFormTemplateById(int id) {
        //SELECT column_name(s) FROM table_name where column = value
        final String sql = "SELECT id, name FROM FormTemplates where id = ?";
        FormTemplate formTemplate = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
        return formTemplate;
    }
}
