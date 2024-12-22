package com.code.editor.problemservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "problems")
@Data
public class Problem {
    @Id
    private String problemId;
    private String title;
    private String description;
    private String difficulty;
    private String[] tags;
    private String createdBy;

    // Getters and Setters
}
