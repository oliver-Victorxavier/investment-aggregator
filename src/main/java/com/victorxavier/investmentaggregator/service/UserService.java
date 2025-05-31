package com.victorxavier.investmentaggregator.service;

import com.victorxavier.investmentaggregator.controller.CreateUserDTO;
import com.victorxavier.investmentaggregator.entity.User;
import com.victorxavier.investmentaggregator.repository.UserRepository;
import com.victorxavier.investmentaggregator.controller.UpdateUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO) {

        var entity = new User(
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password()
        );

        var userSaved = userRepository.save(entity);
        return userSaved.getId();
    }
    public Optional<User> getUserById(String userId) {

        return userRepository.findById(UUID.fromString(userId));
    }
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDTO updateUserDTO) {

        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDTO.username() != null) {
                user.setUsername(updateUserDTO.username());
            }

            if (updateUserDTO.password() != null) {
                user.setPassword(updateUserDTO.password());
            }

            userRepository.save(user);
        }

    }

    public void deleteById(String userId) {

        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }
}