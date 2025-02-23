package com.pm.pmapp.service;

import com.pm.pmapp.model.Issue;
import com.pm.pmapp.model.User;
import com.pm.pmapp.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
   Issue getIssueById(Long issueId) throws Exception;
    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issueRequest, User user) throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;
    Issue addUserToIssue(Long issueId, Long userId) throws Exception;
    Issue updateStatus(Long issueId, String status) throws Exception;

}
