package com.pm.pmapp.service;


import com.pm.pmapp.model.Chat;
import com.pm.pmapp.model.Project;
import com.pm.pmapp.model.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, User user) throws Exception;
    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;
    Project getProjectById(Long projectId) throws Exception;
    void deleteProject(Long projectId, Long userId) throws Exception;
    Project updatedProject(Project updatedProject,Long id) throws Exception;
    void addUserToProject(Long projectId,Long userId) throws Exception;
    void removeUserToProject(Long projectId,Long userId) throws Exception;
    Chat getChatByProjectId(Long projectId) throws Exception;
    List<Project> searchProject(String keyword, User user) throws  Exception;
}
