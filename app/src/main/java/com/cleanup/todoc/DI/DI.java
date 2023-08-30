package com.cleanup.todoc.DI;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.repository.DataRepository;
import com.cleanup.todoc.ui.ListFragment;
import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.viewmodel.DataViewModel;
import com.cleanup.todoc.viewmodel.DataViewModelFactory;

public class DI{
    private static AppDatabase appDatabase;
    private static TaskDao taskDao;
    private static ProjectDao projectDao;
    private static DataRepository dataRepo;

    public static void init(Application application){
        appDatabase = AppDatabase.getDatabase(application);
        taskDao = appDatabase.taskDao();
        projectDao = appDatabase.projectDao();
        dataRepo = new DataRepository(taskDao, projectDao);
    }

    public static DataRepository getDataRepo(){
        return dataRepo;
    }
}
