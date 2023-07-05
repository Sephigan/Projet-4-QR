import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.database.Tasks;
import com.cleanup.todoc.database.TasksDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Tasks.class}, version = 1, exportSchema = false)
public abstract class TasksRoomDatabase extends RoomDatabase {
    public abstract TasksDao studentDao();

    private static volatile TasksRoomDatabase studentRoomDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TasksRoomDatabase getDatabase(final Context context) {
        if (studentRoomDatabase == null) {
            synchronized (TasksRoomDatabase.class) {
                if (studentRoomDatabase == null) {
                    studentRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    TasksRoomDatabase.class, "student_database")
                            .build();
                }
            }
        }
        return studentRoomDatabase;
    }
}