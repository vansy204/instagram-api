package org.instagramapi.service;

import org.instagramapi.exceptions.UserException;
import org.instagramapi.model.User;

import java.util.List;

public interface UserService{
    public User registerUser(User user) throws UserException;
    public User findUserById(Integer id) throws UserException;
    public User findUserProfile(String token) throws UserException;
    public User findUserByUsername(String username) throws UserException;
    public String followUser(Integer reqUserId, Integer followerId) throws UserException;
    public String unFollowUser(Integer reqUserId, Integer followerId) throws UserException;
    public List<User> findUserByIds(List<Integer> userIds) throws UserException;
    public List<User> searchUser(String query) throws UserException;
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException;



}

