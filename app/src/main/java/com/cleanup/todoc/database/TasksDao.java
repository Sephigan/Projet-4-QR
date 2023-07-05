package com.cleanup.todoc.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public class TasksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tasks tasks);

    @Update
    void update(Tasks Tasks);

    @Query("SELECT * from tasks_table ORDER By id Asc")
    LiveData<List<Tasks>> getTasks();

    @Query("DELETE from tasks_table")
    void deleteAll();
}
