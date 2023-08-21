package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repository.DataRepository;

public class DataViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final DataRepository dataRepository;
    public DataViewModelFactory(Application application, DataRepository dataRepository) {
        this.application = application;
        this.dataRepository = dataRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DataViewModel.class)) {
            return (T) new DataViewModel(application, dataRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}