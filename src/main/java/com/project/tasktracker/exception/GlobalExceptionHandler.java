package com.project.tasktracker.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFound(
            TaskNotFoundException exception,
            Model model) {

        model.addAttribute(
                "errorMessage",
                exception.getMessage()
        );

        return "error";
    }
}