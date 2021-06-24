package com.sabo.catbooru.model;

public class AuthenticationResponse {

    public AuthenticationResponse(User user, String token){
        this.username = user.getUsername();
        this.id = user.getId();
        this.token = token;
        this.isAdmin = user.getAdmin();
    }

    private Long id;

    private String username;

    private String token;

    private Boolean isAdmin;

    public Long getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }
}
