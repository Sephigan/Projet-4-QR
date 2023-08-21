package com.cleanup.todoc;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repository.DataRepository;
import com.cleanup.todoc.viewmodel.DataViewModel;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import android.content.Context;


/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskUnitTest {

    @Mock
    private AppDatabase database;
    @Mock
    private TaskDao taskDao;
    @Mock
    private ProjectDao projectDao;
    @Mock
    Context context;
    @InjectMocks
    private DataViewModel dVM;
    @InjectMocks
    private DataRepository dataRepo;

    Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
    Project p2 = new Project(2L, "Projet Lucidia", 0xFFB4CDBA);
    Project p3 = new Project(3L, "Projet Circus", 0xFFA3CED2);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(database.taskDao()).thenReturn(taskDao);
        when(database.projectDao()).thenReturn(projectDao);
        dataRepo = new DataRepository(taskDao,projectDao);
        dVM = new DataViewModel(this, dataRepo);
    }

    /*@Test
    public void testAddTask() {
        doNothing().when(taskDao).insertTask(any(Task.class));
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        tDVM.insertTask(testTask);
        verify(taskDao, times(1)).insertTask(eq(testTask));
    }

    @Test
    public void test_projects() {
        final Task task1 = new Task(1, p1, "task 1", new Date().getTime());
        final Task task2 = new Task(2, p2, "task 2", new Date().getTime());
        final Task task3 = new Task(3, p3, "task 3", new Date().getTime());

        assertEquals("Projet Tartampion", task1.getProject().getName());
        assertEquals("Projet Lucidia", task2.getProject().getName());
        assertEquals("Projet Circus", task3.getProject().getName());
    }

    @Test
    public void supp_Task() throws InterruptedException {
        doNothing().when(taskDao).deleteTask(any(Task.class));

        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        tDVM.insertTask(testTask);
        Thread.sleep(100);
        tDVM.deleteTask(testTask);
        Thread.sleep(100);
        verify(taskDao, times(1)).deleteTask(eq(testTask));
    }

    /*@Test
    public void filter_Task(){
        doNothing().when(taskDao).orderAlphaAZ();
        doNothing().when(taskDao).orderAlphaZA();
        doNothing().when(taskDao).orderCreationAsc();
        doNothing().when(taskDao).orderCreationDesc();
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        Task testTask2 = new Task(2, p2, "Test add2", new Date().getTime());
        taskRepository.insertTask(testTask);
        taskRepository.insertTask(testTask2);
        taskRepository.orderAlphaAZ();
        taskRepository.orderAlphaZA();
        taskRepository.orderCreationAsc();
        taskRepository.orderCreationDesc();
        verify(taskDao, times(1)).orderAlphaAZ();
        verify(taskDao, times(1)).orderAlphaZA();
        verify(taskDao, times(1)).orderCreationAsc();
        verify(taskDao, times(1)).orderCreationDesc();
    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(1, p1, "aaa", 123);
        final Task task2 = new Task(2, p2, "zzz", 124);
        final Task task3 = new Task(3, p3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1, p1, "aaa", 123);
        final Task task2 = new Task(2, p2, "zzz", 124);
        final Task task3 = new Task(3, p3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1, p1, "aaa", 123);
        final Task task2 = new Task(2, p2, "zzz", 124);
        final Task task3 = new Task(3, p3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1, p1, "aaa", 123);
        final Task task2 = new Task(2, p2, "zzz", 124);
        final Task task3 = new Task(3, p3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }*/
}