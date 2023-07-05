package com.cleanup.todoc.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "Task")
public class Tasks {
    @PrimaryKey
    @NonNull
    long ids;
    @ForeignKey
    @NonNull
    long projectIds;
    String names;
    long creationTimeStamps;

    public Tasks(long ids, long projectIds, String names, long creationTimeStamps) {
        this.ids = ids;
        this.projectIds = projectIds;
        this.names = names;
        this.creationTimeStamps = creationTimeStamps;
    }

    public long getIds() {
        return ids;
    }

    public void setIds(long id) {
        this.ids = ids;
    }

    public long getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(long projectIds) {
        this.projectIds = projectIds;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public long getCreationTimeStamps() {
        return creationTimeStamps;
    }

    public void setCreationTimeStamps(long creationTimeStamps) {
        this.creationTimeStamps = creationTimeStamps;
    }
}

@Entity (tableName = "Project")
public class Projects {
    @PrimaryKey
    @NonNull
    long ids;
    String names;
    long colors;

    public Projects(long ids, String names, long colors) {
        this.ids = ids;
        this.names = names;
        this.colors = colors;
    }

    public long getIds() {
        return ids;
    }

    public void setIds(long ids) {
        this.ids = ids;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public long getColors() {
        return colors;
    }

    public void setColors(long colors) {
        this.colors = colors;
    }
}
