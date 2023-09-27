package com.cleanup.todoc;

import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.DataRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class RepoUnitTest {
    @Mock
    private TaskDao taskDao;
    @Mock
    private ProjectDao projectDao;
    @InjectMocks
    private DataRepository dataRepo;

    Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        taskDao = mock(TaskDao.class);
        projectDao = mock(ProjectDao.class);
        dataRepo = new DataRepository(taskDao,projectDao);
    }

    @Test
    public void insertTask_Repo(){
        doNothing().when(taskDao).insertTask(any(Task.class));
        Task testTask = new Task(p1, "Test add", new Date().getTime());
        dataRepo.insertTask(testTask);
        verify(taskDao, times(1)).insertTask(eq(testTask));
    }

    @Test
    public void getTasks_Repo(){
        dataRepo.getAllTasks();
        verify(taskDao, times(1)).getTasks();
    }

    @Test
    public void deleteTask_Repo() throws InterruptedException {
        doNothing().when(taskDao).deleteTask(any(Task.class));
        Task testTask = new Task(p1, "Test add", new Date().getTime());
        dataRepo.insertTask(testTask);
        Thread.sleep(100);
        dataRepo.deleteTask(testTask);
        Thread.sleep(100);
        verify(taskDao, times(1)).deleteTask(eq(testTask));
    }

    //1 test par méthode
    @Test
    public void filterAZ_Task_Repo(){
        dataRepo.orderAlphaAZ();
        verify(taskDao, times(1)).orderAlphaAZ();
    }

    @Test
    public void filterZA_Task_Repo(){
        dataRepo.orderAlphaZA();
        verify(taskDao, times(1)).orderAlphaZA();
    }

    @Test
    public void filterCAsc_Task_Repo(){
        dataRepo.orderCreationAsc();
        verify(taskDao, times(1)).orderCreationAsc();
    }

    @Test
    public void filterCDesc_Task_Repo(){
        dataRepo.orderCreationDesc();
        verify(taskDao, times(1)).orderCreationDesc();
    }

}
