package nl.hu.ipass.databaseLaag.deleters;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import nl.hu.ipass.databaseLaag.connectors.connectorGebruiker;
import nl.hu.ipass.domeinLaag.Gebruiker;

public class deleterGebruiker {
    public static void delete(Gebruiker gebruiker){
        try {
            BlobContainerClient blobContainer = connectorGebruiker.blobContainer;

            BlobClient blob = blobContainer.getBlobClient(String.valueOf(gebruiker.getId()));

            blob.delete();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
