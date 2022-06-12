package Model.db;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;



@Dao
public interface GameDao {



    @Query("SELECT * FROM game")
    List<Game> getAll();

    @Query("SELECT * FROM game WHERE categorie = :categorie")
    List<Game> getAllFromCategory(String categorie);

    @Query("SELECT categorie FROM game GROUP BY categorie")
    List<String> getAllCategory();

    @Query("SELECT id FROM game WHERE name = :name")
    Integer getIdFromName(String name);

    @Query("SELECT * FROM game WHERE name = :name")
    Game getGame(String name);

    @Query("SELECT * FROM game WHERE id = :id")
    Game getGameById(int id);





    @Insert
    void insert(Game game);

    @Insert
    long[] insertAll(Game... games);

    @Delete
    void delete(Game game);

    @Update
    void update(Game game);
}
