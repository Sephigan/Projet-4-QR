package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;

import static java.lang.Thread.sleep;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.typeconverter.Converters;
import com.cleanup.todoc.util.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.List;

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TaskDAOUnitTest {
    private TaskDao taskDao;
    private ProjectDao projectDao;
    private AppDatabase appDatabase;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

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
    public void insertTask_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        List<Task> test = LiveDataTestUtil.getOrAwaitValue(taskDao.getTasks());
        assertEquals(1, test.size());
        assertEquals("Test add", test.get(0).getName());
    }

    @Test
    public void getTask_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getTasks());
        assertEquals(testTask.getName(), tasks.get(0).getName());
    }

    @Test
    public void deleteTask_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getTasks());
        assertEquals(1, tasks.size());
        taskDao.deleteTask(tasks.get(0));
        tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getTasks());
        assertEquals(0, tasks.size());
    }

    @Test
    public void orderAZ_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "A Test add", new Date().getTime());
        sleep(100);
        Task testTask2 = new Task(projectDao.getProjectById(1L), "C Test add", new Date().getTime());
        sleep(100);
        Task testTask3 = new Task(projectDao.getProjectById(1L), "B Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        taskDao.insertTask(testTask3);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getTasks());
        assertEquals(3, tasks.size());
        assertEquals("A Test add", tasks.get(0).getName());
        assertEquals("C Test add", tasks.get(1).getName());
        assertEquals("B Test add", tasks.get(2).getName());
        tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.orderAlphaAZ());
        assertEquals(3, tasks.size());
        assertEquals("A Test add", tasks.get(0).getName());
        assertEquals("B Test add", tasks.get(1).getName());
        assertEquals("C Test add", tasks.get(2).getName());
    }

    @Test
    public void orderZA_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "A Test add", new Date().getTime());
        sleep(100);
        Task testTask2 = new Task(projectDao.getProjectById(1L), "C Test add", new Date().getTime());
        sleep(100);
        Task testTask3 = new Task(projectDao.getProjectById(1L), "B Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        taskDao.insertTask(testTask3);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getTasks());
        assertEquals(3, tasks.size());
        assertEquals("A Test add", tasks.get(0).getName());
        assertEquals("C Test add", tasks.get(1).getName());
        assertEquals("B Test add", tasks.get(2).getName());
        tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.orderAlphaZA());
        assertEquals(3, tasks.size());
        assertEquals("C Test add", tasks.get(0).getName());
        assertEquals("B Test add", tasks.get(1).getName());
        assertEquals("A Test add", tasks.get(2).getName());
    }

    @Test
    public void orderCreationAsc_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "A Test add", new Date().getTime());
        sleep(100);
        Task testTask2 = new Task(projectDao.getProjectById(1L), "C Test add", new Date().getTime());
        sleep(100);
        Task testTask3 = new Task(projectDao.getProjectById(1L), "B Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        taskDao.insertTask(testTask3);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getTasks());
        assertEquals(3, tasks.size());
        assertEquals("A Test add", tasks.get(0).getName());
        assertEquals("C Test add", tasks.get(1).getName());
        assertEquals("B Test add", tasks.get(2).getName());
        tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.orderCreationAsc());
        assertEquals(3, tasks.size());
        assertEquals("A Test add", tasks.get(0).getName());
        assertEquals("C Test add", tasks.get(1).getName());
        assertEquals("B Test add", tasks.get(2).getName());
    }

    @Test
    public void orderCreationDesc_DAO() throws InterruptedException {
        Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(p1);
        Task testTask = new Task(projectDao.getProjectById(1L), "A Test add", new Date().getTime());
        sleep(100);
        Task testTask2 = new Task(projectDao.getProjectById(1L), "C Test add", new Date().getTime());
        sleep(100);
        Task testTask3 = new Task(projectDao.getProjectById(1L), "B Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.insertTask(testTask2);
        taskDao.insertTask(testTask3);
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getTasks());
        assertEquals(3, tasks.size());
        assertEquals("A Test add", tasks.get(0).getName());
        assertEquals("C Test add", tasks.get(1).getName());
        assertEquals("B Test add", tasks.get(2).getName());
        tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.orderCreationDesc());
        assertEquals(3, tasks.size());
        assertEquals("B Test add", tasks.get(0).getName());
        assertEquals("C Test add", tasks.get(1).getName());
        assertEquals("A Test add", tasks.get(2).getName());
    }
}

