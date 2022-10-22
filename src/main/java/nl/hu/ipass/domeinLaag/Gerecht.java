package nl.hu.ipass.domeinLaag;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;

public class Gerecht implements Serializable {
    static final long serialVersionUID = -7928374205226774232L;

    private int id;
    private String titel;
    private Recept recept;


    public Gerecht(String titel) {
        this.id = Instant.now().getNano();
        this.titel = titel;
    }

    public Gerecht(int id, String titel){
        this.id = id;
        this.titel = titel;
    }

    public Gerecht(int id, String titel, Recept recept){
        this.id = id;
        this.titel = titel;
        this.recept = recept;
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
