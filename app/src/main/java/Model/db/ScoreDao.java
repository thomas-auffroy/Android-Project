package Model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

import Model.Converters.ConvertersDuration;
import Model.Converters.ConvertersMedails;

@Dao
@TypeConverters({ConvertersMedails.class})

public interface ScoreDao {
    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query("SELECT * FROM score WHERE user = :user")
    List<Score> getAllScoreFromUser(String user);

    @Query("SELECT * FROM score WHERE user = :user AND game = :game")
    Score getScoreGameUser(String user, int game);

    @Query("SELECT DISTINCT category FROM score WHERE user = :user")
    List<String> getCategoryFromScore(String user);

    @Query("SELECT DISTINCT game FROM score WHERE user = :user")
    List<Integer> getGameFromScore(String user);

    @Query("SELECT * FROM score WHERE user = :user AND score in (SELECT MAX(score) FROM score GROUP BY game, user) ORDER BY category")
    List<Score> getHighestScore(String user);

    @Insert
    void insert(Score score);

    @Insert
    long[] insertAll(Score... scores);

    @Delete
    void delete(Score score);

    @Update
    void update(Score score);
}
