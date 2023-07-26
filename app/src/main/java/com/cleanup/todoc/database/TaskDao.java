package com.cleanup.todoc.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.typeconverter.Converters;

import java.util.List;


@TypeConverters(Converters.class)
@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public default void insertTask(Task task) {

    }

    @Update
    default void update(Task Task) {

    }

    @Query("SELECT * from Task ORDER By id Asc")
    LiveData<List<Task>> getTasks();

    @Query("DELETE from Task")
    default void deleteAll() {

    }

    @Query("SELECT * FROM Task ORDER BY name ASC")
    LiveData<List<Task>> orderAlphaAZ();

    @Query("SELECT * FROM Task ORDER BY name DESC")
    LiveData<List<Task>> orderAlphaZA();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> orderCreationAsc();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> orderCreationDesc();
}
