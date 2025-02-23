package com.pm.pmapp.contoller;

import com.pm.pmapp.DTO.IssueDTO;
import com.pm.pmapp.model.Issue;
import com.pm.pmapp.model.User;
import com.pm.pmapp.request.IssueRequest;
import com.pm.pmapp.response.AuthResponse;
import com.pm.pmapp.response.MessageResponse;
import com.pm.pmapp.service.IssueService;
import com.pm.pmapp.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;


    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue, @RequestHeader("Authorization") String
                                                token) throws Exception {
        User tokenUser = userService.findUserProfileByJwt(token);
        User user =userService.findUserById(tokenUser.getId());
            Issue createdIssue = issueService.createIssue(issue,tokenUser);
            IssueDTO dto = new IssueDTO();
            dto.setDescription(createdIssue.getDescription());
            dto.setDueDate(createdIssue.getDueDate());
            dto.setId(createdIssue.getId());
            dto.setPriority(createdIssue.getPriority());
            dto.setProject(createdIssue.getProject());
            dto.setProjectId(createdIssue.getProjectId());
            dto.setStatus(createdIssue.getStatus());
            dto.setTitle(createdIssue.getTitle());
            dto.setTags(createdIssue.getTags());
            dto.setAssignee(createdIssue.getAssignee());
            return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{/issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
                                                                @RequestHeader("Authorization") String token) throws Exception{
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId,user.getId());
        MessageResponse res = new MessageResponse("Issue deleted Successfully!");
        return ResponseEntity.ok(res);
    }

    @PutMapping("{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,
                                                @PathVariable Long userId) throws Exception{
        Issue issue = issueService.addUserToIssue(issueId,userId);
        return  ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable String status,
                                                   @PathVariable Long issueId) throws Exception{
        Issue issue = issueService.updateStatus(issueId,status);
        return ResponseEntity.ok(issue);
    }
}
