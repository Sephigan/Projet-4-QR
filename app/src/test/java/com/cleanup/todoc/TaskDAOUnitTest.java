package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.typeconverter.Converters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.util.Log;


@RunWith(AndroidJUnit4.class)
public class TaskDAOUnitTest {
    @InjectMocks
    private TaskDao taskDao;
    private AppDatabase appDatabase;

    Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);

    @Before
    public void initDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = AppDatabase.getTestDatabase(context);
        if (appDatabase == null) {
            throw new IllegalStateException("appDatabase is null");
        }
        appDatabase.getTypeConverter(Converters.class);
        taskDao = appDatabase.taskDao();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appDatabase.projectDao().insertProject(p1);
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
            long taskId = taskDao.insertTask(testTask);
            Log.d("TaskDAOUnitTest", "Inserted task with ID: " + taskId);
            latch.countDown();
        });

        latch.await();

        LiveData<List<Task>> tasksLiveData = taskDao.getTasks();
        if (tasksLiveData == null) {
            throw new IllegalStateException("tasksLiveData is null");
        }
        List<Task> tasks = tasksLiveData.getValue();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test add", tasks.get(0).getName());
    }
}
    //A FAIRE
    /*@Test
    public void getTask_DAO(){
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        LiveData<List<Task>> tasksLiveData = taskDao.getTasks();
        Observer<List<Task>> observer = tasks -> {
            assertEquals(1, tasks.size());
        };
    }

    @Test
    public void deleteTask_DAO(){
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        LiveData<List<Task>> tasksLiveData = taskDao.getTasks();
        Observer<List<Task>> observer = tasks -> {
            assertEquals(1, tasks.size());
        };
        taskDao.deleteTask(testTask);
        Observer<List<Task>> observer2 = tasks -> {
            assertEquals(0, tasks.size());
        };
    }

    @Test
    public void orderAZ_DAO(){
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        Task testTask2 = new Task(1, p1, "Add test", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        LiveData<List<Task>> tasksLiveData = taskDao.orderAlphaAZ();
        Observer<List<Task>> observer = tasks -> {
            assertEquals(2, tasks.size());
            assertEquals(testTask2, tasks.get(0));
            assertEquals(testTask, tasks.get(1));
        };
    }

    @Test
    public void orderZA_DAO(){
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        Task testTask2 = new Task(1, p1, "Add test", new Date().getTime());
        taskDao.insertTask(testTask2);
        taskDao.insertTask(testTask);
        LiveData<List<Task>> tasksLiveData = taskDao.orderAlphaZA();
        Observer<List<Task>> observer = tasks -> {
            assertEquals(2, tasks.size());
            assertEquals(testTask2, tasks.get(0));
            assertEquals(testTask, tasks.get(1));
        };
    }

    @Test
    public void orderCreationAsc_DAO(){
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        Task testTask2 = new Task(1, p1, "Add test", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        LiveData<List<Task>> tasksLiveData = taskDao.orderCreationAsc();
        Observer<List<Task>> observer = tasks -> {
            assertEquals(2, tasks.size());
            assertEquals(testTask2, tasks.get(0));
            assertEquals(testTask, tasks.get(1));
        };
    }

    @Test
    public void orderCreationDesc_DAO(){
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        Task testTask2 = new Task(1, p1, "Add test", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        LiveData<List<Task>> tasksLiveData = taskDao.orderCreationDesc();
        Observer<List<Task>> observer = tasks -> {
            assertEquals(2, tasks.size());
            assertEquals(testTask, tasks.get(0));
            assertEquals(testTask2, tasks.get(1));
        };
    }*/
