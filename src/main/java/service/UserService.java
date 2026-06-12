package service;

import dao.UserDAO;
import model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) throws Exception {
        if (username.isEmpty() || password.isEmpty()) {
            throw new Exception("Username and password cannot be empty!");
        }

        User user = userDAO.login(username, password);
        if (user == null) {
            throw new Exception("Username or password is incorrect!");
        }
        return user;
    }

    public User register(String username, String password) throws Exception {
        if (username.isEmpty() || password.isEmpty()) {
            throw new Exception("All fields are required!");
        }
        if (username.length() < 3) {
            throw new Exception("The username must be at least 3 characters!");
        }
        if (password.length() < 6) {
            throw new Exception("The password must be at least 6 characters!");
        }
        if (userDAO.usernameExists(username)) {
            throw new Exception("The username is already taken!");
        }

        User newUser = new User(username, password);
        return userDAO.registerAndReturn(newUser);
    }
}
