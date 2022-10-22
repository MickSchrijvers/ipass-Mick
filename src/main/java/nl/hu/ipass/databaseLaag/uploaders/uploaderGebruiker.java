package nl.hu.ipass.databaseLaag.uploaders;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import nl.hu.ipass.databaseLaag.connectors.connectorGebruiker;
import nl.hu.ipass.domeinLaag.Gebruiker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class uploaderGebruiker {
    public static void upload(Gebruiker gebruiker){
        try{
            BlobContainerClient blobContainer = connectorGebruiker.blobContainer;

            BlobClient blob = blobContainer.getBlobClient(String.valueOf(gebruiker.getId()));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(gebruiker);

            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            blob.upload(bais, bytes.length, true);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
