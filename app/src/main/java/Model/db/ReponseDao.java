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

    @Query("SELECT reponse FROM reponse")
    List<String> getAllReponses();

    @Query("SELECT reponse FROM reponse WHERE gameId=:id")
    List<String> getAllReponsesFromId(int id);

    @Query("SELECT estVrai FROM reponse WHERE gameId=:id")
    boolean estVrai(int id); // Permet de récupérer la véracité d'une réponse en fonction d'un id de jeu / de question

    @Query("SELECT * FROM reponse WHERE gameId = :id")
    List<Reponse> getAllReponses(int id);


    @Insert
    void insert(Reponse reponse);

    @Insert
    long[] insertAll(Reponse... reponses);

    @Delete
    void delete(Reponse reponse);

    @Update
    void update(Reponse reponsettttbbb);
}
