package Model.db;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

public class DatabaseClient {
    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    private static Integer questionId = 0;

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

            // Create QcmGame Anglais
            db.execSQL("INSERT INTO game (categorie, description, name, typeJeu) VALUES ('Anglais', 'Traduire un mot français en anglais', 'Translation', 'qcm')");

            class InitGameTranslation extends AsyncTask<Void, Void, Integer> {

                @Override
                protected Integer doInBackground(Void... voids) {
                    Integer id = AppDatabase.gameDao().getIdFromName("Translation");
                    return id;
                }

                @Override
                protected void onPostExecute(Integer id) {

                    // 20 jeux de traduction pour 'translation' pour le moment
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Pomme\" ?',"+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Pineapple', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Triangle', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Waffle', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Apple', 1)");


                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Poire\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Pineapple', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Triangle', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Waffle', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Pear', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Voiture\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Bike', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Bus', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Metro', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Car', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Maison\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Home', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Building', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Flat', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'House', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Chien\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Cat', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Frog', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Bot', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Dog', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Chat\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Frog', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Dog', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Bot', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Cat', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Vélo\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Boat', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Dream', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Sky', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Bike', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Rouge\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Blood', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Purple', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Green', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Red', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Ordinateur\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Bot', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Screen', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Keyboard', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Computer', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Chaise\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Cheer', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Fat', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Black', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Chair', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Chaussures\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Feet', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Foot', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Basket', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Shoes', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Pieds\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Foot', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'FootBall', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Fat', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Feet', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Poisson\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Poison', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Tail', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Bird', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Fish', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Arbre\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Zero', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'One', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Two', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Three', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Homme\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Men', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Human', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Women', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Man', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Jour\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Night', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Dawn', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Twilight', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Day', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Mot\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Ward', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Wolf', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'White', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Word', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Pouvoir\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Powder', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Pear', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Party', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Power', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Chaleur\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Eat', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Cold', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Hoven', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Heat', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'How do we say \"Beauté\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Ugly', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Mother', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Shape', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Beauty', 1)");
                }
            }
            InitGameTranslation initGameTranslation = new InitGameTranslation();
            initGameTranslation.execute();


            // Create QcmGame Français
            db.execSQL("INSERT INTO game (categorie, description, name, typeJeu) VALUES ('Français', 'Questions de culture générale', 'Culture générale', 'qcm')");

            class InitGameCulture extends AsyncTask<Void, Void, Integer> {

                @Override
                protected Integer doInBackground(Void... voids) {
                    Integer id = AppDatabase.gameDao().getIdFromName("Culture générale");
                    return id;
                }

                @Override
                protected void onPostExecute(Integer id) {

                    //10 jeux de traduction pour 'culture générale' pour le moment

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'Combien de planètes il y a t-il dans notre système solaire ?',"+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '10', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '7', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '9', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '8', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'Combien de pattes peut avoir au maximum le mille-pattes ?',"+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '1350', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '115', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '950', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '750', 1)");


                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'Quel bâtiment de Rome est habité par des centaines de chats ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Le Panthéon', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Le Vatican', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Le Vésuve', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Le Colisée', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'Quelle est la somme de deux faces opposées sur un dé à jouer ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '5', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Ca dépend des faces', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '9', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", '7', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'Pourquoi y a-t-il u trou sur les bouchons des stylos \"bic\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Pour que l\"encre ne sèche pas', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Pour faire du bruit quand on souffle dedans', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Pour faire des économies de plastique', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Par mesure de séurité', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'Comment s\"appeleait la chienne embarquée à bord de \"Spoutnik II\" ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Aghas', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Agna', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Malika', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Laika', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'Au Moyen Age, on appelait tranchoir', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Un couteau', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Une hache', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Un espadon', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Une tranche de pain', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'A quel continent appartient la Sierra Leone ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'L\"Amérique centrale', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'L\"Amérique du sud', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'L\"Asie', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'L\"Afrique', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'Comment appelle-t-on un sol fertille très riche en humus ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Le houmous', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'L\"erg', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'La latérite', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'La terre noire', 1)");

                    DatabaseClient.questionId += 1;
                    db.execSQL("INSERT INTO question(gameId, question, questionId) VALUES ("+id+", 'Qu\"est ce que la Mangrove ?', "+DatabaseClient.questionId+")");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Un animal terrestre', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Un poisson des mers chaudes', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Un plat typique Alsacien', 0)");
                    db.execSQL("INSERT INTO reponse(questionId, reponse, estVrai) VALUES ("+DatabaseClient.questionId+", 'Une formation arborescente dans les lagunes', 1)");

                }
            }
            InitGameCulture initGameCulture = new InitGameCulture();
            initGameCulture.execute();





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
