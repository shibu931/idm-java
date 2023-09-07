package org.idm;

import org.idm.models.FileInfo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
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

        this.file.setStatus("Downloading");
        this.manager.updateUI(this.file);
        try {
            URL url = new URL(this.file.getUrl());
            URLConnection openConnection = url.openConnection();
            int fileSize = openConnection.getContentLength();
            int countByte = 0;
            double percentage = 0.0;
            double byteSum =0.0;
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            FileOutputStream fos = new FileOutputStream(this.file.getPath());
            byte data[] = new byte[1024];
            while(true){
                countByte =bufferedInputStream.read(data,0,1024);
                if(countByte==-1){
                    break;
                }
                fos.write(data,0,countByte);
                byteSum= byteSum+countByte;
                if(fileSize>0){
                    percentage=(byteSum/fileSize*100);
                    System.out.println(percentage);
                    this.file.setPercentage(percentage+"");
                    this.manager.updateUI(file);
                }
            }
            fos.close();
            bufferedInputStream.close();
            this.file.setPercentage(100+"");
            this.file.setStatus("Done");
        }catch (Exception e){
            this.file.setStatus("Error");
            System.out.println("Downloading Error");
            e.printStackTrace();
        }
        this.manager.updateUI(this.file);
    }
}
