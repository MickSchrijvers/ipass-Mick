package nl.hu.ipass.domeinLaag;

import java.time.Instant;
import java.util.ArrayList;

public class Gerecht {
    private static ArrayList<Gerecht> alleGerechten = new ArrayList<>();

    private int id;
    private String titel;
    private Recept recept;

    public static ArrayList<Gerecht> getAlleGerechten() {
        return alleGerechten;
    }

    public Gerecht(String titel) {
        this.id = Instant.now().getNano();
        this.titel = titel;
    }

    public Gerecht(int id, String titel){
        this.id = id;
        this.titel = titel;
    }

    public int getId() {
        return this.id;
    }

    public String getTitel() {
        return this.titel;
    }

    public Recept getRecept() {
        return this.recept;
    }

    public void setRecept(Recept recept) {
        this.recept = recept;
    }
}
