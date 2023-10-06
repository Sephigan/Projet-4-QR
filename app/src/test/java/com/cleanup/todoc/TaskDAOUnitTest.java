package com.cleanup.todoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
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
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TaskDAOUnitTest {
    private TaskDao taskDao;
    private ProjectDao projectDao;
    private AppDatabase appDatabase;

    @Before
    public void initDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
        AppDatabase.setInstance(appDatabase);
        appDatabase.getTypeConverter(Converters.class);
        taskDao = appDatabase.taskDao();
        projectDao = appDatabase.projectDao();
    }

    public static void setInstance(AppDatabase db) {
        Converters.setDatabaseInstance(db);
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void insertProject_DAO(){
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        assertEquals("Projet Tartampion", projectDao.getProjectById(1L).getName());
    }

    @Test
    public void insertTask_DAO(){
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        final List<Task>[] tmpTask = new List<Task>[];
        taskDao.getTasks().observeForever(tasks -> {
            tmpTask[0] = tasks;
            assertNotNull(tasks);
            assertEquals(1, tasks.size());
            assertEquals("Test add", tasks.get(0).getName());
        });
    }

    @Test
    public void getTask_DAO(){
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(testTask, tasks.get(0));
        });
    }

    @Test
    public void deleteTask_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(1, tasks.size());
        });
        taskDao.deleteTask(testTask);
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(0, tasks.size());
        });
    }

    @Test
    public void orderAZ_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "A Test add", new Date().getTime());
        Task testTask2 = new Task(projectDao.getProjectById(1L), "C Test add", new Date().getTime());
        Task testTask3 = new Task(projectDao.getProjectById(1L), "B Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        taskDao.insertTask(testTask3);
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(3, tasks.size());
            assertEquals(testTask, tasks.get(0));
            assertEquals(testTask2, tasks.get(1));
            assertEquals(testTask3, tasks.get(2));
        });
        taskDao.orderAlphaAZ();
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(3, tasks.size());
            assertEquals(testTask, tasks.get(0));
            assertEquals(testTask3, tasks.get(1));
            assertEquals(testTask2, tasks.get(2));
        });
    }

    @Test
    public void orderZA_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "A Test add", new Date().getTime());
        Task testTask2 = new Task(projectDao.getProjectById(1L), "C Test add", new Date().getTime());
        Task testTask3 = new Task(projectDao.getProjectById(1L), "B Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        taskDao.insertTask(testTask3);
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(3, tasks.size());
            assertEquals(testTask, tasks.get(0));
            assertEquals(testTask2, tasks.get(1));
            assertEquals(testTask3, tasks.get(2));
        });
        taskDao.orderAlphaZA();
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(3, tasks.size());
            assertEquals(testTask2, tasks.get(0));
            assertEquals(testTask3, tasks.get(1));
            assertEquals(testTask, tasks.get(2));
        });
    }

    @Test
    public void orderCreationAsc_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "A Test add", new Date().getTime());
        Task testTask2 = new Task(projectDao.getProjectById(1L), "C Test add", new Date().getTime());
        Task testTask3 = new Task(projectDao.getProjectById(1L), "B Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        taskDao.insertTask(testTask3);
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(3, tasks.size());
            assertEquals(testTask, tasks.get(0));
            assertEquals(testTask2, tasks.get(1));
            assertEquals(testTask3, tasks.get(2));
        });
        taskDao.orderAlphaAZ();
        taskDao.orderCreationAsc();
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(3, tasks.size());
            assertEquals(testTask, tasks.get(0));
            assertEquals(testTask2, tasks.get(1));
            assertEquals(testTask3, tasks.get(2));
        });
    }

    @Test
    public void orderCreationDesc_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "A Test add", new Date().getTime());
        Task testTask2 = new Task(projectDao.getProjectById(1L), "C Test add", new Date().getTime());
        Task testTask3 = new Task(projectDao.getProjectById(1L), "B Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        taskDao.insertTask(testTask3);
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(3, tasks.size());
            assertEquals(testTask, tasks.get(0));
            assertEquals(testTask2, tasks.get(1));
            assertEquals(testTask3, tasks.get(2));
        });
        taskDao.orderCreationDesc();
        taskDao.getTasks().observeForever(tasks -> {
            assertEquals(3, tasks.size());
            assertEquals(testTask3, tasks.get(0));
            assertEquals(testTask2, tasks.get(1));
            assertEquals(testTask, tasks.get(2));
        });
    }
}

