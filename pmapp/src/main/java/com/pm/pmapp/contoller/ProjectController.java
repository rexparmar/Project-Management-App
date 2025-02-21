package com.pm.pmapp.contoller;

import com.pm.pmapp.model.Message;
import com.pm.pmapp.model.Project;
import com.pm.pmapp.model.User;
import com.pm.pmapp.response.MessageResponse;
import com.pm.pmapp.service.ProjectService;
import com.pm.pmapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectsById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project projects = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project,user);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updatedProject(project,projectId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId,user.getId());
        MessageResponse res = new MessageResponse("Project deleted successfully!");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }



}
