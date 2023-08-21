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
    TaskDao myTaskDao;
    ProjectDao myProjectDao;

    private LiveData<List<Project>> listProjects;
    private LiveData<List<Task>> listTasks;

    public DataRepository(TaskDao taskDao, ProjectDao projectDao) {
        myTaskDao = taskDao;
        myProjectDao = projectDao;
    }

    public void insertTask(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> myTaskDao.insertTask(task));
    }

    public LiveData<List<Task>> getAllTasks() {
        return myTaskDao.getTasks();
    }

    public void insertProject(Project project) {
        myProjectDao.insertProject(project);
    }

    public LiveData<List<Project>> getAllProjects() {
        return myProjectDao.getProjects();
    }

    public void deleteTask(Task task){
        AppDatabase.databaseWriteExecutor.execute(() -> myTaskDao.deleteTask(task));
    }

    public LiveData<List<Task>> orderAlphaAZ()
    {
        listTasks = myTaskDao.orderAlphaAZ();
        return listTasks;
    }

    public LiveData<List<Task>> orderAlphaZA()
    {
        listTasks = myTaskDao.orderAlphaZA();
        return listTasks;
    }

    public LiveData<List<Task>> orderCreationAsc()
    {
        listTasks = myTaskDao.orderCreationAsc();
        return listTasks;
    }

    public LiveData<List<Task>> orderCreationDesc()
    {
        listTasks = myTaskDao.orderCreationDesc();
        return listTasks;
    }

}