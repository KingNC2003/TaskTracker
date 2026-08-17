package com.project.tasktracker.service;

import com.project.tasktracker.model.Task;
import com.project.tasktracker.model.TaskStatus;
import com.project.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.project.tasktracker.model.Priority;
import com.project.tasktracker.exception.TaskNotFoundException;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TaskService{
    private final TaskRepository taskRepository;
    private final DateTimeService dateTimeService;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository,
        DateTimeService dateTimeService){
        this.taskRepository = taskRepository;
        this.dateTimeService = dateTimeService;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    public Task createTask(Task task) {
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(dateTimeService.getCurrentDateTime());

        Task savedTask = taskRepository.save(task);
        logger.info(
                "Task created: id={}, title={}",
                savedTask.getId(),
                savedTask.getTitle()
        );
        return savedTask;
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = getTaskById(id);
        updatedTask.setId(id);
        updatedTask.setCreatedAt(existingTask.getCreatedAt());
        return taskRepository.update(updatedTask);
    }

    public boolean deleteTask(Long id){
        return taskRepository.deleteById(id);
    }

    public List<Task> filterTasks(
        String search,
        TaskStatus status,
        Priority priority) {

        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : taskRepository.findAll()) {

            boolean matchesSearch =
                    search == null ||
                    search.isBlank() ||
                    task.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                    task.getDescription().toLowerCase().contains(search.toLowerCase());

            boolean matchesStatus =
                    status == null ||
                    task.getStatus() == status;

            boolean matchesPriority =
                    priority == null ||
                    task.getPriority() == priority;

            if (matchesSearch && matchesStatus && matchesPriority) {
                filteredTasks.add(task);
            }
        }
    return filteredTasks;
}
}