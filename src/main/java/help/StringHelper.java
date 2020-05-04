package help;

public class StringHelper {

    public static String getTextBoldUnderlined(String text){
        return "__**"+text+"**__";
    }

    public static String getTextUnderlined(String text){
        return "__"+text+"__";
    }
    public static String getTextBold(String text){
        return "__"+text+"__";
    }

    public static String getTextBoldCursive(String text){
        return "***"+text+"***";
    }

    public static String getBreak(int amount){
        String lineBreaks = "";
        for (int i = 0; i < amount; i++) {
            lineBreaks +=  "\n";
        }
        return lineBreaks;
    }

    public static String getIdFromString(String text){
        return "";
    }

}

