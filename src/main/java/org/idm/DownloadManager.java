package org.idm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.idm.config.AppConfig;
import org.idm.models.FileInfo;

import java.io.File;

public class DownloadManager {

    @FXML
    private TextField downloadURL;

    public int index = 0;

    @FXML
    private TableView<FileInfo> tableView;

    @FXML
    void downloadButtonClicked(ActionEvent event) {
        System.out.println("Button Clicked "+downloadURL.getText());
        String url = downloadURL.getText().trim();
        String filename = url.substring(url.lastIndexOf("/")+1);
        String status ="Starting";
        String action="OPEN";
        String path = AppConfig.DOWNLOAD_PATH+ File.separator+filename;
        FileInfo file = new FileInfo((index+1)+"",filename,url,status,action,path);
        this.index = this.index+1;
        DownloadThread thread = new DownloadThread(file,this);
        this.tableView.getItems().add(Integer.parseInt(file.getIndex())-1,file);
        thread.start();
    }

    public void updateUI(FileInfo file) {
        System.out.println(file);
        FileInfo fileInfo = this.tableView.getItems().get(Integer.parseInt(file.getIndex())-1);
        fileInfo.setStatus(file.getStatus());
        this.tableView.refresh();
    }

    @FXML
    public void initialize(){
        TableColumn<FileInfo, String> sn = (TableColumn<FileInfo, String>)this.tableView.getColumns().get(0);
        sn.setCellValueFactory(p->{
            return p.getValue().indexProperty();
        });
        TableColumn<FileInfo, String> filename = (TableColumn<FileInfo, String>)this.tableView.getColumns().get(1);
        filename.setCellValueFactory(p->{
            return p.getValue().nameProperty();
        });
        TableColumn<FileInfo, String> url = (TableColumn<FileInfo, String>)this.tableView.getColumns().get(2);
        url.setCellValueFactory(p->{
            return p.getValue().urlProperty();
        });
        TableColumn<FileInfo, String> status = (TableColumn<FileInfo, String>)this.tableView.getColumns().get(3);
        status.setCellValueFactory(p->{
            return p.getValue().statusProperty();
        });
        TableColumn<FileInfo, String> action = (TableColumn<FileInfo, String>)this.tableView.getColumns().get(4);
        action.setCellValueFactory(p->{
            return p.getValue().actionProperty();
        });
    }
}
