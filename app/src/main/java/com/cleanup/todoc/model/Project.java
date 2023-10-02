package com.cleanup.todoc.model;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.cleanup.todoc.repository.DataRepository;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "Project") // This table name should match what you use in your database
public class Project {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColorInt
    @ColumnInfo(name = "color")
    private int color;

    public Project(@NonNull String name, @ColorInt int color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Returns all the projects of the application.
     *
     * @return all the projects of the application
     */
    @NonNull
    public static Project[] getAllProjects() {
        Project p1 = new Project( "Projet Tartampion", 0xFFEADAD1);
        Project p2 = new Project("Projet Lucidia", 0xFFB4CDBA);
        Project p3 = new Project("Projet Circus", 0xFFA3CED2);
        return new Project[]{p1,p2,p3};
    }

    /**
     * Returns the project with the given unique identifier, or null if no project with that
     * identifier can be found.
     *
     * @param id the unique identifier of the project to return
     * @return the project with the given unique identifier, or null if it has not been found
     */
    @Nullable
    public static Project getProjectById(long id) {
        for (Project project : getAllProjects()) {
            if (project.id == id)
                return project;
        }
        return null;
    }

    /**
     * Returns the unique identifier of the project.
     *
     * @return the unique identifier of the project
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the name of the project.
     *
     * @return the name of the project
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Returns the hex (ARGB) code of the color associated to the project.
     *
     * @return the hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}
