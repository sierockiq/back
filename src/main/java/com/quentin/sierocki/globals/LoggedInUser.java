package com.quentin.sierocki.globals;

import com.quentin.sierocki.controller.model.UserDTO;

public class LoggedInUser {

    private UserDTO user;
    private String jwtToken;

    public LoggedInUser() {}

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return this.user;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

}
