package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.AppDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.DataRepository;
import com.cleanup.todoc.repository.TestRepository;

import java.util.List;

public class TestViewModel extends AndroidViewModel {
    private TestRepository testTaskRepository;
    private LiveData<List<Task>> listLiveDataTask = new LiveData<List<Task>>() {
    };
    private LiveData<List<Project>> listLiveDataProject;

    public TestViewModel(AppDatabase database) {
        super(new Application());
        testTaskRepository = new TestRepository(database);
    }

    public void init(){
        //listLiveDataTask = testTaskRepository.getAllTasks();
        //listLiveDataProject = testTaskRepository.getAllProjects();
    }

    public LiveData<List<Task>> getAllTasksFromVm() { return listLiveDataTask; }

    public LiveData<List<Project>> getAllProjectsFromVm() {
        return listLiveDataProject;
    }

    public void insertTask(Task task) { testTaskRepository.insertTask(task); }

    public void insertProject(Project project) { testTaskRepository.insertProject(project); }

    public void deleteTask(Task task){
        testTaskRepository.deleteTask(task);
    }

    public void orderAlphaAZ(){
        listLiveDataTask = testTaskRepository.orderAlphaAZ();
    }

    public void orderAlphaZA(){
        listLiveDataTask = testTaskRepository.orderAlphaZA();
    }

    public void orderCreationAsc(){
        listLiveDataTask = testTaskRepository.orderCreationAsc();
    }

    public void orderCreationDesc(){
        listLiveDataTask = testTaskRepository.orderCreationDesc();
    }
}
