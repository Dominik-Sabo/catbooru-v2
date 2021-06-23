package com.sabo.catbooru.api;

import com.sabo.catbooru.model.AuthenticationRequest;
import com.sabo.catbooru.model.AuthenticationResponse;
import com.sabo.catbooru.model.User;
import com.sabo.catbooru.service.UserService;
import com.sabo.catbooru.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerNewUser(@RequestBody User user){
        userService.registerNewUser(user);
        AuthenticationResponse response = new AuthenticationResponse(user, generateJwt(user));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        User user = userService.getUserByUsername(authenticationRequest.getUsername());

        AuthenticationResponse response = new AuthenticationResponse(user, generateJwt(user));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<AuthenticationResponse> changeUsernamePassword(@PathVariable Long id, @RequestBody User user){
        userService.changeUsernamePassword(id, user);
        AuthenticationResponse response = new AuthenticationResponse(user, generateJwt(user));
        return ResponseEntity.ok().body(response);
    }

    private String generateJwt(User user){
        final UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
        return jwtTokenUtil.generateToken(userDetails);
    }

}
