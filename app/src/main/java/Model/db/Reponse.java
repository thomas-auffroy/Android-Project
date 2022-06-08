package Model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Game.class,
                        parentColumns = "id",
                        childColumns = "gameId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Reponse implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private int gameId;

    @ColumnInfo(name = "reponse")
    private String reponse;

    @ColumnInfo(name = "estVrai")
    private boolean estVrai;


    /*
     * Getters and Setters
     * */
    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }

    public String getReponse() { return reponse; }
    public void setReponse(String reponse) { this.reponse = reponse;}

    public boolean getEstVrai() { return estVrai; }
    public  void setEstVrai(boolean estVrai) { this.estVrai = estVrai; }
}
