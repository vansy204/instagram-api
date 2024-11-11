package org.instagramapi.service.implement;

import org.instagramapi.dto.UserDto;
import org.instagramapi.exceptions.StoryException;
import org.instagramapi.exceptions.UserException;
import org.instagramapi.model.Story;
import org.instagramapi.model.User;
import org.instagramapi.repository.StoryRepository;
import org.instagramapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class StoryService implements org.instagramapi.service.StoryService {
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Story createStory(Story story, Integer userId) throws UserException {
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());

        story.setUser(userDto);
        story.setTimestamp(LocalDateTime.now());

        user.getStories().add(story);

        return storyRepository.save(story);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
        User user = userService.findUserById(userId);
        List<Story> stories = user.getStories();
        if(stories.size() == 0){
            throw new UserException("This user doesn't have any story");
        }
        return stories;
    }
}
