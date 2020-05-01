package help;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public  class PrivateConstReader {

public static String getConstFromFile(String path){
    try {
        File file = new File(path);
        Scanner tokenReader = new Scanner(file);
        return tokenReader.nextLine();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return null;
}

}
