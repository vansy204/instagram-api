package org.instagramapi.controller;

import org.instagramapi.exceptions.UserException;
import org.instagramapi.modal.User;
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
    public ResponseEntity<User> findUserByIdHandler( @PathVariable int id) throws UserException {
        User user = userService.findUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsernameHandler( @PathVariable String username) throws UserException {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<MessageResponse> followUserHandler(@PathVariable int followUserId){
        //MessageResponse res = userService.followUser(followUserId,)
        return null;
    }
    @PutMapping("/unfollow/{userId}")
    public ResponseEntity<MessageResponse> unfollowUserHandler(@PathVariable int userId){
        return null;
    }
    @PutMapping("/req")
    public ResponseEntity<MessageResponse> findUserProfileHandler(@RequestHeader ("Authorization") String token) throws UserException {

        return null;
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

    public ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization") String token, @RequestBody User user) throws UserException {

        //User updatedUser = userService.updateUserDetails(updated)
        return null;
    }
}
