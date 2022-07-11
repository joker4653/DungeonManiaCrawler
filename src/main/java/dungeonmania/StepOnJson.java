package dungeonmania;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class StepOnJson {
    public static ArrayList<String> getStepLogic(String type) {
        try {
            String path = "src/main/java/dungeonmania/data/" + type + ".txt";

            File types = new File(path);
                
            Scanner reader = new Scanner(types);

            ArrayList<String> listOfTypes = new ArrayList<>();
            while (reader.hasNextLine()) {
                String str = reader.nextLine();
                listOfTypes.add(str);
            } 

            reader.close();

            return listOfTypes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }



}
