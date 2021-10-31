
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.ChiTietDonHang;
import com.doannganh.pojo.User;
import com.doannganh.service.ChiTietDonHangService;
import com.doannganh.service.DonHangService;
import com.doannganh.service.JdbcUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


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
    @FXML 
    private AnchorPane acpLoad;
    
    @FXML 
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis ngay;

    @FXML
    private NumberAxis doanhThu;
    
    @FXML
    public void loadTraCuuHHQLT() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("tracuuhanghoaquanlytruong.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    
    public void loadBarchart(){
        try {
            XYChart.Series setData = new XYChart.Series<>();
            Connection conn = JdbcUtils.getConn();
            ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
            DonHangService dhs = new DonHangService(conn);
            ArrayList<String> ngay = bayNgayGanNhat(); 
            for(int i = 6; i >= 0; i--){
                int tong = 0;
                List<Integer> idDH = dhs.getDHIDByDate(ngay.get(i));
                for(int j = 0; j < idDH.size(); j++){
                    tong += ctdhs.tongDHByID(idDH.get(j));
                }
                setData.getData().add(new XYChart.Data(ngay.get(i),tong));
            }
            this.barChart.getData().addAll(setData);
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadBarchart();
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
    
    public ArrayList<String> bayNgayGanNhat(){
        ArrayList<String> ngay = new ArrayList<>();
        LocalDate today = LocalDate.now();
        ngay.add(today.toString());
        ngay.add(today.minusDays(1).toString());
        ngay.add(today.minusDays(2).toString());
        ngay.add(today.minusDays(3).toString());
        ngay.add(today.minusDays(4).toString());
        ngay.add(today.minusDays(5).toString());
        ngay.add(today.minusDays(6).toString());
        return ngay;
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
