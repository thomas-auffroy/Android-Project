package Model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import Model.ConvertersDate;

@Database(entities = {User.class, Game.class, Score.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract GameDao gameDao();
    public abstract ScoreDao scoreDao();

}

