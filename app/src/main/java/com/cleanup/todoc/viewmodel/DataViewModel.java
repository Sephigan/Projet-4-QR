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

    public DataViewModel(Application application) {
        super(application);
        dataRepository = new DataRepository(application);
    }

    public void init(){
        Log.e("init","on entre");
        listLiveDataTask = dataRepository.getAllTasks();
        listLiveDataProject = dataRepository.getAllProjects();
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

    public void deleteTask(Task task){
        dataRepository.deleteTask(task);
    }

    public void orderAlphaAZ(){
        dataRepository.orderAlphaAZ();
    }

    public void orderAlphaZA(){
        dataRepository.orderAlphaZA();
    }

    public void orderCreationAsc(){
        dataRepository.orderCreationAsc();
    }

    public void orderCreationDesc(){
        dataRepository.orderCreationDesc();
    }
}