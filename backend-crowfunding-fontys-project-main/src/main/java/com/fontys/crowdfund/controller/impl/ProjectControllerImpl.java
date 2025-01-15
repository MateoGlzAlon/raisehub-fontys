package com.fontys.crowdfund.controller.impl;

import com.fontys.crowdfund.controller.ProjectController;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOBookmark;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOProjectImage;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOBookmark;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOProject;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOProject;
import com.fontys.crowdfund.business.ProjectService;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOProjectImage;
import com.fontys.crowdfund.persistence.specialdto.ProjectDetailsDTO;
import com.fontys.crowdfund.persistence.specialdto.ProjectOnlyCoverLandingPage;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    @Override
    @GetMapping
    public ResponseEntity<List<OutputDTOProject>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OutputDTOProject> getProjectById(@PathVariable int id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<OutputDTOProject> createProject(@RequestBody InputDTOProject projectDTO) {
        return ResponseEntity.ok(projectService.createProject(projectDTO));
    }

    @Override
    @PostMapping("/bookmark")
    public ResponseEntity<OutputDTOBookmark> addProjectBookmark(InputDTOBookmark bookmarkDTO) {
        return ResponseEntity.ok(projectService.addProjectBookmark(bookmarkDTO));
    }

    @Override
    public ResponseEntity<Void> removeProjectBookmark(int projectId, int userId) {
        projectService.removeProjectBookmark(projectId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin"})
    @Override
    public ResponseEntity<Void> deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/highlighted")
    public ResponseEntity<List<ProjectOnlyCoverLandingPage>> getCloseToFundingProjects() {
        return ResponseEntity.ok(projectService.getCloseToFundingAllProjects());
    }

    @Override
    @GetMapping("/new")
    public ResponseEntity<List<ProjectOnlyCoverLandingPage>> getNewProjects() {
        return ResponseEntity.ok(projectService.getNewProjects());
    }

    @Override
    @GetMapping("/bookmarks/{userId}")
    public ResponseEntity<List<ProjectOnlyCoverLandingPage>> getBookmarkedProjects(@PathVariable int userId) {
        return ResponseEntity.ok(projectService.getBookmarkedProjects(userId));
    }

    @Override
    @GetMapping("/bookmark/{userId}/{projectId}")
    public ResponseEntity<Boolean> isProjectBookmarked(@PathVariable int userId,@PathVariable int projectId) {
        return ResponseEntity.ok(projectService.isProjectBookmarked(userId,projectId));
    }

    @Override
    @GetMapping("/filters/pagination")
    public ResponseEntity<Map<String, Object>> getAllProjectsForLandingPage(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPercentageFunded,
            @RequestParam(required = false) Double maxPercentageFunded,
            @RequestParam(defaultValue = "dateCreated") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "") String name) {


        Page<ProjectOnlyCoverLandingPage> projectsPage = projectService.getAllProjectsForLandingPage(
                type, minPercentageFunded, maxPercentageFunded, sortBy,name, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("content", projectsPage.getContent());
        response.put("totalPages", projectsPage.getTotalPages());
        response.put("totalElements", projectsPage.getTotalElements());
        response.put("size", projectsPage.getSize());
        response.put("number", projectsPage.getNumber());

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/users/{id}")
    public ResponseEntity<List<OutputDTOProject>> getProjectsFromUserID(@PathVariable int id) {
        return ResponseEntity.ok(projectService.getProjectsFromUserId(id));
    }

    @Override
    @GetMapping("/users/id/{id}")
    public ResponseEntity<List<Integer>> getProjectIdsFromUserID(@PathVariable int id) {
        return ResponseEntity.ok(projectService.getProjectIdsFromUserID(id));
    }

    @Override
    public ResponseEntity<ProjectDetailsDTO> getProjectDetailsByID(int id) {
        return ResponseEntity.ok(projectService.getProjectDetailsById(id));
    }


//==========================================================================================00


    @Override
    @GetMapping("/images")
    public ResponseEntity<List<OutputDTOProjectImage>> getAllProjectImages() {
        return ResponseEntity.ok(projectService.getAllProjectImages());
    }



    @Override
    @PostMapping("/images")
    public ResponseEntity<OutputDTOProjectImage> createProjectImage(@RequestBody InputDTOProjectImage projectDTOImage) {
        return ResponseEntity.ok(projectService.createProjectImage(projectDTOImage));
    }

    @Override
    @GetMapping("/images/{id}")
    public ResponseEntity<OutputDTOProjectImage> getProjectImageById(@PathVariable int id) {
        return ResponseEntity.ok(projectService.getProjectImageById(id));
    }

    @Override
    @DeleteMapping("/images/{id}")
    public ResponseEntity<OutputDTOProjectImage> deleteProjectImage(@PathVariable int id) {
        projectService.deleteProjectImage(id);
        return ResponseEntity.ok().build();
    }


}
