package help;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static boolean checkIfCommand(String text) {
        return text.startsWith("!");
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

    public static String getCommandName(String text) {
        String[] words = text.split(" ");

        return words[0];
    }

    public static String getFirstWord(String string)
    {
        return (string+" ").split(" ")[0];
    }

    public static ArrayList<String> getCommandProps(String text) {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(text.split(" ")));
        words.remove(0);
        return words;
    }



}

