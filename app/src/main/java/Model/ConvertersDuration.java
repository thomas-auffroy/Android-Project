package Model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.Duration;

public class ConvertersDuration {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Duration fromLongToDur(Long seconds) {
        return seconds == null ? null : Duration.ofSeconds(seconds);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long fromDurToLong(Duration duration) {
        return duration == null ? null : duration.getSeconds();
    }
}
