package org.idm;

import org.idm.models.FileInfo;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadThread extends Thread {
    private FileInfo file;
    private DownloadManager manager;

    public DownloadThread(FileInfo file,DownloadManager manager){
        this.file=file;
        this.manager=manager;
    }

    @Override
    public void run() {

        System.out.println("Downloading");
        this.file.setStatus("Downloading");
        this.manager.updateUI(this.file);
        try {
            Files.copy(new URL(this.file.getUrl()).openStream(), Paths.get(this.file.getPath()));
            this.file.setStatus("Done");
        }catch (Exception e){
            this.file.setStatus("Error");
            System.out.println("Downloading Error");
            e.printStackTrace();
        }
        this.manager.updateUI(this.file);
    }
}
