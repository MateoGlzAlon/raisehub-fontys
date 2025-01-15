// ProjectController.java
package com.fontys.crowdfund.controller;

import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOBookmark;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOProjectImage;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOBookmark;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOProject;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOProject;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOProjectImage;
import com.fontys.crowdfund.persistence.specialdto.ProjectDetailsDTO;
import com.fontys.crowdfund.persistence.specialdto.ProjectOnlyCoverLandingPage;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface ProjectController {

    @GetMapping
    ResponseEntity<List<OutputDTOProject>> getAllProjects();

    @GetMapping("/{id}")
    ResponseEntity<OutputDTOProject> getProjectById(@PathVariable int id);

    @GetMapping("/highlighted")
    ResponseEntity<List<ProjectOnlyCoverLandingPage>> getCloseToFundingProjects();

    @GetMapping("/new")
    ResponseEntity<List<ProjectOnlyCoverLandingPage>> getNewProjects();

    @GetMapping("/bookmarks/{userId}")
    ResponseEntity<List<ProjectOnlyCoverLandingPage>> getBookmarkedProjects(@PathVariable int userId);

    @GetMapping("/bookmark/{userId}/{projectId}")
    ResponseEntity<Boolean> isProjectBookmarked(@PathVariable int userId, @PathVariable int projectId);

    @GetMapping("/filters/pagination")
    ResponseEntity<Map<String, Object>> getAllProjectsForLandingPage(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPercentageFunded,
            @RequestParam(required = false) Double maxPercentageFunded,
            @RequestParam(defaultValue = "dateCreated") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "") String name);

    @GetMapping("/users/{id}")
    ResponseEntity<List<OutputDTOProject>> getProjectsFromUserID(@PathVariable int id);

    @GetMapping("/users/id/{id}")
    ResponseEntity<List<Integer>> getProjectIdsFromUserID(@PathVariable int id);

    @GetMapping("/details/{id}")
    ResponseEntity<ProjectDetailsDTO> getProjectDetailsByID(@PathVariable int id);

    @PostMapping
    ResponseEntity<OutputDTOProject> createProject(@RequestBody InputDTOProject projectDTO);

    @PostMapping("/bookmark")
    ResponseEntity<OutputDTOBookmark> addProjectBookmark(@RequestBody InputDTOBookmark bookmarkDTO);

    @DeleteMapping("/bookmark/{projectId}/{userId}")
    ResponseEntity<Void> removeProjectBookmark(@PathVariable int projectId, @PathVariable int userId);

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin"})
    ResponseEntity<Void> deleteProject(@PathVariable int id);



    //=================================00

    @GetMapping("/images")
    ResponseEntity<List<OutputDTOProjectImage>> getAllProjectImages();

    @PostMapping("/images")
    ResponseEntity<OutputDTOProjectImage> createProjectImage(@RequestBody InputDTOProjectImage projectDTOImage);

    @GetMapping("/images/{id}")
    ResponseEntity<OutputDTOProjectImage> getProjectImageById(@PathVariable int id);

    @DeleteMapping("/images/{id}")
    ResponseEntity<OutputDTOProjectImage> deleteProjectImage(@PathVariable int id);


}
