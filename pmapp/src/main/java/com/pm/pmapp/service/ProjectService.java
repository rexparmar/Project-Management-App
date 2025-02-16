package com.pm.pmapp.service;


import com.pm.pmapp.model.Project;

public interface ProjectService {
    Project createProject(Project project, Long userId) throws Exception;



}
