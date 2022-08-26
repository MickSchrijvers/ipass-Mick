package nl.hu.ipass.controllerLaag;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Remover {

    public static void removeDish(int userId, int dishId, int recipeId){
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap> data = Initializer.getData();

        System.out.println(data);

        for (HashMap users : data){

            if ((int) users.get("id") == userId){
                ArrayList<HashMap> gerechten = ((HashMap<String, ArrayList>)users.get("kookboek")).get("gerechten");
                System.out.println(gerechten);

                HashMap x = new HashMap<>();

                for (HashMap gerecht : gerechten){

                    if ((int) gerecht.get("id") == dishId){
                        Integer receptId = ((HashMap<String, Integer>)gerecht.get("recept")).get("id");
                        if (receptId == recipeId){
                            //Can't remove an object from a list that is getting iterated over.
                            //So x stores the value of the dish object for the time being.
                            x = gerecht;
                            break;
                        }else {
                            System.out.println("No recipe");
                        }
                    } else {
                        System.out.println("No dish");
                    }
                }
                if (x != null){
                    gerechten.remove(x);
                    System.out.println("dit is de nieuwe lijst");
                    System.out.println(gerechten);
                }

            }else {
                System.out.println("No user");
            }
        }
        try{
            //Saves the new json data to the file, which updates it
            mapper.writeValue(new File("src/main/java/nl/hu/ipass/databaseLaag/test.json"), data);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
