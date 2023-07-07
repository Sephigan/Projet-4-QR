package com.cleanup.todoc.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "Task")
public class Task {
    @PrimaryKey
    @NonNull
    long id;
    /**
     * Trouver comment définir une ForeignKey
     */
    @NonNull
    long projectId;
    String name;
    long creationTimeStamp;

    public Task(long id, long projectId, String name, long creationTimeStamp) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.creationTimeStamp = creationTimeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(long creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }
}
