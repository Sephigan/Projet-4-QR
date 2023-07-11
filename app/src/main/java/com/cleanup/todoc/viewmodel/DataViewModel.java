package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.DataRepository;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private final LiveData<List<Task>> listLiveDataTask;
    private final LiveData<List<Project>> listLiveDataProject;

    public DataViewModel(Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        listLiveDataTask = dataRepository.getAllTasks();
        listLiveDataProject = dataRepository.getAllProjects();
    }

    public LiveData<List<Task>> getAllTasksFromVm() {
        return listLiveDataTask;
    }

    public LiveData<List<Project>> getAllProjectsFromVm() {
        return listLiveDataProject;
    }

    public void insertTask(Task task) {
        dataRepository.insertTask(task);
    }

    public void insertProject(Project project) {
        dataRepository.insertProject(project);
    }
}