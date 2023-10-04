package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.typeconverter.Converters;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 4, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Executor getDatabaseWriteExecutor() {
        return executor;
    }

    private static volatile AppDatabase INSTANCE;

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    ProjectDao projectDao = INSTANCE.projectDao();

                    long[] id = {1L, 2L, 3L};
                    String[] names = {"Projet Tartampion", "Projet Lucidia", "Projet Circus"};
                    int[] colors = {0xFFEADAD1, 0xFFB4CDBA, 0xFFA3CED2};

                    for (int i = 0; i < names.length; i++) {
                        Project project = new Project(id[i], names[i], colors[i]);
                        projectDao.insertProject(project);
                    }
                }
            });
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}