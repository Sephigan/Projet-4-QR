package com.cleanup.todoc.database;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.typeconverter.Converters;
import java.util.List;


@TypeConverters(Converters.class)
@Dao
public abstract class TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTask(Task task);

    @Query("SELECT * from Task ORDER By id Asc")
    public abstract LiveData<List<Task>> getTasks();

    @Delete(entity = Task.class)
    public abstract void deleteTask(Task task);

    @Query("SELECT * FROM Task ORDER BY name ASC")
    public abstract LiveData<List<Task>> orderAlphaAZ();

    @Query("SELECT * FROM Task ORDER BY name DESC")
    public abstract LiveData<List<Task>> orderAlphaZA();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
    public abstract LiveData<List<Task>> orderCreationAsc();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    public abstract LiveData<List<Task>> orderCreationDesc();
}
