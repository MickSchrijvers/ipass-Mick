package nl.hu.ipass.domeinLaag;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;

public class Kookboek implements Serializable {
    static final long serialVersionUID = -4112007155508867319L;

    private int id;
    private String titel;
    private ArrayList<Gerecht> gerechten;

    public Kookboek (String titel) {
        this.id = Instant.now().getNano();
        this.titel = titel;
        this.gerechten = new ArrayList<>();
    }

    public Kookboek (int id, String titel) {
        this.id = id;
        this.titel = titel;
        this.gerechten = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getTitel() {
        return this.titel;
    }

    public ArrayList<Gerecht> getGerechten() {
        return this.gerechten;
    }

    public void updateGerechten(Gerecht gerecht) {
        this.gerechten.add(gerecht);
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}
