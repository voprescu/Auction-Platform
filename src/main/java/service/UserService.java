package service;

import dao.UserDAO;
import model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) throws Exception {
        if (username.isEmpty() || password.isEmpty()) {
            throw new Exception("Username și parola nu pot fi goale!");
        }

        User user = userDAO.login(username, password);
        if (user == null) {
            throw new Exception("Username sau parola greșite!");
        }
        return user;
    }

    public User register(String username, String password) throws Exception {
        if (username.isEmpty() || password.isEmpty()) {
            throw new Exception("Toate câmpurile sunt obligatorii!");
        }
        if (username.length() < 3) {
            throw new Exception("Username-ul trebuie să aibă minim 3 caractere!");
        }
        if (password.length() < 6) {
            throw new Exception("Parola trebuie să aibă minim 6 caractere!");
        }
        if (userDAO.usernameExists(username)) {
            throw new Exception("Username-ul este deja luat!");
        }

        User newUser = new User(username, password);
        return userDAO.registerAndReturn(newUser);
    }
}
