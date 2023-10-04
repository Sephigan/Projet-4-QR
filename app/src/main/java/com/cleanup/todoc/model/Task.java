package com.cleanup.todoc.model;


import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.cleanup.todoc.typeconverter.Converters;

import java.util.Comparator;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "Task", foreignKeys = {@ForeignKey(entity = Project.class,
        parentColumns = "id",
        childColumns = "project",
        onDelete = ForeignKey.CASCADE)})
public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long id;

    /**
     * The unique identifier of the project associated to the task
     */
    @ColumnInfo(name = "project")
    @TypeConverters({Converters.class})
    Project project;

    /**
     * The name of the task
     */
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    /**
     * The timestamp when the task has been created
     */
    @ColumnInfo(name = "creationTimestamp")
    private long creationTimestamp;

    /**
     * Instantiates a new Task.
     *
     * @param project         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public Task(Project project, @NonNull String name, long creationTimestamp) {
        this.setProject(project);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the unique identifier of the project associated to the task.
     *
     * @param project the unique identifier of the project associated to the task to set
     */
    private void setProject(Project project) {
        this.project = project;
    }

    /**
     * Returns the project associated to the task.
     *
     * @return the project associated to the task
     */
    @Nullable
    public Project getProject() {
        return project;
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the name of the task to set
     */
    private void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Sets the timestamp when the task has been created.
     *
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getCreationTimestamp(){
        return creationTimestamp;
    }

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
