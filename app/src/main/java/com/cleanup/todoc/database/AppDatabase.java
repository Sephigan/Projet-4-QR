package com.cleanup.todoc.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.typeconverter.Converters;


@Database(entities = {Task.class, Project.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();
    private static volatile AppDatabase appDatabase;

    public static AppDatabase getDatabase(final Context context) {
        if (appDatabase == null) {
            synchronized (AppDatabase.class) {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database").createFromAsset("sampledata/projectDB.json").allowMainThreadQueries().build();
                }
            }
        }
        return appDatabase;
    }
}