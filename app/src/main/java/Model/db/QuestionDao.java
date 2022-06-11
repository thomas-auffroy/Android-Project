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

    @Query("SELECT * FROM question WHERE gameId = :gameId")
    List<Question> getAllQuestionsFromGameId(Integer gameId);

    @Query("SELECT count(*) FROM question WHERE gameId = :gameId")
    Integer getNombreQuestions(Integer gameId);

    @Query("SELECT * FROM question WHERE gameId = :gameId ORDER BY RANDOM() LIMIT :limit") // Permet de récupérer ':limit' ligne de manière random
    List<Question> getAllQuestionsFromGameId(Integer gameId, Integer limit);



    @Insert
    void insert(Question question);

    @Insert
    long[] insertAll(Question... questions);

    @Delete
    void delete(Question question);

    @Update
    void update(Question question);
}
