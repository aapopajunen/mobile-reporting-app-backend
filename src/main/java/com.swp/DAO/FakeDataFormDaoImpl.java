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
                put(1, new Layout(1, "Raportti 1"));
                put(2, new Layout(2, "Raportti 2"));
                put(3, new Layout(3, "Raportti 3"));
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
    public User getUserById(int id) {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public Collection<AccessRights> getUserAccessRights(int id) {
        return null;
    }

    @Override
    public void grantUserAccessRights(int id, Map<String, String> params) {

    }

    @Override
    public void deleteUserAccessRights(int id, Map<String, String> params) {

    }

    @Override
    public Collection<Form> getFormsByUser(int id, Map<String, String> params) {
        return null;
    }

    @Override
    public void createForm(Form form) {

    }

    @Override
    public Collection<Layout> getLayoutsByUser(int id, Map<String, String> params) {
        return null;
    }
}
