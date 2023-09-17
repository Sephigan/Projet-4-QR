package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.typeconverter.Converters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.util.Log;


@RunWith(AndroidJUnit4.class)
public class TaskDAOUnitTest {
    private TaskDao taskDao;
    private ProjectDao projectDao;
    private AppDatabase appDatabase;

    Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);

    @Before
    public void initDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = AppDatabase.getTestDatabase(context);
        appDatabase.getTypeConverter(Converters.class);
        taskDao = appDatabase.taskDao();
        projectDao = appDatabase.projectDao();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            projectDao.insertProject(p1);
            List<Project> px = projectDao.getProjects().getValue();
        });
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void insertTask_DAO() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());

        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(testTask);
            latch.countDown();
        });

        latch.await();

        LiveData<List<Task>> tasksLiveData = taskDao.getTasks();
        List<Task> tasks = tasksLiveData.getValue();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test add", tasks.get(0).getName());
    }

    /*@Test
    public void getTask_DAO() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(testTask);
            latch.countDown();
        });

        latch.await();
        assertEquals(testTask, taskDao.getTasks().getValue().get(0));
    }

    @Test
    public void deleteTask_DAO() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(testTask);
            latch.countDown();
        });
        latch.await();
        LiveData<List<Task>> tasksLiveData = taskDao.getTasks();
        List<Task> tasks = tasksLiveData.getValue();
        assertEquals(1, tasks.size());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.deleteTask(testTask);
            latch.countDown();
        });
        latch.await();
        assertEquals(0, tasks.size());
    }

    @Test
    public void orderAZ_DAO() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        Task testTask2 = new Task(1, p1, "Add test", new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(testTask);
            taskDao.insertTask(testTask2);
            latch.countDown();
        });
        latch.await();
        LiveData<List<Task>> tasksLiveData = taskDao.orderAlphaAZ();
        List<Task> tasks = tasksLiveData.getValue();
        assertNotNull(tasks);
        assertEquals(testTask, taskDao.getTasks().getValue().get(1));
        assertEquals(testTask2, taskDao.getTasks().getValue().get(0));
    }

    @Test
    public void orderZA_DAO() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        Task testTask2 = new Task(1, p1, "Add test", new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(testTask);
            taskDao.insertTask(testTask2);
            latch.countDown();
        });
        latch.await();
        LiveData<List<Task>> tasksLiveData = taskDao.orderAlphaZA();
        List<Task> tasks = tasksLiveData.getValue();
        assertNotNull(tasks);
        assertEquals(testTask, taskDao.getTasks().getValue().get(0));
        assertEquals(testTask2, taskDao.getTasks().getValue().get(1));
    }

    @Test
    public void orderCreationAsc_DAO() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        Task testTask2 = new Task(1, p1, "Add test", new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(testTask);
            latch.countDown();
            taskDao.insertTask(testTask2);
            latch.countDown();
        });
        latch.await();
        LiveData<List<Task>> tasksLiveData = taskDao.orderCreationAsc();
        List<Task> tasks = tasksLiveData.getValue();
        assertNotNull(tasks);
        assertEquals(testTask, taskDao.getTasks().getValue().get(0));
        assertEquals(testTask2, taskDao.getTasks().getValue().get(1));
    }

    @Test
    public void orderCreationDesc_DAO() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        Task testTask2 = new Task(1, p1, "Add test", new Date().getTime());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(testTask);
            latch.countDown();
            taskDao.insertTask(testTask2);
            latch.countDown();
        });
        latch.await();
        LiveData<List<Task>> tasksLiveData = taskDao.orderCreationDesc();
        List<Task> tasks = tasksLiveData.getValue();
        assertNotNull(tasks);
        assertEquals(testTask, taskDao.getTasks().getValue().get(1));
        assertEquals(testTask2, taskDao.getTasks().getValue().get(0));
    }*/
}

