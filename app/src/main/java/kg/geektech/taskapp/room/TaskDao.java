package kg.geektech.taskapp.room;





import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kg.geektech.taskapp.models.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();

    @Insert
    void insert(Task task);

    @Query("SELECT * FROM Task ORDER BY title ASC")
    LiveData<List<Task>> sortAsc();

    @Delete
    void remove(Task task);

}
