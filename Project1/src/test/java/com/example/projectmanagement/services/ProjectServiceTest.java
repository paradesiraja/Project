package com.example.projectmanagement.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.projectmanagement.models.Project;
import com.example.projectmanagement.repositories.ProjectRepository;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "Project 1", "Description 1"));
        projects.add(new Project(2L, "Project 2", "Description 2"));

        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetProjectById() {
        Project project = new Project(1L, "Project 1", "Description 1");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Optional<Project> result = projectService.getProjectById(1L);
        assertEquals(project, result.get());
    }

    
}
