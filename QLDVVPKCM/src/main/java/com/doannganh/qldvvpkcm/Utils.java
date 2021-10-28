/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class Utils {
    public static Alert getBox(String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(content);

        return alert;
    }
    
    public static ImageView setImageView(String path) {
        InputStream is = Utils.class.getResourceAsStream(path);
        ImageView iv = new ImageView(new Image(is));
        iv.setFitWidth(100);
        iv.setFitHeight(100);
        return iv;
    }
}
