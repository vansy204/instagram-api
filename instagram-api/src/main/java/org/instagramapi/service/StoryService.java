package org.instagramapi.service;

import org.instagramapi.exceptions.StoryException;
import org.instagramapi.exceptions.UserException;
import org.instagramapi.model.Story;

import java.util.List;

public interface StoryService {
    public Story createStory(Story story, Integer userId) throws UserException;
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;
}
