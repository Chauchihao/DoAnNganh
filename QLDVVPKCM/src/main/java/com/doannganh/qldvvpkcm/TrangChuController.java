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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TrangChuController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    User nd;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setTTUser(User u){
        nd = u;
    }
    
    public void traCuuHangHoaThuKhoHandler(MouseEvent evt) throws IOException{
        try {
            Parent tchh;
            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("tracuuhanghoathukho.fxml"));
            tchh = loader.load();
            Scene scene = new Scene(tchh);
            TraCuuHangHoaThuKhoController controller = loader.getController();
            controller.setTTUser(nd);
            //stage.setResizable(false);
            //stage.initStyle(StageStyle.UTILITY);
            //stage.setFullScreen(true);
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TrangChuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void traCuuHangHoaQuanLyTruongHandler(MouseEvent evt) throws IOException{
        try {
            Parent tchh;
            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("tracuuchanghoaquanlytruong.fxml"));
            tchh = loader.load();
            Scene scene = new Scene(tchh);
            TraCuuHangHoaQuanLyTruongController controller = loader.getController();
            controller.setTTUser(nd);
            //stage.setResizable(false);
            //stage.initStyle(StageStyle.UTILITY);
            //stage.setFullScreen(true);
            //stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TrangChuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logoutHandler(ActionEvent evt) throws IOException{
        try {
            Parent dx;
            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("dangnhap.fxml"));
            dx = loader.load();
            Scene scene = new Scene(dx);
            stage.setScene(scene);
            //stage.setMaximized(true);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TrangChuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
