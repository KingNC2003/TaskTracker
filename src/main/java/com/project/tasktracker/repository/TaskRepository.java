package com.project.tasktracker.repository;

import com.project.tasktracker.model.Task;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository{
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public List<Task> findAll(){
        return new ArrayList<>(tasks);
    }

    public Task save(Task task){
        tasks.add(task);
        task.setId(nextId++);
        return task;
    }

    public Task findById(Long id){
        return tasks.stream().filter(task->task.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean deleteById(Long id){
        return tasks.removeIf(task -> task.getId().equals(id));
    }

    public Task update(Task updatedTask){
        for (int i=0; i<tasks.size(); i++){
            Task existingTask = tasks.get(i);
            
            if (updatedTask.getId().equals(existingTask.getId())){
                tasks.set(i,updatedTask);
                return updatedTask;
            }
        }
        return null;
    }
}