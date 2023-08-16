package com.cleanup.todoc.repository;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Task;

public class TestTaskRepository {

    private static TaskDao taskDao;

    public TestTaskRepository(AppDatabase appDatabase) {
        taskDao = appDatabase.taskDao();
    }

    public static void insertTask(Task task) {
        taskDao.insertTask(task);
    }

}
