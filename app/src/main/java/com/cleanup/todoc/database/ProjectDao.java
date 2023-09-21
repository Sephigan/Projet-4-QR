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
public abstract class ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertProject(Project project);

    @Query("SELECT * from Project ORDER By id Asc")
    public abstract LiveData<List<Project>> getProjects();

}
