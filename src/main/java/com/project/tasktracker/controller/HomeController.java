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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import com.project.tasktracker.service.WeatherService;

@Controller
@RequestMapping("/tasks")
public class HomeController{

    private TaskService taskService;
    private final WeatherService weatherService;

    public HomeController(TaskService taskService,
        WeatherService weatherService){
        this.taskService = taskService;
        this.weatherService = weatherService;
    }

    @GetMapping
    public String showTasks(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) TaskStatus status,
        @RequestParam(required = false) Priority priority,
        @RequestParam(defaultValue = "1") int page,
        Model model) {

        List<Task> filteredTasks =
                taskService.filterTasks(search, status, priority);
        int pageSize = 10;
        int totalTasks = filteredTasks.size();
        int totalPages =
                (int) Math.ceil((double) totalTasks / pageSize);
        int currentPage = Math.max(
                1,
                Math.min(page, Math.max(totalPages, 1))
        );
        int startIndex =
                (currentPage - 1) * pageSize;
        int endIndex =
                Math.min(startIndex + pageSize, totalTasks);
        List<Task> pageTasks;
        if (totalTasks == 0) {
            pageTasks = List.of();
        } else {
            pageTasks =
                    filteredTasks.subList(startIndex, endIndex);
        }
        model.addAttribute("tasks", pageTasks);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("search", search);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedPriority", priority);

        model.addAttribute(
                "weather",
                weatherService.getCurrentWeather()
        );
        
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
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "task-form";
        }
        taskService.createTask(task);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Task created successfully."
        );
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
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            task.setId(id);
            return "edit-task";
        }
        taskService.updateTask(id, task);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Task updated successfully."
        );
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes) {
        taskService.deleteTask(id);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Task deleted successfully."
        );
        return "redirect:/tasks";
    }
}