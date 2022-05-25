package Model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.Duration;

import Model.Converters.ConvertersDuration;
import Model.Converters.ConvertersMedails;
import Model.Enum.Medails;

@Entity(
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

@TypeConverters({ConvertersDuration.class, ConvertersMedails.class})

public class Score implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String user;

    @ColumnInfo(name = "game")
    private int game;

    @ColumnInfo(name = "score")
    private float score;

    @ColumnInfo(name = "temps")
    private Duration temps;

    @ColumnInfo(name = "medaille")
    private Medails medaille;

    /*
     * Getters and Setters
     * */
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

    public Duration getTemps()
    {
        return temps;
    }

    public void setTemps(Duration temps)
    {
        this.temps = temps;
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
