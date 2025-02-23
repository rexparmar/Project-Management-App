package com.pm.pmapp.repository;

import com.pm.pmapp.model.Issue;
import com.pm.pmapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue,Long> {
    Optional<Issue> findById(Long issueId);
    List<Issue> findByProjectId(Long projectId);
}
