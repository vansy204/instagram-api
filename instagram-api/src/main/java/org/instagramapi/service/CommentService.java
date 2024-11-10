package org.instagramapi.service;

import org.instagramapi.exceptions.CommentException;
import org.instagramapi.exceptions.PostException;
import org.instagramapi.exceptions.UserException;
import org.instagramapi.model.Comment;

public interface CommentService {
    public Comment createComment(Comment comment, Integer postId,Integer userId) throws UserException, PostException;
    public Comment findCommentById(Integer commentId) throws CommentException;
    public Comment likeComment(Integer commentId, Integer userId) throws UserException, CommentException;
    public Comment unlikeComment(Integer commentId, Integer userId) throws UserException, CommentException;

}
   