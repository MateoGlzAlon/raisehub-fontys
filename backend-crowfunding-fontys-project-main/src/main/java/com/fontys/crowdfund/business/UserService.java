package com.fontys.crowdfund.business;

import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOUser;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOUser;
import com.fontys.crowdfund.persistence.specialdto.UserProjectDTO;

import java.util.List;

public interface UserService {

    // Get all users and convert them to DTOs
    List<OutputDTOUser> getAllUsers();

    // Get user by ID
    OutputDTOUser getUserById(int id);

    // Create a new user
    OutputDTOUser createUser(InputDTOUser userDTO);

    void deleteUser(int id);

    UserProjectDTO getUserDataForProject(int id);

    Integer getUserIdFromEmail(String email);

    boolean updateProfilePicture(String newPicture, int id);

    String getProfilePicture(int id);
}
