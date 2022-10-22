package nl.hu.ipass.databaseLaag.connectors;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

public class connectorGebruiker {
        private final static String ENDPOINT = "https://ipassmick.blob.core.windows.net/";
        private final static String SASTOKEN = "?sv=2021-06-08&ss=bfqt&srt=sco&sp=rwdlacupiytfx&se=2023-10-17T20:08:20Z&st=2022-10-17T12:08:20Z&spr=https&sig=9vTBaDz%2FDuFXXjNFUI27dtgFDzdw6apR%2BkAUggqZKo0%3D";
        private final static String CONTAINER = "gebruiker";

        public static BlobContainerClient blobContainer = new BlobContainerClientBuilder()
                                .endpoint(ENDPOINT)
                                .sasToken(SASTOKEN)
                                .containerName(CONTAINER)
                                .buildClient();

}
