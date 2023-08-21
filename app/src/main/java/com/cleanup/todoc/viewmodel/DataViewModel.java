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

public class DataViewModel{
    private DataRepository dataRepository;
    private LiveData<List<Task>> listLiveDataTask;
    private LiveData<List<Project>> listLiveDataProject;

    public DataViewModel(DataRepository dataRepo) {
        dataRepository = dataRepo;
        init();
    }

    public void init(){
        listLiveDataTask = dataRepository.getAllTasks();
        listLiveDataProject = dataRepository.getAllProjects();
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
        listLiveDataTask = dataRepository.orderAlphaAZ();
    }

    public void orderAlphaZA(){
        listLiveDataTask = dataRepository.orderAlphaZA();
    }

    public void orderCreationAsc(){
        listLiveDataTask = dataRepository.orderCreationAsc();
    }

    public void orderCreationDesc(){
        listLiveDataTask = dataRepository.orderCreationDesc();
    }
}