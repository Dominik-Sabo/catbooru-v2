package com.sabo.catbooru.service;

import com.sabo.catbooru.model.Upvote;
import com.sabo.catbooru.model.User;
import com.sabo.catbooru.repository.CommentRepository;
import com.sabo.catbooru.repository.PostRepository;
import com.sabo.catbooru.repository.UpvoteRepository;
import com.sabo.catbooru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UpvoteRepository upvoteRepository;

    @Autowired
    PostService postService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void registerNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Long id){
        for(Upvote upvote: upvoteRepository.findAllByUserId(id)){
            postRepository.unlikePost(upvote.getPostId());
        }
        commentRepository.userDelete(id);
        upvoteRepository.deleteAllByUserId(id);
        postService.deleteUserPosts(id);
        userRepository.deleteById(id);
    }

    public void changeUsernamePassword(Long id, User user){
        //Todo

    }

    public UserDetails loadUserByUsername(String username){
        User user = this.getUserByUsername(username);
        return new org.springframework.security.core.userdetails.User (user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    @Transactional
    public User makeAdmin(String username){
        userRepository.makeAdmin(username);
        return userRepository.findByUsername(username);
    }
}
