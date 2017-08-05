package br.com.jgeniselli.catalogacaolem.login;

/**
 * Created by joaog on 05/08/2017.
 */

public class User {

    private String name;
    private String userId;
    private String password;
    private String token;

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public User(String name, String userId, String password, String token) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}

