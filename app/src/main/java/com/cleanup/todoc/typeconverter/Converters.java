package com.cleanup.todoc.typeconverter;

import androidx.room.TypeConverter;

import com.cleanup.todoc.model.Project;

public class Converters {
    @TypeConverter
    public static Long fromProject(Project project) {
        if (project==null) {
            return(null);
        }
        return(project.getId());
    }
}
