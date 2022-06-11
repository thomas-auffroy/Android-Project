package Model.db;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface QuestionDao {


    @Query("SELECT * FROM question")
    List<Question> getAll();

    @Query("SELECT * FROM question WHERE gameId = :gameId AND questionId = :questionId")
    Question getQuestionFromIds(Integer gameId, Integer questionId);

    @Query("SELECT count(*) FROM question WHERE gameId = :gameId")
    Integer getNombreQuestions(Integer gameId);


    @Insert
    void insert(Question question);

    @Insert
    long[] insertAll(Question... questions);

    @Delete
    void delete(Question question);

    @Update
    void update(Question question);
}
