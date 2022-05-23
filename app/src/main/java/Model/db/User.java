package Model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull private String email;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "motDePasse")
    private String motDePasse;

    @ColumnInfo(name = "dateNaissance")
    private String dateNaissance;

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

    public String getMotDePasse()
    {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse)
    {
        this.motDePasse = motDePasse;
    }

    public String getDateNaissance()
    {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance)
    {
        this.dateNaissance = dateNaissance;
    }

    public String getSrcImage()
    {
        return srcImage;
    }

    public void setSrcImage(String srcImage)
    {
        this.srcImage = srcImage;
    }
}
