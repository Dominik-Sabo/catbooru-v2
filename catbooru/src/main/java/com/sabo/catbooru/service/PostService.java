package com.sabo.catbooru.service;

import com.sabo.catbooru.model.*;
import com.sabo.catbooru.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UpvoteRepository upvoteRepository;

    @Autowired
    private CommentRepository commentRepository;

    public void createNewPost(Post post, MultipartFile imageFile, String tags) throws Exception {
        postRepository.save(post);
        String filename = post.getId().toString();
        if(imageFile.getOriginalFilename().toLowerCase().endsWith(".jpg")) filename = filename + ".jpg";
        else if(imageFile.getOriginalFilename().toLowerCase().endsWith(".jpeg")) filename = filename + ".jpeg";
        else if(imageFile.getOriginalFilename().toLowerCase().endsWith(".png")) filename = filename + ".png";
        else if(imageFile.getOriginalFilename().toLowerCase().endsWith(".gif")) filename = filename + ".gif";
        else {
            postRepository.delete(post);
            throw new Exception();
        }
        storageService.save(imageFile, filename);
        post.setFilePath(filename);
        postRepository.save(post);
        if(tags.isBlank()) return;
        tagRepository.saveAll(formatTags(post.getId(), removeDuplicateTags(tags)));
    }

    public void deleteUserPosts(Long userId){
        List<Post> posts = postRepository.findAllByUser_Id(userId);
        for(Post post: posts){
            deletePostById(post.getId());
        }
    }

    @Transactional
    public void deletePostById(Long id){
        tagRepository.deleteAllByPostId(id);
        upvoteRepository.deleteAllByPostId(id);
        commentRepository.deleteAllByPostId(id);
        postRepository.deleteById(id);
    }

    public String addTag(Long postId, String newTag){
        List<Tag> postTags = tagRepository.findAllByPostId(postId);
        for(Tag tag : postTags){
            if (newTag.equals(tag.getTagValue())){
                return null;
            }
        }
        tagRepository.save(new Tag(postId, newTag));
        return newTag;
    }

    @Transactional
    public void deleteTag(Long postId, String tag){
        tagRepository.deleteByPostIdAndTagValue(postId, tag);
    }

    public List<Post> getAllPosts(String order, String sort){
        switch (order){
            case "ascending": return postRepository.findAll(Sort.by(Sort.Direction.ASC, sort));
            case "descending": return postRepository.findAll(Sort.by(Sort.Direction.DESC, sort));
            default: return postRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
        }

    }

    public List<Post> filterPosts(String query, String order, String sort){
        List<Post> allPosts = getAllPosts(order, sort);
        String potentialUsername = null;
        if(query.isEmpty()) return allPosts;

        List<String> listQuery = removeDuplicateTags(query);
        for (String tag: listQuery){
            if(tag.contains("user:")){
                potentialUsername = tag.split(":", 2)[1];
                listQuery.remove(tag);
                break;
            }
        }
        final String username = potentialUsername;

        if(potentialUsername!=null){
            allPosts.removeIf(post -> !post.getUsername().equals(username));
        }

        if(listQuery.isEmpty()) return allPosts;

        List<Tag> tagList = new ArrayList<Tag>();

        for(String tag: listQuery){
           tagList.addAll(tagRepository.findAllByTagValue(tag));
        }

        List<Long> ids = new ArrayList<Long>();

        for(Tag tag: tagList){
            ids.add(tag.getPostId());
        }
        List<Long> culledIds = ids.stream().distinct().collect(Collectors.toList());

        for(Long id: culledIds){
            for(int i = 0; i<listQuery.size()-1; i++){
                ids.remove(id);
            }
        }

        allPosts.removeIf(post -> !ids.contains(post.getId()));
        return allPosts;
    }

    public Post getPost(Long id){
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> getLikedPosts(Long userId, String order, String sort){
        List<Upvote> upvotes = upvoteRepository.findAllByUserId(userId);
        List<Long> ids = new ArrayList<Long>();
        for(Upvote upvoted: upvotes){
            ids.add(upvoted.getPostId());
        }
        return sortPosts(ids, order, sort);
    }

    @Transactional
    public void likePost(Long userId, Long postId){
        postRepository.likePost(postId);
        upvoteRepository.save(new Upvote(userId, postId));
    }

    @Transactional
    public void unlikePost(Long userId, Long postId){
        postRepository.unlikePost(postId);
        upvoteRepository.deleteByUserIdAndPostId(userId, postId);
    }

    public void commentOnPost(Comment comment){
        userRepository.findById(comment.getUserId()).ifPresent(user -> comment.setUsername(user.getUsername()));
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }

    public List<Comment> getPostComments(Long postId){
        return commentRepository.findAllByPostId(postId);
    }

    public List<Post> getCommentedOnPosts(Long userId, String order, String sort){
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        List<Long> ids = new ArrayList<Long>();
        for(Comment commented: comments){
            ids.add(commented.getPostId());
        }
        return sortPosts(ids, order, sort);
    }

    public List<String> getPostTags(Long postId){
        return tagRepository.findAllByPostId(postId).stream().map(Tag::getTagValue).sorted().collect(Collectors.toList());
    }

    public List<String> getAllTags(){
        return tagRepository.findAll().stream().map(Tag::getTagValue).distinct().sorted().collect(Collectors.toList());
    }

    public List<Long> getPostUpvoteUserIds(Long postId){
        return upvoteRepository.findAllByPostId(postId).stream().map(Upvote::getUserId).collect(Collectors.toList());
    }

    private List<String> removeDuplicateTags(String tags){
        String[] allTags = tags.split(" ");
        ArrayList<String> tagList = new ArrayList<>(Arrays.asList(allTags));
        ArrayList<String> duplicateList = new ArrayList<String>();
        for(String tag : tagList){
            if(!duplicateList.contains(tag)){
                duplicateList.add(tag);
            }
        }
        return duplicateList;
    }

    private List<Tag> formatTags(Long postId, List<String> tags){
        List<Tag> formattedTags = new ArrayList<Tag>();
        for(String tag: tags){
            formattedTags.add(new Tag(postId, tag));
        }
        return formattedTags;
    }

    private List<Post> sortPosts(List<Long> ids, String order, String sort){
        switch (order) {
            case "ascending":
                return postRepository.findByIdIn(ids, Sort.by(Sort.Direction.ASC, sort));
            case "descending":
                return postRepository.findByIdIn(ids, Sort.by(Sort.Direction.DESC, sort));
            default:
                return postRepository.findByIdIn(ids, Sort.by(Sort.Direction.DESC, "timestamp"));
        }
    }
}
