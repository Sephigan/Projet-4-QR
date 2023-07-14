package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.DataRepository;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private final MutableLiveData<List<Task>> listLiveDataTask = new MutableLiveData<List<Task>>() {
    };
    private final LiveData<List<Project>> listLiveDataProject;

    /**
     * Créer un init qui fait les instantiations.
     * @param application
     */
    public DataViewModel(Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        listLiveDataTask.setValue(dataRepository.getAllTasks().getValue());
        listLiveDataProject = dataRepository.getAllProjects();
    }

    public LiveData<List<Task>> getAllTasksFromVm() { return listLiveDataTask; }

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