package com.sabo.catbooru.api;

import com.sabo.catbooru.model.Comment;
import com.sabo.catbooru.model.Post;
import com.sabo.catbooru.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/new")
    public ResponseEntity<byte[]> createNewPost(@RequestParam("userId") Long userId, @RequestParam("imageFile") MultipartFile imageFile, @RequestParam("tags") String tags) throws Exception {
        postService.createNewPost(new Post(userId), imageFile, tags);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<?> deletePostById(@PathVariable("postId") Long id){
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/addtag")
    public ResponseEntity<String> addTag(@PathVariable("postId") Long postId, @RequestParam("tag") String tag){
        tag = postService.addTag(postId, tag);
        return ResponseEntity.ok().body(tag);
    }

    @DeleteMapping("/{postId}/deletetag")
    public ResponseEntity<?> deleteTag(@PathVariable("postId") Long postId, @RequestParam("tag") String tag){
        postService.deleteTag(postId, tag);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam("order") String order, @RequestParam("sort") String sort){
        List<Post> posts = postService.getAllPosts(order, sort);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/query")
    public ResponseEntity<List<Post>> filterPosts(@RequestParam("query") String query, @RequestParam("order") String order, @RequestParam("sort") String sort){
        List<Post> posts = postService.filterPosts(query, order, sort);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id){
        Post post = postService.getPost(id);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId, @RequestParam Long userId){
        postService.likePost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId, @RequestParam Long userId){
        postService.unlikePost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/liked")
    public ResponseEntity<List<Post>> getLikedPosts(@RequestParam Long userId, @RequestParam("order") String order, @RequestParam("sort") String sort){
        List<Post> posts = postService.getLikedPosts(userId, order, sort);
        return ResponseEntity.ok().body(posts);
    }

    @PostMapping("/{postId}/addcomment")
    public ResponseEntity<Long> commentOnPost(@PathVariable("postId") Long postId, @RequestParam Long userId, @RequestParam String commentText){
        Comment comment = new Comment(postId, userId, commentText);
        postService.commentOnPost(comment);
        return ResponseEntity.ok().body(comment.getId());
    }

    @DeleteMapping("/deletecomment")
    public ResponseEntity<?> deleteCommentById(@RequestParam("commentId") Long commentId){
        postService.deleteCommentById(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getPostComments(@PathVariable("postId") Long postId){
        List<Comment> comments = postService.getPostComments(postId);
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/commented")
    public ResponseEntity<List<Post>> getCommentedOnPosts(@RequestParam("userId") Long userId, @RequestParam("order") String order, @RequestParam("sort") String sort){
        List<Post> posts = postService.getCommentedOnPosts(userId, order, sort);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/{postId}/tags")
    public ResponseEntity<List<String>> getPostTags(@PathVariable("postId") Long postId) {
        List<String> tags = postService.getPostTags(postId);
        return ResponseEntity.ok().body(tags);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAllTags(){
        List<String> tags = postService.getAllTags();
        return ResponseEntity.ok().body(tags);
    }

    @GetMapping("/{postId}/upvotes")
    public ResponseEntity<List<Long>> getPostUpvoteUserIds(@PathVariable Long postId){
        List<Long> ids = postService.getPostUpvoteUserIds(postId);
        return ResponseEntity.ok().body(ids);
    }
}
