package com.pm.pmapp.service;

import com.pm.pmapp.model.Comment;
import com.pm.pmapp.model.Issue;
import com.pm.pmapp.model.User;
import com.pm.pmapp.repository.CommentRepository;
import com.pm.pmapp.repository.IssueRepository;
import com.pm.pmapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository repo;

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private UserRepository userRepo;


    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepo.findById(issueId);
        Optional<User> userOptional = userRepo.findById(userId);

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        if(issueOptional.isEmpty()){
            throw new Exception("Issue not found with id : " + issueId);
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not found with id : "+ userId);
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setCreatedDate(LocalDateTime.now());
        comment.setIssue(issue);
        Comment savedComment = repo.save(comment);
        issue.getComments().add(savedComment);
        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> commentOptional = repo.findById(commentId);
        Optional<User> userOptional = userRepo.findById(userId);

        if(commentOptional.isEmpty()){
            throw new Exception("Comment not found!");
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not found!");
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if(comment.getUser().equals(user)){
            repo.delete(comment);
        }else{
            throw new Exception("User does not have permission to delete this comment!");
        }
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return repo.findByIssueId(issueId);
    }
}
