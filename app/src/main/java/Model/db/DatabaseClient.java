package Model.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseClient {
    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase AppDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // à l'aide du "Room database builder"
        // MyToDos est le nom de la base de données
        //AppDatabase = Room.databaseBuilder(context, AppDatabase.class, "Marelle").build();

        ////////// REMPLIR LA BD à la première création à l'aide de l'objet roomDatabaseCallback
        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
        AppDatabase = Room.databaseBuilder(context, AppDatabase.class, "Marelle").addCallback(roomDatabaseCallback).build();
    }

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return AppDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //
            db.execSQL("INSERT INTO game (categorie, description, name) VALUES ('Mathématiques', 'Les espaces vectoriels en un click', 'Equa Diff')");
            db.execSQL("INSERT INTO game (categorie, description, name) VALUES ('Anglais', 'yes', 'PineApple')");
            db.execSQL("INSERT INTO game (categorie, description, name) VALUES ('Français', 'oui', 'Diantre')");
            db.execSQL("INSERT INTO user (email, prenom, nom, motDePasse, dateNaissance, srcImage) VALUES(\"test@gmail.com\", \"Théo\", \"Hauray\", \"tt\", \"1997-12-29\", \"test\");");

        }
    };
}
