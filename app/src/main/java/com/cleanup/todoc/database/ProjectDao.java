package com.cleanup.todoc.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;


/**
 * Query = language SQL, fonctionnement et utilisations à étudier.
 */
@Dao
public class ProjectDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Project project) {

    }

    @Update
    void update(Project project) {

    }

    @Query("SELECT * from Project ORDER By id Asc")
    public LiveData<List<Project>> getProjects() {
        return null;
    }

    @Query("DELETE from Project")
    void deleteAll() {

    }
}
