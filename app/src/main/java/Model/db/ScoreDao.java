package Model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

import Model.ConvertersDuration;
import Model.ConvertersMedails;

@Dao
@TypeConverters({ConvertersDuration.class, ConvertersMedails.class})

public interface ScoreDao {
    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query("SELECT * FROM score WHERE user = :user")
    List<Score> getAllScoreFromUser(String user);

    @Insert
    void insert(Score score);

    @Insert
    long[] insertAll(Score... scores);

    @Delete
    void delete(Score score);

    @Update
    void update(Score score);
}
