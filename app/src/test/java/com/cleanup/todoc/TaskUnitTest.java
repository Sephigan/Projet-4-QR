package com.cleanup.todoc;


import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.DataRepository;
import com.cleanup.todoc.repository.TestTaskRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import androidx.room.Room;

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
    Context context;

    @InjectMocks
    private TestTaskRepository taskRepository;
    Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
    Project p2 = new Project(2L, "Projet Lucidia", 0xFFB4CDBA);
    Project p3 = new Project(3L, "Projet Circus", 0xFFA3CED2);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(database.taskDao()).thenReturn(taskDao);

        taskRepository = new TestTaskRepository(database);
    }

    @Test
    public void testAddTask() {
        doNothing().when(taskDao).insertTask(any(Task.class));

        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        TestTaskRepository.insertTask(testTask);

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
    public void supp_Task(){
        /*
        check if task is in table
        supp task
        check is task isn't in table
        */
    }

    @Test
    public void filter_Task(){
        /*
        check table
        apply filter
        check table modification
        */
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
    }
}