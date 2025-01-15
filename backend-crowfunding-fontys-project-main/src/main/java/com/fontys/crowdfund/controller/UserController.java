// UserController.java
package com.fontys.crowdfund.controller;

import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOUser;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOUser;
import com.fontys.crowdfund.persistence.specialdto.UserProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface UserController {

    @GetMapping
    List<OutputDTOUser> getAllUsers();

    @GetMapping("/{id}")
    ResponseEntity<OutputDTOUser> getUserById(@PathVariable int id);

    @GetMapping("/project/{id}")
    ResponseEntity<UserProjectDTO> getUserDataForProject(@PathVariable int id);

    @GetMapping("/email/{email}")
    ResponseEntity<Integer> getUserIdFromEmail(@PathVariable String email);

    @PostMapping
    ResponseEntity<OutputDTOUser> createUser(@RequestBody InputDTOUser userDTO);

    @PostMapping("/picture/{id}")
    ResponseEntity<Boolean> updateProfilePicture(@RequestBody Map<String, String> requestBody, @PathVariable int id);

    @GetMapping("/picture/{id}")
    ResponseEntity<String> getProfilePicture(@PathVariable int id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable int id);
}
