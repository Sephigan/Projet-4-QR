package com.cleanup.todoc.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repository.DataRepository;

public class DataViewModelFactory implements ViewModelProvider.Factory {
    private final DataRepository dataRepository;
    public DataViewModelFactory(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DataViewModel.class)) {
            return (T) new DataViewModel(dataRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}