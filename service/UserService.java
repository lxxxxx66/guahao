package com.medicalappointment.service;

import com.medicalappointment.dao.UserDAO;
import com.medicalappointment.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        return userDAO.login(username, password);
    }

    public boolean register(User user) {
        return userDAO.register(user);
    }
}
