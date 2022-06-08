package Model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Game.class, Score.class, Question.class, Reponse.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract GameDao gameDao();
    public abstract ScoreDao scoreDao();
    public abstract QuestionDao questionDao();
    public abstract ReponseDao reponseDao();

}

