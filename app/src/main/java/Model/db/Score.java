package Model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

import Model.Converters.ConvertersDuration;
import Model.Converters.ConvertersMedails;
import Model.Enum.Medails;

@Entity

        /*(
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "email",
                        childColumns = "user",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Game.class,
                        parentColumns = "id",
                        childColumns = "game",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
*/

@TypeConverters({ConvertersMedails.class})

public class Score implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "user")
    private String user;

    @ColumnInfo(name = "game")
    private int game;

    @ColumnInfo(name = "score")
    private float score;

    @ColumnInfo(name = "medaille")
    private Medails medaille;

    @Ignore
    public static ArrayList<Score> scoresAnonyme;

    /*
     * Getters and Setters
     * */
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

    public int getGame()
    {
        return game;
    }

    public void setGame(int game)
    {
        this.game = game;
    }

    public float getScore()
    {
        return score;
    }

    public void setScore(float score)
    {
        this.score = score;
    }

    public Medails getMedaille()
    {
        return medaille;
    }

    public void setMedaille(Medails medaille)
    {
        this.medaille = medaille;
    }

}
