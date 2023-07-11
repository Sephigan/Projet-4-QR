package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DataViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    public DataViewModelFactory(Application myApplication) {
        application = myApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DataViewModel(application);
    }
}