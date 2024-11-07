package org.instagramapi.service;

import org.instagramapi.exceptions.PostException;
import org.instagramapi.exceptions.UserException;
import org.instagramapi.modal.Post;

import java.util.List;

public interface PostService {
    public Post createPost(Post post,Integer userId) throws UserException;
    public String deletePost(Integer postId,Integer userId) throws UserException, PostException;
    public List<Post> findPostByUserId(Integer userId) throws UserException;
    public Post findPostById(Integer postId) throws PostException;
    public List<Post> findAllPostsByUserIds(List<Integer> userIds) throws PostException,UserException;
    public String savedPost(Integer postId, Integer userId) throws UserException, PostException;
    public String unSavedPost(Integer postId, Integer userId) throws UserException, PostException;
    public Post likePost(Integer postId, Integer userId) throws UserException, PostException;
    public Post unLikePost(Integer postId, Integer userId) throws UserException, PostException;

}
