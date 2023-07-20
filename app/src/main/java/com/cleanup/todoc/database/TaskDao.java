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


/**
 * Query = language SQL, fonctionnement et utilisations à étudier.
 */
@TypeConverters(Converters.class)
@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public default void insert(Task task) {

    }

    @Update
    default void update(Task Task) {

    }

    @Query("SELECT * from Task ORDER By id Asc")
    LiveData<List<Task>> getTasks();

    @Query("DELETE from Task")
    default void deleteAll() {

    }
}
