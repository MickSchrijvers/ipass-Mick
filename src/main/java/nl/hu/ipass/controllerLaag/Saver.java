package nl.hu.ipass.controllerLaag;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.ipass.domeinLaag.Gebruiker;
import nl.hu.ipass.domeinLaag.Gerecht;
import nl.hu.ipass.domeinLaag.Recept;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Saver {
    public static void saveDish(int id){
        ObjectMapper mapper = new ObjectMapper();

        List<HashMap> data = Initializer.getData();
        System.out.println(data);

        for (HashMap element : data){
            if ((int) element.get("id") == id){
                //Adds a new recipe/dish to the gerechten arraylist
                ArrayList<HashMap> gerechten = ((HashMap<String, ArrayList>)element.get("kookboek")).get("gerechten");
                HashMap nieuwGerecht = new HashMap();
                HashMap nieuwRecept = new HashMap<>();

                ArrayList<String> nieuwStappen = new ArrayList<>();
                ArrayList<String> nieuwBenodigdheden = new ArrayList<>();

                nieuwStappen.add("Test Mayo");
                nieuwBenodigdheden.add("Test Mayo");

                nieuwRecept.put("id", 3333);
                nieuwRecept.put("titel", "Mayo");
                nieuwRecept.put("stappen", nieuwStappen);
                nieuwRecept.put("benodigdheden", nieuwStappen);

                nieuwGerecht.put("id", 4444);
                nieuwGerecht.put("titel", "Test Mayo");
                nieuwGerecht.put("recept", nieuwRecept);

                gerechten.add(nieuwGerecht);

                //Causes the current loaded object to also have the recipe/dish in their cookbook
                Recept recept = new Recept((Integer) nieuwRecept.get("id"), (String) nieuwRecept.get("titel"));
                recept.setStappen(nieuwStappen);
                recept.setBenodigdheden(nieuwBenodigdheden);

                Gerecht gerecht = new Gerecht((Integer) nieuwGerecht.get("id"), (String) nieuwGerecht.get("titel"));
                gerecht.setRecept(recept);

                Gebruiker gebruiker = Gebruiker.getGebruikerBijId(id);
                gebruiker.getKookboek().updateGerechten(gerecht);
            }
        }
        System.out.println(data);

        try{
//            File file = new File(System.getProperty("user.dir") + "/database.json");

//            InputStream iS = Saver.class.getResourceAsStream("/database.json");
//            System.out.println(iS);

//            System.out.println(System.getProperty("user.dir") + "/database.json");
//            System.out.println(file.getAbsolutePath());
//
//            System.out.println(file.exists());

            File file = new File("C:\\Users\\Mick\\Desktop\\School\\ipass-Mick\\src\\main\\resources\\database.json");

            //Saves the new json data to the file, which updates it
            mapper.writeValue(file, data);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
