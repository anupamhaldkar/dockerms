package com.code.editor.problemservice.controller;

import com.code.editor.problemservice.model.Problem;
import com.code.editor.problemservice.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemController {
    @Autowired
    private ProblemRepository problemRepository;

    @PostMapping
    public Problem createProblem(@RequestBody Problem problem) {
        return problemRepository.save(problem);
    }

    @GetMapping("/{id}")
    public Problem getProblem(@PathVariable String id) {
        return problemRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    @PutMapping("/{id}")
    public Problem updateProblem(@PathVariable String id, @RequestBody Problem problem) {
        problem.setProblemId(id);
        return problemRepository.save(problem);
    }

    @DeleteMapping("/{id}")
    public String deleteProblem(@PathVariable String id) {
        problemRepository.deleteById(id);
        return "Problem deleted successfully";
    }
}
