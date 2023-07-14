package com.cleanup.todoc.typeconverter;

import static com.cleanup.todoc.model.Project.getAllProjects;

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

    @TypeConverter
    public static Project fromId(long id) {
        for (Project project : getAllProjects()) {
            if (project.getId() == id)
                return project;
        }
        return null;
    }
}
