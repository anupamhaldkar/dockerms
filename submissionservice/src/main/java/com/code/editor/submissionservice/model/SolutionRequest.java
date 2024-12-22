package com.code.editor.submissionservice.model;

import lombok.Data;

@Data
public class SolutionRequest {
    private String userId;
    private String problemId;
    private String code;
    private String language;
    private int executionTimeout;
    private String input;
    private String expectedOutput;

    // Getters and setters
}