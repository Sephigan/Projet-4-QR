package com.cleanup.todoc.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class DataRepository {
    AppDatabase appDatabase;
    TaskDao taskDao;
    ProjectDao projectDao;

    private LiveData<List<Project>> listProjects;
    private LiveData<List<Task>> listTasks;

    public DataRepository(TaskDao taskDao, ProjectDao projectDao) {
        //A Faire
    }

    public void insertTask(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.insertTask(task));
    }

    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getTasks();
    }

    public void insertProject(Project project) {
        projectDao.insertProject(project);
    }

    public LiveData<List<Project>> getAllProjects() {
        return projectDao.getProjects();
    }

    public void deleteTask(Task task){
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.deleteTask(task));
    }

    public LiveData<List<Task>> orderAlphaAZ()
    {
        listTasks = taskDao.orderAlphaAZ();
        return listTasks;
    }

    public LiveData<List<Task>> orderAlphaZA()
    {
        listTasks = taskDao.orderAlphaZA();
        return listTasks;
    }

    public LiveData<List<Task>> orderCreationAsc()
    {
        listTasks = taskDao.orderCreationAsc();
        return listTasks;
    }

    public LiveData<List<Task>> orderCreationDesc()
    {
        listTasks = taskDao.orderCreationDesc();
        return listTasks;
    }

}