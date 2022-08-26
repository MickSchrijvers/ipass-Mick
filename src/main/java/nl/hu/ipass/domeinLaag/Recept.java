package nl.hu.ipass.domeinLaag;

import java.time.Instant;
import java.util.ArrayList;

public class Recept {
    private int id;
    private String titel;
    private ArrayList<String> stappen;
    private ArrayList<String> benodigdheden;

    public Recept (String titel) {
        this.id = Instant.now().getNano();
        this.titel = titel;
        this.stappen = new ArrayList<>();
        this.benodigdheden = new ArrayList<>();
    }

    public Recept (int id, String titel){
        this.id = id;
        this.titel = titel;
        this.stappen = new ArrayList<>();
        this.benodigdheden = new ArrayList<>();
    }

    public void setStappen(ArrayList<String> stappen){
        this.stappen = stappen;
    }

    public void setBenodigdheden(ArrayList<String> benodigdheden){
        this.benodigdheden = benodigdheden;
    }

    public int getId() {
        return this.id;
    }

    public String getTitel() {
        return this.titel;
    }

    public ArrayList<String> getStappen() {
        return this.stappen;
    }

    public ArrayList<String> getBenodigdheden() {
        return this.benodigdheden;
    }

    public void updateStappen(String stap){
        this.stappen.add(stap);
    }

    public void updateBenodigdheden(String benodigdheid){
        this.benodigdheden.add(benodigdheid);
    }
}
