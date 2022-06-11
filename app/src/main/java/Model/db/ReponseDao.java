package Model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ReponseDao {
    @Query("SELECT * FROM reponse")
    List<Reponse> getAll();

    @Query("SELECT * FROM reponse WHERE questionId=:id")
    List<Reponse> getAllReponsesFromQuestionId(int id);

    @Insert
    void insert(Reponse reponse);

    @Insert
    long[] insertAll(Reponse... reponses);

    @Delete
    void delete(Reponse reponse);

    @Update
    void update(Reponse reponsettttbbb);
}
