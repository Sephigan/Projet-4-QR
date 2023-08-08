package com.cleanup.todoc.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.DataRepository;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private LiveData<List<Task>> listLiveDataTask = new LiveData<List<Task>>() {
    };
    private LiveData<List<Project>> listLiveDataProject;
    private LiveData<List<Task>> listLiveDataProjectAZ = new LiveData<List<Task>>() {
    };;
    private LiveData<List<Task>> listLiveDataProjectZA = new LiveData<List<Task>>() {
    };;
    private LiveData<List<Task>> listLiveDataProjecCTS = new LiveData<List<Task>>() {
    };;
    private LiveData<List<Task>> listLiveDataProjectCTSR = new LiveData<List<Task>>() {
    };;

    public DataViewModel(Application application) {
        super(application);
        dataRepository = new DataRepository(application);
    }

    public void init(){
        Log.e("init","on entre");
        listLiveDataTask = dataRepository.getAllTasks();
        listLiveDataProject = dataRepository.getAllProjects();
        listLiveDataProjectAZ = dataRepository.orderAlphaAZ();
        listLiveDataProjectZA = dataRepository.orderAlphaZA();
        listLiveDataProjecCTS = dataRepository.orderCreationAsc();
        listLiveDataProjectCTSR = dataRepository.orderCreationDesc();
        Log.e("init","on sort");
    }

    public LiveData<List<Task>> getAllTasksFromVm() { return listLiveDataTask; }

    public LiveData<List<Project>> getAllProjectsFromVm() {
        return listLiveDataProject;
    }

    public void insertTask(Task task) {
        dataRepository.insertTask(task);
    }

    public void insertProject(Project project) { dataRepository.insertProject(project); }

    public void deleteTask(Long id){
        dataRepository.deleteTask(id);
    }

    public LiveData<List<Task>> orderAlphaAZ(){
        return listLiveDataProjectAZ;
    }

    public LiveData<List<Task>> orderAlphaZA(){
        return listLiveDataProjectZA;
    }

    public LiveData<List<Task>> orderCreationAsc(){
        return listLiveDataProjecCTS;
    }

    public LiveData<List<Task>> orderCreationDesc(){
        return listLiveDataProjectCTSR;
    }
}