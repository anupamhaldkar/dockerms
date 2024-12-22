package com.code.editor.codeexecutionservice.controller;

import com.code.editor.codeexecutionservice.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/execute")
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    @Autowired
    public CodeExecutionController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @PostMapping("/code")
    public String executeCode(@RequestBody String code) {
        return codeExecutionService.executeCode(code);
    }
}

