package edu.school21.models;

public class User {
    private Long id;
    private String login;
    private String password;
    private boolean authenticated;

    public User(Long id, String login, String password, boolean authenticated) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authenticated = authenticated;
    }

    public Long getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public boolean isAuthenticated() { return authenticated; }

    public void setId(Long id) { this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setAuthenticated(boolean authenticated) { this.authenticated = authenticated; }
}
