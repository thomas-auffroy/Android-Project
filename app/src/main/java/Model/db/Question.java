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

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "questionId")
    private int questionId;

    @ColumnInfo(name = "gameId")
    private Integer gameId;

    @ColumnInfo(name = "question")
    private String question;


    /*
     * Getters and Setters
     * */
    public Integer getGameId() { return gameId; }
    public void setGameId(Integer gameId) { this.gameId = gameId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question;}

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
}
