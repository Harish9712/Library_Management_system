package model;

public abstract class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public boolean checkPassword(String pass) { return this.password.equals(pass); }
    public abstract String getRole();
}
