package com.code.editor.problemservice.repository;

import com.code.editor.problemservice.model.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem, String> {
}