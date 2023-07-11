package com.cleanup.todoc.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.DataViewHolder> {
    ArrayList<Task> taskArrayList;
    ArrayList<Project> projectArrayList;

    public DataListAdapter(ArrayList<Task> tasks, ArrayList<Project> projects) {
        this.taskArrayList = tasks;
        this.projectArrayList = projects;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyclerview_item_list, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.tvTaskId.setText(toString(taskArrayList.get(position).getId()));
        holder.tvTaskProjectId.setText(toString(taskArrayList.get(position).getProject()));
        holder.tvTaskName.setText(taskArrayList.get(position).getName());
        holder.tvTaskCreationTimeStamp.setText(toString(taskArrayList.get(position).getCreationTimestamp()));
        holder.tvProjectId.setText(toString(projectArrayList.get(position).getId()));
        holder.tvProjectName.setText(projectArrayList.get(position).getName());
        holder.tvProjectColor.setText(toString(projectArrayList.get(position).getColor()));
    }

    public int getTaskItemCount() {
        return taskArrayList.size();
    }
    public int getProjectItemCount() {
        return projectArrayList.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName;
        TextView tvStudentLastName;
        TextView tvStudentAddress;
        TextView tvStudentMarks;
        TextView tvProjectId;
        TextView tvProjectName;
        TextView tvProjectColor;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews();
        }

        private void findViews() {
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentLastName = itemView.findViewById(R.id.tvStudentLastName);
            tvStudentAddress = itemView.findViewById(R.id.tvStudentAddress);
            tvStudentMarks = itemView.findViewById(R.id.tvStudentMarks);
        }
    }