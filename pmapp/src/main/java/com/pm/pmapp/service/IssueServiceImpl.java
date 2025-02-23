package com.pm.pmapp.service;

import com.pm.pmapp.model.Issue;
import com.pm.pmapp.model.Project;
import com.pm.pmapp.model.User;
import com.pm.pmapp.repository.IssueRepository;
import com.pm.pmapp.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{

    @Autowired
    private IssueRepository repo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = repo.findById(issueId);
        if(issue.isPresent()){
            return issue.get();
        }
        throw new Exception("No issue found with id"+issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        projectService.getProjectById(projectId);
        return repo.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectId());
        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setProjectId(issueRequest.getProjectId());
        issue.setDueDate(issueRequest.getDueDate());
        issue.setPriority(issueRequest.getPriority());

        issue.setProject(project);
        return repo.save(issue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssueById(issueId);
        repo.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);
        return repo.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return repo.save(issue);
    }
}
