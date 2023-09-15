package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import com.cleanup.todoc.LiveDataTestUtil;


@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TaskDAOUnitTest {
    private TaskDao taskDao;
    private AppDatabase appDatabase;

    Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);

    @Before
    public void initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        taskDao = appDatabase.taskDao();
        appDatabase.projectDao().insertProject(p1);
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void insertTask_DAO() throws InterruptedException {
        Task testTask = new Task(2, p1, "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        LiveData<List<Task>> tasksLiveData = taskDao.getTasks();

        // Use LiveDataTestUtil to observe LiveData
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(tasksLiveData);

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
