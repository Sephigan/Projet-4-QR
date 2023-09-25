package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.DataRepository;
import com.cleanup.todoc.viewmodel.DataViewModel;

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
public class DVMUnitTest {
    @InjectMocks
    private DataViewModel dVM;
    @Mock
    private DataRepository dataRepo;


    Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dataRepo = mock(DataRepository.class);
        dVM = new DataViewModel(dataRepo);
    }

    @Test
    public void testAddTask() throws InterruptedException {
        doNothing().when(dataRepo).insertTask(any(Task.class));
        Task testTask = new Task(p1, "Test add", new Date().getTime());
        Thread.sleep(100);
        dVM.insertTask(testTask);
        Thread.sleep(100);
        verify(dataRepo, times(1)).insertTask(eq(testTask));
    }

    @Test
    public void supp_Task() throws InterruptedException {
        doNothing().when(dataRepo).deleteTask(any(Task.class));
        Task testTask = new Task(p1, "Test add", new Date().getTime());
        dVM.deleteTask(testTask);
        Thread.sleep(100);
        verify(dataRepo, times(1)).deleteTask(eq(testTask));
    }

    @Test
    public void filterAZ_Task(){
        dVM.orderAlphaAZ();
        verify(dataRepo, times(1)).orderAlphaAZ();
    }

    @Test
    public void filterZA_Task(){
        dVM.orderAlphaZA();
        verify(dataRepo, times(1)).orderAlphaZA();
    }

    @Test
    public void filterCAsc_Task(){
        dVM.orderCreationAsc();
        verify(dataRepo, times(1)).orderCreationAsc();
    }
    @Test
    public void filterCDes_Task(){
        dVM.orderCreationDesc();
        verify(dataRepo, times(1)).orderCreationDesc();
    }

}
