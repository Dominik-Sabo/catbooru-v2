package com.sabo.catbooru.model;

public class AuthenticationResponse {

    public AuthenticationResponse(User user, String token){
        this.username = user.getUsername();
        this.id = user.getId();
        this.token = token;
    }

    private Long id;

    private String username;

    private String token;

    public Long getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
