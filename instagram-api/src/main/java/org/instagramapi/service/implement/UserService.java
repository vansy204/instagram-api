package org.instagramapi.service.implement;

import org.instagramapi.dto.UserDto;
import org.instagramapi.exceptions.UserException;
import org.instagramapi.model.User;
import org.instagramapi.repository.UserRepository;
import org.instagramapi.security.JwtTokenClaims;
import org.instagramapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements org.instagramapi.service.UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public User registerUser(User user) throws UserException {
        Optional <User> isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist.isPresent()) {
            throw new UserException("Email is already exists");
        }
        Optional <User> isUsernameExist = userRepository.findByUsername(user.getUsername());
        if(isUsernameExist.isPresent()) {
            throw new UserException("Username is already taken...");
        }
        if(user.getEmail() == null || user.getUsername() == null || user.getPassword() == null | user.getName() == null) {
            throw new UserException("all fields are required");
        }
        User newUser  = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);

    }

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        throw new UserException("user not exist with id: " + id);
    }

    @Override
    public User findUserProfile(String token) throws UserException {
        token = token.substring(7);
        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClamsFromToken(token);
        String email = jwtTokenClaims.getUsername();
        Optional<User> opt = userRepository.findByEmail(email);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("Invalid token...");
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return user.get();
        }
        throw new UserException("user not exist with username: " + username);
    }

    @Override
    public String followUser(Integer reqUserId, Integer followerId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followerId);

        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getImage());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();
        following.setEmail(follower.getEmail());
        following.setId(follower.getId());
        following.setName(follower.getName());
        following.setUserImage(follower.getUserImage());
        following.setUsername(follower.getUsername());

        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);

        userRepository.save(reqUser);
        userRepository.save(followUser);

        return "You are following " + followUser.getUsername();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followerId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followerId);

        UserDto follower = new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getImage());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();
        following.setEmail(follower.getEmail());
        following.setId(follower.getId());
        following.setName(follower.getName());
        following.setUserImage(follower.getUserImage());
        following.setUsername(follower.getUsername());

        reqUser.getFollowing().remove(following);
        followUser.getFollower().remove(follower);

        userRepository.save(reqUser);
        userRepository.save(followUser);
        return "You have unfollowed " + followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> ids) throws UserException {
        List<User> users = userRepository.findAllUsersByUserIds(ids);
        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> users = userRepository.findByQuery(query);
        if(users.size() == 0){
            throw new UserException("user not found");
        }
        return users;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {
        if(updatedUser.getEmail() !=null){
            existingUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getUsername() !=null){
            existingUser.setUsername(updatedUser.getUsername());
        }
        if(updatedUser.getPassword() !=null){
            existingUser.setPassword(updatedUser.getPassword());
        }
        if(updatedUser.getName() !=null){
            existingUser.setName(updatedUser.getName());
        }
        if(updatedUser.getImage() !=null){
            existingUser.setImage(updatedUser.getImage());
        }
        if(updatedUser.getBio() !=null){
            existingUser.setBio(updatedUser.getBio());
        }
        if(updatedUser.getMobile() !=null){
            existingUser.setMobile(updatedUser.getMobile());
        }
        if(updatedUser.getGender() !=null){
            existingUser.setGender(updatedUser.getGender());
        }
        if(updatedUser.getWebsite() !=null){
            existingUser.setWebsite(updatedUser.getWebsite());
        }
        if(updatedUser.getId() ==(existingUser.getId())){
            return userRepository.save(existingUser);
        }
        throw new UserException("You can't update this user ");
    }
}
