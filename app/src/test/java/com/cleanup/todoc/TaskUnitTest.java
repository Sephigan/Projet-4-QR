package com.cleanup.todoc;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.ui.TasksAdapter;
import com.cleanup.todoc.viewmodel.DataViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import android.app.Application;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {

    private AppDatabase database;
    private TaskDao taskDao;
    Project p1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
    Project p2 = new Project(2L, "Projet Lucidia", 0xFFB4CDBA);
    Project p3 = new Project(3L, "Projet Circus", 0xFFA3CED2);

    @Before
    public void setupDatabase(){
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase.class
        ).allowMainThreadQueries().build();

        taskDao = database.taskDao();
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
    public void add_Task(){
        int tableCount = taskDao.countTasks();
        int tableCountAfter;
        Task testTask = new Task(1, p1, "Test add", new Date().getTime());
        taskDao.insertTask(testTask);
        tableCountAfter = taskDao.countTasks();
        assertEquals(tableCountAfter, tableCount+1);
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

    @After
    public void closeDatabase(){
        database.close();
    }
}