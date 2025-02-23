package com.pm.pmapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {
    private String title;
    private String description;
    private String status;
    private long projectId;
    private String priority;
    private LocalDate dueDate;
}
