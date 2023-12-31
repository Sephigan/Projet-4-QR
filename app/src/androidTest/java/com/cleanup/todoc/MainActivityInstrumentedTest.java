package com.cleanup.todoc;

import static android.os.SystemClock.sleep;

import android.view.View;
import android.widget.TextView;

import com.cleanup.todoc.ui.ListFragment;
import com.cleanup.todoc.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addAndRemoveTask() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.container);
        final int sizeList;
        if(listTasks.getAdapter() == null){
            sizeList = 0;
        }else{
            sizeList = listTasks.getAdapter().getItemCount();
        }
        //----------------Add a Task-------------------------------------------------------
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Task example"));
        onView(withId(android.R.id.button1)).perform(click());

        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        //----------------Assert a Task is added------------------------------------------
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(sizeList+1));

        //----------------Delete a Task---------------------------------------------------
        onView(withId(R.id.container)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new ActionOnDeleteButton()));

        sleep(100);
        //----------------Assert a Task is Deleted----------------------------------------
        if(sizeList == 0){
            assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
        }
        else{
            assertThat(listTasks.getAdapter().getItemCount(), equalTo(sizeList));
        }
    }

    @Test
    public void sortTasks() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.container);
        final int sizeList;
        if(listTasks.getAdapter() == null){
            sizeList = 0;
        }else{
            sizeList = listTasks.getAdapter().getItemCount();
        }

        for(int i=0; i<sizeList; i++){
            onView(withId(R.id.container)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new ActionOnDeleteButton()));
        }

        //----------------------Add Task to test filter-------------------------------------------
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        //----------------------Sort alphabetical------------------------------------------------
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        //----------------------Check positions onview-------------------------------------------
        onView(withRecyclerView(R.id.container).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.container).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.container).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));

        //----------------------Sort alphabetical inverted---------------------------------------
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        //----------------------Check positions onview-------------------------------------------
        onView(withRecyclerView(R.id.container).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.container).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.container).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

        //----------------------Sort old first--------------------------------------------------
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        //----------------------Check positions onview------------------------------------------
        onView(withRecyclerView(R.id.container).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.container).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.container).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        //----------------------Sort recent first-----------------------------------------------
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());
        //----------------------Check positions onview------------------------------------------
        onView(withRecyclerView(R.id.container).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.container).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.container).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
    }
}
