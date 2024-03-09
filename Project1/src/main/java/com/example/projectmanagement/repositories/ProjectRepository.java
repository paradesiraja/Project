package com.example.projectmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagement.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {


	
}

