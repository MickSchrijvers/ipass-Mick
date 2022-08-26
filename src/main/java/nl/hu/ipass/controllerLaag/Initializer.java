package nl.hu.ipass.controllerLaag;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.ipass.domeinLaag.Gebruiker;
import nl.hu.ipass.domeinLaag.Gerecht;
import nl.hu.ipass.domeinLaag.Kookboek;
import nl.hu.ipass.domeinLaag.Recept;

import java.io.*;
import java.util.*;


public class Initializer {
    public static ArrayList<Gebruiker> alleGebruikers = new ArrayList<>();
    public static List<HashMap> data = new ArrayList<>();

    public static void main(String[] args) {
//        Saver.saveDish(11);
//        loadData();
    }

    public static List<HashMap> getData(){
        Initializer.loadData();
        return data;
    }


    public static void loadData(){
        try{


            ObjectMapper mapper = new ObjectMapper();
//            InputStream file = Initializer.class.getResourceAsStream("/nl/hu/ipass/controllerLaag/database.json");
//            File file = new File(System.getProperty("user.dir") + "/database.json");

            File file = new File("target/ipass-Mick-1.0/x.json");

//            File file = new File(System.getProperty("user.home") + "database.json");

            data = mapper.readValue(file, List.class);
        }
        catch (javax.xml.ws.WebServiceException exception){
            System.out.println(System.getProperty("user.dir"));
            System.out.println(exception);
        }
        catch (Exception e){
            System.out.println(System.getProperty("user.dir"));
            System.out.println(e);
        }
    }

    public static void loadUsers(){
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap> gebruikers = Initializer.getData();

        try {

            for (HashMap gebruiker : gebruikers){
                Object gebruikerId = gebruiker.get("id");
                Object gebruikerNaam = gebruiker.get("naam");
                Object gebruikerWachtwoord = gebruiker.get("wachtwoord");
                Object gebruikerRol = gebruiker.get("rol");

                Integer kookboekId = ((HashMap<String, Integer>)gebruiker.get("kookboek")).get("id");
                String kookboekTitel = ((HashMap<String, String>)gebruiker.get("kookboek")).get("titel");
                ArrayList<HashMap> gerechten = ((HashMap<String, ArrayList>)gebruiker.get("kookboek")).get("gerechten");

                Kookboek kookboek = new Kookboek(kookboekId, kookboekTitel);

                for (HashMap gerecht : gerechten){
                    Object gerechtId = gerecht.get("id");
                    Object gerechtTitel = gerecht.get("titel");
                    Object gerechtRecept = gerecht.get("recept");

                    Integer receptId = ((HashMap<String, Integer>)gerecht.get("recept")).get("id");
                    String receptTitel = ((HashMap<String, String>)gerecht.get("recept")).get("titel");

                    Recept recept = new Recept(receptId, receptTitel);

                    ArrayList<String> receptStappen = ((HashMap<String, ArrayList<String>>)gerecht.get("recept")).get("stappen");
                    for (String stap : receptStappen){
                        recept.updateStappen(stap);
                    }
                    ArrayList<String> receptBenodigdheden = ((HashMap<String, ArrayList<String>>)gerecht.get("recept")).get("benodigdheden");
                    for (String benodigdheid : receptBenodigdheden){
                        recept.updateBenodigdheden(benodigdheid);
                    }

                    Gerecht g1 = new Gerecht((Integer) gerechtId, (String) gerechtTitel);
                    g1.setRecept(recept);

                    kookboek.updateGerechten(g1);
                }

                Gebruiker.updateAlleGebruikers(new Gebruiker((int) gebruikerId, (String) gebruikerNaam, (String) gebruikerWachtwoord, (String) gebruikerRol, kookboek));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
