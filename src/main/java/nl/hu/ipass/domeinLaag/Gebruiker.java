package nl.hu.ipass.domeinLaag;

import nl.hu.ipass.controllerLaag.Initializer;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Gebruiker implements Principal {
    private static ArrayList<Gebruiker> alleGebruikers = new ArrayList<>();

    private int id;
    private String naam;
    private String wachtwoord;
    private String rol;
    private Kookboek kookboek;

    static {

        Initializer.loadUsers();


//        alleGebruikers.add(new Gebruiker("Hans", "root", "admin"));
//
//        Recept testRecept = new Recept("Nieuw recept");
//        testRecept.updateBenodigdheden("Ijsje");
//        testRecept.updateStappen("Likken");
//
//        Gerecht testGerecht = new Gerecht("Nieuw gerecht");
//        testGerecht.setRecept(testRecept);
//
//        Kookboek testKookboek = new Kookboek("Nieuw kookboek");
//        testKookboek.updateGerechten(testGerecht);
//
//        Gebruiker testGebruiker = Gebruiker.getGebruikerBijNaam("Hans");
//        testGebruiker.setKookboek(testKookboek);
    }

    public Gebruiker(String naam, String wachtwoord, String rol){
        this.id = Instant.now().getNano();
        this.naam = naam;
        this.wachtwoord = wachtwoord;
        this.rol = rol;
    }

    public Gebruiker(int id, String naam, String wachtwoord, String rol, Kookboek kookboek){
        this.id = id;
        this.naam = naam;
        this.wachtwoord = wachtwoord;
        this.rol = rol;
        this.kookboek = kookboek;
    }

    public static int verifieerLogin(String naam, String wachtwoord){
        for (Gebruiker gebruiker : alleGebruikers){
            if (gebruiker.getNaam().equals(naam) && gebruiker.getWachtwoord().equals(wachtwoord)){
                return gebruiker.getId();
            }
        }
        return 0;
    }

    public static Gebruiker getGebruikerBijNaam(String naam){
        for (Gebruiker gebruiker : alleGebruikers){
            if (gebruiker.getNaam().equals(naam)){
                return gebruiker;
            }
        }
        return null;
    }

    public static ArrayList<Gebruiker> getAlleGebruikers(){
        return alleGebruikers;
    }

    public static void setAlleGebruikers(ArrayList<Gebruiker> gebruikers){
        alleGebruikers = gebruikers;
    }

    public static void updateAlleGebruikers(Gebruiker gebruiker){
        alleGebruikers.add(gebruiker);
    }

    public static Gebruiker getGebruikerBijId(int id){
        for (Gebruiker gebruiker : alleGebruikers){
            if (gebruiker.getId() == id) {
                return gebruiker;
            }
        }
        return null;
    }

    public String getNaam(){
        return this.naam;
    }

    public String getRol(){
        return this.rol;
    }

    public String getWachtwoord(){
        return this.wachtwoord;
    }

    public Kookboek getKookboek(){
        return this.kookboek;
    }

    public void setKookboek(Kookboek kookboek) {
        this.kookboek = kookboek;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String getName() {
        return null;
    }
}
