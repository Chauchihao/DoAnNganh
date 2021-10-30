
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TrangChuQuanLyTruongController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    static User nd;
    @FXML private AnchorPane acpLoad;
    @FXML private ScrollPane spLoad;
    @FXML private HBox hbLoad;
    @FXML
    public void loadTraCuuHHQLT() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("tracuuhanghoaquanlytruong.fxml"));
        acpLoad.getChildren().add(loader.load());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public static void setTTUser(User u){
        nd = u;
    }
    
    public void traCuuHangHoaQuanLyTruongHandler(ActionEvent evt) throws IOException{
        try {
            Parent tchh;
            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("tracuuhanghoaquanlytruong.fxml"));
            tchh = loader.load();
            Scene scene = new Scene(tchh);
            TraCuuHangHoaQuanLyTruongController controller = loader.getController();
            controller.setTTUser(nd);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void logoutHandler(ActionEvent evt) throws IOException{
        try {
            App.setRoot("dangnhap");
//            Parent dx;
//            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("dangnhap.fxml"));
//            dx = loader.load();
//            Scene scene = new Scene(dx);
//            stage.setScene(scene);
//            //stage.setMaximized(true);
//            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
