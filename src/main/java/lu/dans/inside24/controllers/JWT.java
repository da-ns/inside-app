package lu.dans.inside24.controllers;

public class JWT {

    private String token;

    JWT(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
