package com.cleanup.todoc.ui;

import static java.lang.String.valueOf;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.DataRepository;
import com.cleanup.todoc.viewmodel.DataViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class ListFragment extends Fragment implements TasksAdapter.DeleteTaskListener{

    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;
    @Nullable
    public AlertDialog dialog = null;
    @Nullable
    private Spinner dialogSpinner = null;
    @Nullable
    private EditText dialogEditText = null;
    @NonNull
    private TextView lblNoTasks;
    @NonNull
    private RecyclerView listTasks;
    private DataViewModel dataViewModel;
    private TasksAdapter dataAdapter;
    private List<Task> tmpList;

    /**
     * Il faut récupérer les valeurs du xml et les appliquer aux valeurs du java
     * if faut attacher le xml aux param java
     * il faut mettre à jour le delete
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.e("onCreate","on entre");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dataViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DataViewModel.class);
        dataViewModel.init();
        Log.e("onCreate","on sort");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        Log.e("onCreateOM","on entre");
        inflater.inflate(R.menu.actions, menu);
        Log.e("onCreateOM","on sort");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        listTasks = view.findViewById(R.id.container);
        lblNoTasks = view.findViewById(R.id.lbl_no_task);
        listTasks.setLayoutManager(new LinearLayoutManager(context));
        listTasks.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        view.findViewById(R.id.fab_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });
        Log.e("onVCreate","on sort");
        return view;
    }

    /**
     * DeleteTask à parametrer dans le repo.
     * @param task the task that needs to be deleted
     */
    @Override
    public void onDeleteTask(Task task) {
        dataViewModel.deleteTask(task);
        updateTasks();
    }

    private void initList(){
        Log.e("initlist","on entre");
        dataViewModel.getAllTasksFromVm().observe(getViewLifecycleOwner(), tasks ->
        {
            if (tasks != null && !tasks.isEmpty()) {
                tmpList=tasks;
                dataAdapter = new TasksAdapter(tmpList, this);
                listTasks.setAdapter(dataAdapter);
                updateTasks();
            }
        });
        Log.e("initlist","on sort");
    }

    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {
                // TODO: Replace this by id of persisted task
                long id = (long) (Math.random() * 50000);


                Task task = new Task(
                        id,
                        taskProject,
                        taskName,
                        new Date().getTime()
                );
                dataViewModel.insertTask(task);
                updateTasks();
                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else{
                dialogInterface.dismiss();
            }
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();
        dialog.show();
        dialogSpinner = dialog.findViewById(R.id.project_spinner);
        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        populateDialogSpinner();
    }

    private void populateDialogSpinner(){
        dataViewModel.getAllProjectsFromVm().observe(this, projects ->
        {
            final ArrayAdapter<Project> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, projects);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if (dialogSpinner != null) {
                dialogSpinner.setAdapter(adapter);
            }
        });
    }

    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity(), R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogEditText = null;
                dialogSpinner = null;
                dialog = null;
            }
        });
        dialog = alertBuilder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClick(dialog);
                    }
                });
            }
        });
        return dialog;
    }

    private void updateTasks() {
        Log.e("updateTasks","on entre");
        if (tmpList == null) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            Log.e("tmplist", ""+tmpList.size());
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
            switch (sortMethod) {
                case ALPHABETICAL:
                    dataViewModel.orderAlphaAZ();
                    break;
                case ALPHABETICAL_INVERTED:
                    dataViewModel.orderAlphaZA();
                    break;
                case RECENT_FIRST:
                    dataViewModel.orderCreationAsc();
                    break;
                case OLD_FIRST:
                    dataViewModel.orderCreationDesc();
                    break;

            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }
        updateTasks();
        return super.onOptionsItemSelected(item);
    }

    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }
}
