package com.code.editor.codeexecutionservice.service;

import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CodeExecutionService {

    public String executeCode(String javaCode) {
        String result;
        String className = "Solution"; // For simplicity, assume all Java code uses this class name

       // Step 1: Get system temporary directory path dynamically
    String tempDir = System.getProperty("java.io.tmpdir");
    Path javaFilePath = Paths.get(tempDir, className + ".java");

    try {
        // Write the Java code to the file in the system's temp directory
        Files.writeString(javaFilePath, javaCode);
    } catch (IOException e) {
        return "Error writing code to file: " + e.getMessage();
    }
        // Step 2: Define Docker commands for compiling and running Java code
        String dockerImage = "openjdk:11";
        String compileCommand = String.format("docker run --rm -v %s:/tmp %s javac /tmp/%s.java",
                javaFilePath.getParent(), dockerImage, className);
        String runCommand = String.format("docker run --rm -v %s:/tmp %s java -cp /tmp %s",
                javaFilePath.getParent(), dockerImage, className);

        // Step 3: Execute compile command in Docker
        result = executeDockerCommand(compileCommand);
        if (!result.isEmpty()) {
            return "Compilation error:\n" + result;
        }

        // Step 4: Execute run command in Docker
        result = executeDockerCommand(runCommand);
        if (!result.isEmpty()) {
            return "Execution output:\n" + result;
        }


        try {
            Files.deleteIfExists(javaFilePath);
        } catch (IOException e) {
            return "Error cleaning up code file: " + e.getMessage();
        }

        return "Unknown error during execution.";
    }

    private String executeDockerCommand(String command) {
        StringBuilder output = new StringBuilder();

        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            // Capture standard output
            try (BufferedReader stdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = stdOutput.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            // Capture standard error (if any)
            try (BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = stdError.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

        } catch (IOException | InterruptedException e) {
            return "Error executing Docker command: " + e.getMessage();
        }

        return output.toString().trim();
    }
}
