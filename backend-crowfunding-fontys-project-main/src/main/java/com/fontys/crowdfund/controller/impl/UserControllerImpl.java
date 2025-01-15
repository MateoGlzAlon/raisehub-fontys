package com.fontys.crowdfund.controller.impl;

import com.fontys.crowdfund.controller.UserController;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOUser;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOUser;
import com.fontys.crowdfund.business.UserService;
import com.fontys.crowdfund.persistence.specialdto.UserProjectDTO;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    private UserService userService;

    @Override
    @GetMapping
    @RolesAllowed({"admin"})
    public List<OutputDTOUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OutputDTOUser> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    @GetMapping("/project/{id}")
    public ResponseEntity<UserProjectDTO> getUserDataForProject(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserDataForProject(id));
    }


    @GetMapping("/email/{email}")
    @Override
    public ResponseEntity<Integer> getUserIdFromEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserIdFromEmail(email));
    }

    @Override
    @PostMapping
    public ResponseEntity<OutputDTOUser> createUser(@RequestBody InputDTOUser userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @Override
    @PostMapping("/picture/{id}")
    public ResponseEntity<Boolean> updateProfilePicture(@RequestBody Map<String, String> requestBody, @PathVariable int id) {
        String newPicture = requestBody.get("newPicture");

        boolean isUpdated = userService.updateProfilePicture(newPicture, id);
        return ResponseEntity.ok(isUpdated);
    }

    @Override
    @GetMapping("/picture/{id}")
    public ResponseEntity<String> getProfilePicture(@PathVariable int id) {
        return ResponseEntity.ok(userService.getProfilePicture(id));
    }


    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
