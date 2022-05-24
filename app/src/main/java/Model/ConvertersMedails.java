package Model;

import androidx.room.TypeConverter;

public class ConvertersMedails {

    @TypeConverter
    public static Medails fromStringToMedails(String medaille) {
        switch (medaille)
        {
            case "PARTICIPATION" :
                return Medails.PARTICIPATION;
            case "BRONZE" :
                return Medails.BRONZE;
            case "ARGENT" :
                return Medails.ARGENT;
            case "OR" :
                return Medails.OR;
            case "PLATINE" :
                return Medails.PLATINE;
            default:
                return Medails.VIDE;
        }
    }

    @TypeConverter
    public static String fromMedailsToString(Medails medaille) {
        switch (medaille)
        {
            case PARTICIPATION :
                return "PARTICIPATION";
            case BRONZE:
                return "BRONZE";
            case ARGENT:
                return "ARGENT";
            case OR:
                return "OR";
            case PLATINE:
                return "PLATINE";
            default:
                return "VIDE";
        }
    }
}
