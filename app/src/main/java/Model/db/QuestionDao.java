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

    @Query("SELECT question FROM question")
    List<String> getAllQuestions();

    @Query("SELECT question FROM question WHERE gameId = :id")
    List<String> getAllQuestionsFromId(int id);

    @Query("SELECT question FROM question where gameId = :gameId AND questionId = :questionId")
    String getQuestionFromIds(Integer gameId, Integer questionId);

    @Query("SELECT gameId from question")
    List<Integer> getAllGameId();

    @Insert
    void insert(Question question);

    @Insert
    long[] insertAll(Question... questions);

    @Delete
    void delete(Question question);

    @Update
    void update(Question question);
}
