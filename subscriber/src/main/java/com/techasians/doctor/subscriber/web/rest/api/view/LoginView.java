package com.techasians.doctor.subscriber.web.rest.api.view;

/**
 * LoginView class.
 * Contains data used return for client when login success
 *
 * @author ngocquang
 * @since 20/1/2020
 */
public class LoginView {
    private String token;

    public LoginView() {
    }

    public LoginView(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
