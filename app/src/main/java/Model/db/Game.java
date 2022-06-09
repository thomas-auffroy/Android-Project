package Model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Game implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "categorie")
    private String categorie;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "typeJeu")
    private String typeJeu;

    /*
     * Getters and Setters
     * */
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCategorie()
    {
        return categorie;
    }

    public void setCategorie(String email)
    {
        this.categorie = categorie;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTypeJeu()
    {
        return typeJeu;
    }

    public void setTypeJeu(String typeJeu)
    {
        this.typeJeu = typeJeu;
    }
}
