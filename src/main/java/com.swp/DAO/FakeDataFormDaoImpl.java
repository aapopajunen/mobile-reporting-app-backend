package com.swp.DAO;

import com.swp.Entity.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Qualifier("fakeData")
class FakeDataFormDaoImpl implements FormDAO {

    private static Map<Integer, Layout> forms;

    static {
        forms = new HashMap<Integer, Layout>() {

            {
                put(1, new Layout(1, "Raportti 1", "ongi", "kek"));
                put(2, new Layout(2, "Raportti 2", "pongi", "juu"));
                put(3, new Layout(3, "Raportti 3", "kys", "terve"));
            }

        };
    }

    @Override
    public Collection<Form> getAllForms(Map<String, String> params) {
        return null;
    }

    @Override
    public Form getFormById(int formId) {
        return null;
    }

    @Override
    public void deleteFormById(int formId) {

    }

    @Override
    public Collection<Field> getFieldsByFormId(int formId) {
        return null;
    }

    @Override
    public Collection<FieldAnswer> getAnswersByFormId(int formId) {
        return null;
    }

    @Override
    public Collection<Layout> getAllLayouts() {
        return null;
    }

    @Override
    public Layout getLayoutById(int layoutid) {
        return null;
    }

    @Override
    public Collection<Field> getFieldsByLayoutId(int layoutId) {
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }

    @Override
    public void createUser(String username) {

    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public Collection<AccessRights> getUserAccessRights(String username) {
        return null;
    }

    @Override
    public void grantUserAccessRights(String username, Map<String, String> params) {

    }

    @Override
    public void deleteUserAccessRights(String username, Map<String, String> params) {

    }

    @Override
    public Collection<Form> getFormsByUser(String username, Map<String, String> params) {
        return null;
    }

    @Override
    public void createForm(Form form) {

    }

    @Override
    public Collection<Layout> getLayoutsByUser(String username, Map<String, String> params) {
        return null;
    }
}
