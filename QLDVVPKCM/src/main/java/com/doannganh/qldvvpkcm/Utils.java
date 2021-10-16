/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
}
