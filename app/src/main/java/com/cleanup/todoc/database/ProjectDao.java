package com.cleanup.todoc.database;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.typeconverter.Converters;

import java.util.List;


@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public default void insertProject(Project project) {
    }

    @Update
    default void update(Project project) {

    }

    @Query("SELECT * from Project ORDER By id Asc")
    public default LiveData<List<Project>> getProjects() {
        return null;
    }

    @Query("DELETE from Project")
    default void deleteAll() {

    }
}
