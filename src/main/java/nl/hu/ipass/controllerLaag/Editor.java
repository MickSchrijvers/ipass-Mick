package nl.hu.ipass.controllerLaag;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Editor {

    public static void editDish(int userId, int dishId, int recipeId, ArrayList<String> nieuweStappen, ArrayList<String> nieuweBenodigdheden, String nieuweTitel){
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap> data = Initializer.getData();

        Boolean titelVervangen = false;
        Boolean stappenVervangen = false;
        Boolean benodigdhedenVervangen = false;

        for (HashMap users : data){

            if ((int) users.get("id") == userId){
                ArrayList<HashMap> gerechten = ((HashMap<String, ArrayList>)users.get("kookboek")).get("gerechten");
                System.out.println(gerechten);

                for (HashMap gerecht : gerechten){

                    if ((int) gerecht.get("id") == dishId){
                        Integer receptId = ((HashMap<String, Integer>)gerecht.get("recept")).get("id");
                        if (receptId == recipeId){

                           //hier



                            if (!nieuweTitel.isEmpty()){
                                String receptTitel = ((HashMap<String, String>)gerecht.get("recept")).get("titel");
                                receptTitel = nieuweTitel;
                                titelVervangen = true;
                            }
                            if (!nieuweStappen.isEmpty()){
                                ArrayList<String> receptStappen = ((HashMap<String, ArrayList<String>>)gerecht.get("recept")).get("stappen");
                                receptStappen.clear();
                                receptStappen = nieuweStappen;
                                stappenVervangen = true;
                            }
                            if (!nieuweBenodigdheden.isEmpty()){
                                ArrayList<String> receptBenodigdheden = ((HashMap<String, ArrayList<String>>)gerecht.get("recept")).get("benodigdheden");
                                receptBenodigdheden.clear();
                                receptBenodigdheden = nieuweBenodigdheden;
                                benodigdhedenVervangen = true;
                            }
                        }else {
                            System.out.println("No recipe");
                        }
                    } else {
                        System.out.println("No dish");
                    }
                }
            }else {
                System.out.println("No user");
            }
        }
        try{
            //Saves the new json data to the file, which updates it
            if (titelVervangen || stappenVervangen || benodigdhedenVervangen){
                mapper.writeValue(new File("src/main/java/nl/hu/ipass/databaseLaag/test.json"), data);
            } else {
                System.out.println("Nothing changed");
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
