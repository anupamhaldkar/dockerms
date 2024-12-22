package com.code.editor.submissionservice.controller;

import com.code.editor.submissionservice.model.SolutionRequest;
import com.code.editor.submissionservice.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/submit")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/solution")
    public ResponseEntity<Map<String, Object>> submitSolution(@RequestBody SolutionRequest solutionRequest) {

        // Extract parameters from request
        String code = solutionRequest.getCode();
        String language = solutionRequest.getLanguage();
        int executionTimeout = solutionRequest.getExecutionTimeout();
        String input = solutionRequest.getInput();
        String expectedOutput = solutionRequest.getExpectedOutput();
        String userId = solutionRequest.getUserId();
        String problemId = solutionRequest.getProblemId();

        // Execute the code (this would be a call to your code execution service)
        String result = submissionService.submitSolution(code, language, input, executionTimeout, expectedOutput);

        // Check if output matches expected output
        boolean isCorrect = expectedOutput.equals(result);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("problemId", problemId);
        response.put("result", result);
        response.put("status", isCorrect ? "Success" : "Failed");

        return ResponseEntity.ok(response);
    }
}

