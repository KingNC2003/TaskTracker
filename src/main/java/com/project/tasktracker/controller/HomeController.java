package com.project.tasktracker.controller;

import com.project.tasktracker.service.TaskService;
import com.project.tasktracker.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.project.tasktracker.model.Priority;
import com.project.tasktracker.model.TaskStatus;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tasks")
public class HomeController{

    private TaskService taskService;

    public HomeController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public String showTasks(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) TaskStatus status,
        @RequestParam(required = false) Priority priority,
        Model model) {

        model.addAttribute("tasks", taskService.filterTasks(search, status, priority));
        model.addAttribute("search", search);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedPriority", priority);
        return "dashboard";
    }

    @GetMapping("/new")
    public String showCreateTaskForm(Model model){
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping
    public String createTask(
            @Valid @ModelAttribute("task") Task task,
            BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
                return "task-form";
            }
            taskService.createTask(task);
            return "redirect:/tasks";
        }
    
    @GetMapping("/{id}/edit")
    public String showEditTaskForm(
        @PathVariable Long id,
        Model model){
            Task task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            return "edit-task";
        }
    
    @PostMapping("/{id}/edit")
    public String updateTask(
            @PathVariable Long id,
            @Valid @ModelAttribute("task") Task task,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            task.setId(id);
            return "edit-task";
        }
        taskService.updateTask(id, task);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}