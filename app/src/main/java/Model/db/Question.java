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
public class Question implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private int gameId;

    @ColumnInfo(name = "question")
    private String question;


    /*
     * Getters and Setters
     * */
    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question;}
}
