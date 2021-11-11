/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class HanghoaController implements Initializable {

    @FXML
    private AnchorPane paneHH;

    @FXML
    private Button btThongKe;

    @FXML
    private TextField soLuong;

    @FXML
    private CheckBox mhBanChay;

    @FXML
    private CheckBox lhBanChay;

    @FXML
    private CheckBox lhBanIt;

    @FXML
    private CheckBox mhBanIt;

    @FXML
    private Button btCapNhat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
//    public HashMap<String,Integer> loadDataMatHang(int top){
//        
//    }
    
    @FXML
    void checkLoaiHangBanChay(ActionEvent event) {
        lhBanIt.setSelected(false);
        mhBanChay.setSelected(false);
        mhBanIt.setSelected(false);
    }

    @FXML
    void checkLoaiHangBanIt(ActionEvent event) {
        lhBanChay.setSelected(false);
        mhBanChay.setSelected(false);
        mhBanIt.setSelected(false);
    }

    @FXML
    void checkMatHangBanChay(ActionEvent event) {
        lhBanChay.setSelected(false);
        lhBanIt.setSelected(false);
        mhBanIt.setSelected(false);
    }

    @FXML
    void checkMatHangBanIt(ActionEvent event) {
        lhBanChay.setSelected(false);
        mhBanChay.setSelected(false);
        lhBanIt.setSelected(false);
    }

    @FXML
    void loadThongKe(ActionEvent event) {
        if(!(this.soLuong.getText().matches("\\d+")) || 
        (this.lhBanChay.isSelected() == false && lhBanIt.isSelected() == false &&
        mhBanChay.isSelected() == false && mhBanIt.isSelected() == false) ){
            Utils.getBox("Vui lòng chỉ nhập số!", Alert.AlertType.WARNING).show();
            this.soLuong.setText("");
        }
    }

    
}
