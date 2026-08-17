package com.project.tasktracker.service;

import com.project.tasktracker.model.Task;
import com.project.tasktracker.model.TaskStatus;
import com.project.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.project.tasktracker.model.Priority;

import java.util.List;
import java.util.ArrayList;

@Service
public class TaskService{
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id);
    }

    public Task createTask(Task task){
        task.setStatus(TaskStatus.PENDING);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        updatedTask.setId(id);
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