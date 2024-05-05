package project.printseven.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GooglePhotoService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACOUNT_KEY_PATH = "C:\\Users\\user\\IdeaProjects\\Print-Seven\\cred.json";


    public String uploadImageToDrive(File file){
        try {
            String folderId = "1kMH5JZubUEYwgyjGLswZb-TggaA4BH_y";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent).setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            file.delete();

            return imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NUll";

    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {
        System.out.println(SERVICE_ACOUNT_KEY_PATH);
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();

    }
}
