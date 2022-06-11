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
                        entity = Question.class,
                        parentColumns = "questionId",
                        childColumns = "questionId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Reponse implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "reponseId")
    private Integer reponseId;

    @ColumnInfo(name = "questionId")
    private Integer questionId;

    @ColumnInfo(name = "reponse")
    private String reponse;

    @ColumnInfo(name = "estVrai")
    private Integer estVrai;


    /*
     * Getters and Setters
     * */
    public Integer getQuestionId() { return questionId; }
    public void setQuestionId(int gameId) { this.questionId = gameId; }

    public Integer getReponseId() { return reponseId; }
    public void setReponseId(int reponseId) { this.reponseId = reponseId; }

    public String getReponse() { return reponse; }
    public void setReponse(String reponse) { this.reponse = reponse;}

    public Integer getEstVrai() { return estVrai; }
    public  void setEstVrai(Integer estVrai) { this.estVrai = estVrai; }
}
