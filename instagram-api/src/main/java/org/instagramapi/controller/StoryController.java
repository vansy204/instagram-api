package org.instagramapi.controller;

import org.instagramapi.exceptions.UserException;
import org.instagramapi.model.Story;
import org.instagramapi.model.User;
import org.instagramapi.service.UserService;
import org.instagramapi.service.implement.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")

public class StoryController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story,@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        Story createdStory = storyService.createStory(story,user.getId());

        return new ResponseEntity<Story>(createdStory, HttpStatus.OK);
    }
    @GetMapping("/m/{userId}")
    public ResponseEntity<List<Story>> findAllStoryByUserIdHandler(@PathVariable Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        List<Story> stories = user.getStories();
        return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
    }
}
