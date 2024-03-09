package com.example.projectmanagement.services;

import java.util.List;
import java.util.Optional;

import com.example.projectmanagement.models.Project;

public interface ProjectServices {
    List<Project> getAllProjects();
    Optional<Project> getProjectById(Long id);
    Project createProject(Project project);
    Project updateProject(Long id, Project project);
    void deleteProject(Long id);
}

