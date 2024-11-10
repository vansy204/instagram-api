package org.instagramapi.service.implement;

import org.instagramapi.dto.UserDto;
import org.instagramapi.exceptions.CommentException;
import org.instagramapi.exceptions.PostException;
import org.instagramapi.exceptions.UserException;
import org.instagramapi.model.Comment;
import org.instagramapi.model.Post;
import org.instagramapi.model.User;
import org.instagramapi.repository.CommentRepository;
import org.instagramapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService implements org.instagramapi.service.CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());

        comment.setUser(userDto);
        comment.setCreatedAt(LocalDateTime.now());

        Comment createdComment = commentRepository.save(comment);

        post.getComments().add(createdComment);
        postRepository.save(post);

        return createdComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {
        Optional<Comment> opt = commentRepository.findById(commentId);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new CommentException("Comment is not exists with id: " + commentId);
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws UserException, CommentException {
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());

        comment.getLikeByUser().add(userDto);

        return commentRepository.save(comment);
    }

    @Override
    public Comment unlikeComment(Integer commentId, Integer userId) throws UserException, CommentException {
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());

        comment.getLikeByUser().remove(userDto);

        return commentRepository.save(comment);
    }
}
