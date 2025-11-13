package service;

import model.Admin;
import model.Borrower;
import model.User;

import java.util.Collection;
import java.util.HashMap;

public class AuthService {
    private static final String ADMIN_EMAIL = "admin@library.com";
    private static final String ADMIN_PASSWORD = "admin123";
    private HashMap<String, User> users = new HashMap<>();

    public AuthService() {
        users.put(ADMIN_EMAIL, new Admin(ADMIN_EMAIL, ADMIN_PASSWORD));
    }

    public boolean registerBorrower(String email, String password) {
        if (users.containsKey(email)) return false;
        users.put(email, new Borrower(email, password));
        return true;
    }

    public User login(String email, String password) {
        User user = users.get(email);
        return (user != null && user.checkPassword(password)) ? user : null;
    }

    public Collection<User> getAllUsers() { return users.values(); }
}