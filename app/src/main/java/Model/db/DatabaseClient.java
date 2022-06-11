package Model.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

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


            // Create Game Anglais
            db.execSQL("INSERT INTO game (categorie, description, name, typeJeu) VALUES ('Anglais', 'Traduire un mot français en anglais', 'Translation', 'qcm')");

            class GetId extends AsyncTask<Void, Void, Integer> {

                @Override
                protected Integer doInBackground(Void... voids) {
                    Integer id = AppDatabase.gameDao().getIdFromName("Translation");
                    return id;
                }

                @Override
                protected void onPostExecute(Integer id) {
                    Integer questionId = 0;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Pomme\" ?',"+questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Pineapple', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Triangle', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Waffle', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Apple', 1)");


                    questionId = 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Poire\" ?', "+questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Pineapple', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Triangle', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Waffle', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Pear', 1)");

                    questionId = 2;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Voiture\" ?', "+questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Bike', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Bus', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Metro', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Car', 1)");

                    questionId = 3;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Maison\" ?', "+questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Home', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Building', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'Flat', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+questionId+", 'House', 1)");
                }
            }

            // Executer tache asynchrone
            GetId getId = new GetId();
            getId.execute();





            //
            //db.execSQL("INSERT INTO game (categorie, description, name, typeJeu) VALUES ('Mathématiques', 'Les espaces vectoriels en un click', 'Equa Diff','qcm')");
            db.execSQL("INSERT INTO game (categorie, description, name, typeJeu) VALUES ('Mathématiques', 'Les tables de multiplication', 'Multiplication','maths')");
            //db.execSQL("INSERT INTO game (categorie, description, name, typeJeu) VALUES ('Anglais', 'yes', 'PineApple','qcm')");
            //db.execSQL("INSERT INTO game (categorie, description, name, typeJeu) VALUES ('Français', 'oui', 'Diantre','qcm')");
            //db.execSQL("INSERT INTO user (email, prenom, nom, motDePasse, dateNaissance, srcImage) VALUES(\"test@gmail.com\", \"Théo\", \"Hauray\", \"tt\", \"1997-12-29\", \"test\");");

            /*
            db.execSQL("INSERT INTO question (question) VALUES ('Test de question ?')");
            db.execSQL("INSERT INTO question (question) VALUES ('Test de queazdzastion ?')");
            db.execSQL("INSERT INTO question (question) VALUES ('Test de quezadzadazdstion ?')");
            db.execSQL("INSERT INTO question (question) VALUES ('Test de queazdazdazstion ?')");
            db.execSQL("INSERT INTO question (question) VALUES ('Tazdazdaest de question ?')");
            */

            // User test
            db.execSQL("INSERT INTO user (email, prenom, nom, motDePasse, dateNaissance, srcImage) VALUES ('a@a', 'test', 'test', 'a', '2000-01-01', '')");
        }
    };
}
