package com.code.editor.submissionservice.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Service
public class SubmissionService {

    @Autowired
    private RestTemplate restTemplate;

    // private final String codeExecutionServiceUrl = "http://codeexecutionservice:8083/execute/code";

    private final String codeExecutionServiceUrl = "http://localhost:8083/execute/code";

    public String submitSolution(String code, String language, String input, int executionTime, String expectedOutput) {
        // Add test case to the code
        String codeWithTestCase = generateTestCode(code, input);

        // Call CodeExecutionService
        String actualOutput = restTemplate.postForObject(codeExecutionServiceUrl, codeWithTestCase, String.class);

        // Validate actual output
        if (actualOutput != null && actualOutput.trim().equals(expectedOutput)) {
            return "Test Passed";
        } else {
            return "Test Failed: Expected " + expectedOutput + " but got " + actualOutput;
        }
    }

    private String generateTestCode(String userCode, String testCaseInput) {
        // Wraps user's code with a main method to execute it
        return String.format(
                "import java.util.*;\n" +
                        "public class Solution {\n" +
                        "%s\n" +
                        "    public static void main(String[] args) {\n" +
                        "        // Convert test case input to required format\n" +
                        "        System.out.println(new Solution().yourMethod(%s));\n" +  // Call to user's method
                        "    }\n" +
                        "}",
                userCode, testCaseInput
        );
    }
}


