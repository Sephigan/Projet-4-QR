package com.cleanup.todoc;

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

import static org.mockito.Mockito.*;

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
        taskDao = mock(TaskDao.class);
    }

    @After
    public void closeDb(){
        appDatabase.close();
    }

    @Test
    public void insertTask_DAO(){
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        verify(taskDao, times(1)).insertTask(eq(testTask));
    }

    @Test
    public void getTask_DAO(){
        taskDao.getTasks();
        verify(taskDao, times(1)).getTasks();
    }

    @Test
    public void deleteTask_DAO(){
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        taskDao.deleteTask(testTask);
        verify(taskDao, times(1)).deleteTask(eq(testTask));
    }

    @Test
    public void orderAZ_DAO(){
        taskDao.orderAlphaAZ();
        verify(taskDao, times(1)).orderAlphaAZ();
    }

    @Test
    public void orderZA_DAO(){
        taskDao.orderAlphaZA();
        verify(taskDao, times(1)).orderAlphaZA();
    }

    @Test
    public void orderCreationAsc_DAO(){
        taskDao.orderCreationAsc();
        verify(taskDao, times(1)).orderCreationAsc();
    }

    @Test
    public void orderCreationDesc_DAO(){
        taskDao.orderCreationDesc();
        verify(taskDao, times(1)).orderCreationDesc();
    }
}