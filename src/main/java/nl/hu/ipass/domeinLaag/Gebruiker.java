package nl.hu.ipass.domeinLaag;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;

public class Gebruiker implements Principal {
    private static ArrayList<Gebruiker> alleGebruikers;

    private int id;
    private String naam;
    private String wachtwoord;
    private String rol;

    static {
        alleGebruikers = new ArrayList<>();
        alleGebruikers.add(new Gebruiker("Hans", "root", "admin"));
    }

    public Gebruiker(String naam, String wachtwoord, String rol){
        this.id = Instant.now().getNano();
        this.naam = naam;
        this.wachtwoord = wachtwoord;
        this.rol = rol;
    }

    public static String verifieerLogin(String naam, String wachtwoord){
        for (Gebruiker gebruiker : alleGebruikers){
            if (gebruiker.getNaam().equals(naam) && gebruiker.getWachtwoord().equals(wachtwoord)){
                return gebruiker.getRol();
            }
        }
        return null;
    }

    public static Gebruiker getGebruikerBijNaam(String naam){
        for (Gebruiker gebruiker : alleGebruikers){
            if (gebruiker.getNaam().equals(naam)){
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

    public int getId(){
        return this.id;
    }

    @Override
    public String getName() {
        return null;
    }
}
