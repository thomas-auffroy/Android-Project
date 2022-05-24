package Model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import Model.Converters;

@Database(entities = {User.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}

