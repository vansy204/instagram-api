package org.instagramapi.service;

import jdk.jshell.spi.ExecutionControl;
import org.instagramapi.exceptions.UserException;
import org.instagramapi.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserService{
    public User registerUser(User user) throws UserException;
    public User findUserById(int id) throws UserException;
    public User findUserProfile(String token) throws UserException;
    public User findUserByUsername(String username) throws UserException;
    public String followUser(int reqUserId, int followerId) throws UserException;
    public String unFollowUser(int reqUserId, int followerId) throws UserException;
    public List<User> findUserByIds(List<Integer> userIds) throws UserException;
    public List<User> searchUser(String query) throws UserException;
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException;



}

