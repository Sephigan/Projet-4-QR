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
    private LiveData<List<Task>> listTasksAZ;
    private LiveData<List<Task>> listTasksZA;
    private LiveData<List<Task>> listTasksCTS;
    private LiveData<List<Task>> listTasksCTSR;

    public DataRepository(Application application) {
        appDatabase = AppDatabase.getDatabase(application);
        taskDao = appDatabase.taskDao();
        projectDao = appDatabase.projectDao();
        listTasks = taskDao.getTasks();
        listProjects = projectDao.getProjects();
        listTasksAZ = taskDao.orderAlphaAZ();
        listTasksZA = taskDao.orderAlphaZA();
        listTasksCTS = taskDao.orderCreationAsc();
        listTasksCTSR = taskDao.orderCreationDesc();
    }

    public void insertTask(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.insertTask(task));
    }

    public LiveData<List<Task>> getAllTasks() {
        return listTasks;
    }

    public void insertProject(Project project) {
        projectDao.insertProject(project);
    }

    public LiveData<List<Project>> getAllProjects() {
        return listProjects;
    }

    public void deleteTask(Long id){
        taskDao.deleteTask(id);
    }

    public LiveData<List<Task>> orderAlphaAZ()
    {
        return listTasksAZ;
    }

    public LiveData<List<Task>> orderAlphaZA()
    {
        return listTasksZA;
    }

    public LiveData<List<Task>> orderCreationAsc()
    {
        return listTasksCTS;
    }

    public LiveData<List<Task>> orderCreationDesc()
    {
        return listTasksCTSR;
    }

}