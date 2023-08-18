package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TestRepository {

    private static TaskDao taskDao;
    private static ProjectDao projectDao;

    public TestRepository(AppDatabase appDatabase) {
        taskDao = appDatabase.taskDao();
        projectDao = appDatabase.projectDao();
    }

    public static void insertTask(Task task) {
        taskDao.insertTask(task);
    }

    public void insertProject(Project project) {
        projectDao.insertProject(project);
    }

    public static void deleteTask(Task task){
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.deleteTask(task));
    }
    public LiveData<List<Task>> orderAlphaAZ()
    {
        return taskDao.orderAlphaAZ();
    }

    public LiveData<List<Task>> orderAlphaZA()
    {
        return taskDao.orderAlphaZA();
    }

    public LiveData<List<Task>> orderCreationAsc()
    {
        return taskDao.orderCreationAsc();
    }

    public LiveData<List<Task>> orderCreationDesc()
    {
        return taskDao.orderCreationDesc();
    }

}
