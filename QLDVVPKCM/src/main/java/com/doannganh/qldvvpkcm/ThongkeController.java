/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.service.ChiTietDonHangService;
import com.doannganh.service.DonHangService;
import com.doannganh.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;


public class ThongkeController implements Initializable {

    @FXML
    private DatePicker ngayBD;

    @FXML
    private DatePicker ngayKT;

    @FXML
    private ComboBox<String> loaiTK;

    @FXML
    private Button btPieChart;

    @FXML
    private Button btLineChart;

    @FXML
    private Button btBarChart;

    @FXML
    private Button btAreaChart;
    
    @FXML
    private CheckBox cbDoanhThu;

    @FXML
    private CheckBox cbLoiNhuan;

    @FXML
    private CheckBox cbBoth;
    
    @FXML
    private AnchorPane chartPane;
    
    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<?, ?> lineChart;
    
    @FXML
    private AreaChart<?, ?> areaChart;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCBData();
        this.ngayKT.setValue(LocalDate.now());
        this.ngayBD.setValue(LocalDate.of(2021, 01, 11));
        this.cbDoanhThu.setSelected(true);
        String ngayBD = this.ngayBD.getValue().format(dateFormatter);
        String ngayKT = this.ngayKT.getValue().format(dateFormatter);;
        String loaiTK = this.loaiTK.getValue().toString();
        
        if(loaiTK.equals("Ngày")){
            if(this.cbDoanhThu.isSelected()){
                
            }
        }
        else if(loaiTK.equals("Tháng")){
            
        }
        else{
            
        }
        
    }    
    
    public void loadCBData(){
        ObservableList<String> list = FXCollections.observableArrayList("Ngày", "Tháng", "Năm");
        this.loaiTK.setItems(list);
        this.loaiTK.getSelectionModel().selectFirst();
    }

     @FXML
    void selectedNgayBD(ActionEvent event) {
        if((this.ngayBD.getValue()).isAfter(LocalDate.now())){
            Utils.getBox("Vui lòng chọn ngày trong quá khứ!!!", Alert.AlertType.INFORMATION).show();
            this.ngayBD.setValue(LocalDate.of(2021, 01, 11));
        }
    }

    @FXML
    void selectedNgayKT(ActionEvent event) {
        if((this.ngayKT.getValue()).isBefore(this.ngayBD.getValue())){
            Utils.getBox("Vui lòng chọn ngày sau ngày bắt đầu!!!", Alert.AlertType.INFORMATION).show();
            this.ngayKT.setValue(LocalDate.now());
        }
    }
    
    public ObservableList<PieChart.Data> loadPieChartData() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        DonHangService dhs = new DonHangService(conn);
        ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);

        LocalDate ngayBD = this.ngayBD.getValue();
        LocalDate ngayKT = this.ngayKT.getValue();
        String loaiTK = this.loaiTK.getValue().toString();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if(loaiTK.equals("Ngày")){
            if(this.cbDoanhThu.isSelected()){
                int i = 0;
                while((ngayBD.plusDays(i)).isAfter(ngayKT)== false){
                    List<Integer> idDH = dhs.getDHIDByDate((ngayBD.plusDays(i)).format(dateFormatter));
                    int tong = 0;
                    for(int j = 0; j < idDH.size(); j++){
                        tong += ctdhs.tongDHByID(idDH.get(j));
                    }
                    if(tong != 0){
                        pieChartData.add(new PieChart.Data(ngayBD.plusDays(i).format(dateFormatter), tong));
                    }
                    i++;
                }
            }   
        }  
            else if(loaiTK.equals("Tháng")){

                    }
                    else{

                    }
        return  pieChartData;
    }
    
    @FXML
    void loadAreaChart(ActionEvent event) {
        
    }

    @FXML
    void loadBarChart(ActionEvent event) {

    }

    @FXML
    void loadLineChart(ActionEvent event) {

    }

    @FXML
    void loadPieChart(ActionEvent event) throws SQLException{
        ObservableList<PieChart.Data> data = loadPieChartData();
        pieChart.getData().clear();
        for(int i = 0; i < data.size(); i++){
            pieChart.getData().add(data.get(i));
        } 
        setVisible(1);
    }
    
    public void setVisible(int i){
        switch (i) {
            case 1:
                this.pieChart.setVisible(true);
                this.lineChart.setVisible(false);
                this.barChart.setVisible(false);
                this.areaChart.setVisible(false);
                break;
            case 2:
                this.pieChart.setVisible(false);
                this.barChart.setVisible(false);
                this.areaChart.setVisible(false);
                break;
            case 3:
                this.pieChart.setVisible(false);
                this.lineChart.setVisible(false);
                this.areaChart.setVisible(false);
                break;
            case 4:
                this.pieChart.setVisible(false);
                this.lineChart.setVisible(false);
                this.barChart.setVisible(false);
                break;
        }
    }
    
}
