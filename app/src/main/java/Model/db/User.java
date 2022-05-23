package Model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private String email;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "motDePasse")
    private String motDePasse;

    @ColumnInfo(name = "dateNaissance")
    private Date dateNaissance;

    @ColumnInfo(name = "image")
    private String srcImage;


    /*
     * Getters and Setters
     * */
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

}
