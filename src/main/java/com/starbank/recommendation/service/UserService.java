package com.starbank.recommendation.service;

import com.starbank.recommendation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean isExistsUser(UUID id) {

        return userRepository.isExistsUser(id);

    }

}
