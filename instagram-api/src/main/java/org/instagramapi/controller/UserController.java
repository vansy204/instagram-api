package org.instagramapi.controller;

import org.instagramapi.exceptions.UserException;
import org.instagramapi.model.User;
import org.instagramapi.response.MessageResponse;
import org.instagramapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findUserByIdHandler( @PathVariable Integer id) throws UserException {
        User user = userService.findUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsernameHandler( @PathVariable String username) throws UserException {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<MessageResponse> followUserHandler(@PathVariable Integer followUserId,@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);

        String message = userService.followUser(user.getId(),followUserId);
        MessageResponse messageResponse = new MessageResponse(message);
        return new ResponseEntity<MessageResponse> (messageResponse,HttpStatus.OK);
    }
    @PutMapping("/unfollow/{userId}")
    public ResponseEntity<MessageResponse> unfollowUserHandler(@PathVariable Integer userId,@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);

        String message = userService.unFollowUser(user.getId(),userId);
        MessageResponse messageResponse = new MessageResponse(message);
        return new ResponseEntity<MessageResponse> (messageResponse,HttpStatus.OK);
    }
    @GetMapping("/req")
    public ResponseEntity<User> findUserProfileHandler(@RequestHeader ("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);

        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<User>> findUserByUserIdsHandler(@PathVariable List<Integer> userIds) throws UserException {
        List<User> users = userService.findUserByIds(userIds);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
    //api/user/search?q="query"
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q") String query) throws UserException {
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
    @PutMapping("/account/edit")
    public ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization") String token, @RequestBody User user) throws UserException {
        User reqUser = userService.findUserProfile(token);
        User updatedUser = userService.updateUserDetails(user,reqUser);

        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }
}
