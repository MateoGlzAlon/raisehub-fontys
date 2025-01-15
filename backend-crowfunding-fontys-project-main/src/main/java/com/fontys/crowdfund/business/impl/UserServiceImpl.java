package com.fontys.crowdfund.business.impl;

import com.fontys.crowdfund.business.UserService;
import com.fontys.crowdfund.exception.EmailAlreadyExistsException;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOUser;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOUser;
import com.fontys.crowdfund.repository.UserRepository;
import com.fontys.crowdfund.persistence.entity.UserEntity;
import com.fontys.crowdfund.persistence.specialdto.UserProjectDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // Get all users and convert them to DTOs
    @Override
    public List<OutputDTOUser> getAllUsers() {
        List<OutputDTOUser> outputDTOUsers = new ArrayList<>();

        for (UserEntity userEntity : userRepository.findAll()) {
            outputDTOUsers.add(createOutputDTOUser(userEntity));
        }

        return outputDTOUsers;
    }

    // Get user by ID
    @Override
    public OutputDTOUser getUserById(int id) {
        return createOutputDTOUser(userRepository.findById(id));
    }

    // Create a new user
    @Override
    public OutputDTOUser createUser(InputDTOUser userDTO) {

        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new EmailAlreadyExistsException();
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        UserEntity user = UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(encodedPassword)
                .projects(new HashSet<>())
                .role(userDTO.getRole())
                .profilePicture(userDTO.getProfilePicture())
                .build();

        return createOutputDTOUser(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserProjectDTO getUserDataForProject(int id) {
        return userRepository.getUserDataForProject(id);
    }

    @Override
    public Integer getUserIdFromEmail(String email) {
        return userRepository.getUserIdFromEmail(email);
    }

    @Override
    @Transactional
    public boolean updateProfilePicture(String newPicture, int id) {
        int rowsUpdated = userRepository.updateProfilePicture(newPicture, id);
        return rowsUpdated > 0;
    }

    @Override
    public String getProfilePicture(int id) {
        return userRepository.getProfilePicture(id);
    }


    public OutputDTOUser createOutputDTOUser(UserEntity userEntity) {

        return OutputDTOUser.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .role(userEntity.getRole())
                .profilePicture(userEntity.getProfilePicture())
                .build();

    }



}
