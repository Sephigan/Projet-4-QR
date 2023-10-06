package com.cleanup.todoc.typeconverter;

import static com.cleanup.todoc.model.Project.getAllProjects;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.model.Project;

public class Converters {
    private static AppDatabase database;

    public static void setDatabaseInstance(AppDatabase db) {
        database = db;
    }

    @TypeConverter
    public static long fromProject(Project project) {
        if (project == null) {
            return 0;
        }
        return project.getId();
    }

    @TypeConverter
    public static Project fromId(long id) {
        if(database != null) {
            return database.projectDao().getProjectById(id);
        }
        return null;
    }
}
