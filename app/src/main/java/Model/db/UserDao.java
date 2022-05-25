package Model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

import Model.Converters.ConvertersDate;

@Dao
@TypeConverters({ConvertersDate.class})

public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT email FROM user")
    List<String> getAllEmails();

    @Insert
    void insert(User user);

    @Insert
    long[] insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}
