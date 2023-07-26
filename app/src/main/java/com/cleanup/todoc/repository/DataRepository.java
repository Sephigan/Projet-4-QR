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

    public DataRepository(Application application) {
        appDatabase = AppDatabase.getDatabase(application);
        taskDao = appDatabase.taskDao();
        projectDao = appDatabase.projectDao();
        listTasks = taskDao.getTasks();
        listProjects = projectDao.getProjects();
    }

    public void insertTask(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.insertTask(task));
    }

    public LiveData<List<Task>> getAllTasks() {
        return listTasks;
    }

    public void insertProject(Project project) {
        AppDatabase.databaseWriteExecutor.execute(() -> projectDao.insertProject(project));
    }

    public LiveData<List<Project>> getAllProjects() {
        return listProjects;
    }

    public void deleteTask(Task task){

    }

    public void orderAlphaAZ()
    {
        taskDao.orderAlphaAZ();
    }

    public void orderAlphaZA()
    {
        taskDao.orderAlphaZA();
    }
    public void orderCreationAsc()
    {
        taskDao.orderCreationAsc();
    }

    public void orderCreationDesc()
    {
        taskDao.orderCreationDesc();
    }

}