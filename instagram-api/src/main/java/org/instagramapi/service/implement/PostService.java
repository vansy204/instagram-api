package org.instagramapi.service.implement;

import org.instagramapi.dto.UserDto;
import org.instagramapi.exceptions.PostException;
import org.instagramapi.exceptions.UserException;
import org.instagramapi.modal.Post;
import org.instagramapi.modal.User;
import org.instagramapi.repository.PostRepository;
import org.instagramapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements org.instagramapi.service.PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Post createPost(Post post,Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setUserImage(user.getImage());
        userDto.setName(user.getName());

        post.setUser(userDto);
        Post createdPost = postRepository.save(post);
        return createdPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(post.getUser().getId().equals(user.getId())) {
            postRepository.deleteById(postId);
            return "Successfully deleted post";
        }
        throw new PostException("You can't delete other user's post");
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepository.findByUserId(userId);
        if(posts.size() == 0){
            throw new UserException("This user does not have any post");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> opt = postRepository.findById(postId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new PostException("Post not found with id " + postId);
    }

    @Override
    public List<Post> findAllPostsByUserIds(List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postRepository.findAllPostByUserIds(userIds);
        if(posts.size() == 0){
            throw new PostException("No post available");
        }
        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(!user.getSavedPost().contains(post)){
            user.getSavedPost().add(post);
            userRepository.save(user);
        }
        return "Post Saved Successfully";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(!user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
            userRepository.save(user);
        }
        return "Post Removed Successfully";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setUserImage(user.getImage());
        userDto.setName(user.getName());

        post.getLikedByUsers().add(userDto);
        return postRepository.save(post);
    }

    @Override
    public Post unLikePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setUserImage(user.getImage());
        userDto.setName(user.getName());

        post.getLikedByUsers().remove(userDto);
        return postRepository.save(post);
    }
}
