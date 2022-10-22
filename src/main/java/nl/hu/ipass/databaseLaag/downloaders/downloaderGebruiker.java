package nl.hu.ipass.databaseLaag.downloaders;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import nl.hu.ipass.databaseLaag.connectors.connectorGebruiker;
import nl.hu.ipass.domeinLaag.Gebruiker;
import nl.hu.ipass.domeinLaag.Gerecht;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;

public class downloaderGebruiker {
    public static void download(){
        try {
            BlobContainerClient blobContainer = connectorGebruiker.blobContainer;

            for (BlobItem blobItem : blobContainer.listBlobs()){
                BlobClient blob = blobContainer.getBlobClient(blobItem.getName());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                blob.downloadStream(baos);

                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

                ObjectInputStream ois = new ObjectInputStream(bais);

                Gebruiker gebruiker = (Gebruiker) ois.readObject();
                Gebruiker.updateAlleGebruikers(gebruiker);
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
