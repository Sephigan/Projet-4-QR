package com.cleanup.todoc.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;


/**
 * Query = language SQL, fonctionnement et utilisations à étudier.
 */
@Dao
public class TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task) {

    }

    @Update
    void update(Task Task) {

    }

    @Query("SELECT * from Task ORDER By id Asc")
    LiveData<List<Task>> getTasks() {
        return null;
    }

    @Query("DELETE from Task")
    void deleteAll() {

    }
}
