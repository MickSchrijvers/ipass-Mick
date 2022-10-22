package nl.hu.ipass.databaseLaag.updaters;

import nl.hu.ipass.databaseLaag.deleters.deleterGebruiker;
import nl.hu.ipass.databaseLaag.uploaders.uploaderGebruiker;
import nl.hu.ipass.domeinLaag.Gebruiker;

public class updaterGebruiker {
    public static void update(Gebruiker gebruiker){
        try {
            //Er is geen blob.update dus ik doe eerst een delete en vervolgens upload ik de nieuwe versie gewoon.

            deleterGebruiker.delete(gebruiker);

            uploaderGebruiker.upload(gebruiker);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
