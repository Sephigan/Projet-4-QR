package com.cleanup.todoc;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
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
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void insertTask_DAO(){
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        verify(taskDao, times(1)).insertTask(eq(testTask));
    }
}