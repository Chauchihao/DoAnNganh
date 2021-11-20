/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.ChiTietDonHang;
import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.NhaCungCap_HangHoa;
import com.doannganh.service.ChiTietDonHangService;
import com.doannganh.service.DonHangService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.NhaCungCapService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
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
    private CheckBox cbChiPhi;
    
    @FXML
    private AnchorPane chartPane;
    
    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xBar;

    @FXML
    private NumberAxis yBar;

    @FXML
    private LineChart<String, Number> lineChart;
    
    @FXML
    private NumberAxis yLine;
    
    @FXML
    private CategoryAxis xLine;
    
    @FXML
    private AreaChart<String, Number> areaChart;
    
    @FXML
    private CategoryAxis xArea;

    @FXML
    private NumberAxis yArea;


    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCBData();
        this.ngayKT.setValue(LocalDate.now());
        this.ngayKT.setEditable(false);
        this.ngayBD.setEditable(false);
        this.ngayBD.setValue(LocalDate.now().minusDays(15));
        this.cbDoanhThu.setSelected(true);    
    }    
     
    @FXML
    void loadPieChart(ActionEvent event) throws SQLException{
//        pieChart.setData(loadPieChartData());
        pieChart.getData().clear();
        LocalDate ngayBD = this.ngayBD.getValue();
        LocalDate ngayKT = this.ngayKT.getValue();
        String loaiTK = this.loaiTK.getValue().toString();
        
        HashMap<String,Integer> data = new HashMap<>();
        String title = "Biểu đồ";

        if(this.cbLoiNhuan.isSelected()){
            data = tinhLoiNhuan(ngayBD, ngayKT, loaiTK);
            title += " lợi nhuận";
        } 
        if(this.cbDoanhThu.isSelected()){
            data = tinhDoanhThu(ngayBD, ngayKT, loaiTK);
            title += " doanh thu";
        }
        if(this.cbChiPhi.isSelected()){
            data = tinhChiPhi(ngayBD, ngayKT, loaiTK);
            title += " chi phí";
        }
        
        if(this.loaiTK.getValue().equals("Ngày")){
            title += " theo ngày";
        }
        if(this.loaiTK.getValue().equals("Tháng")){
            title += " theo tháng";
        }
        if(this.loaiTK.getValue().equals("Năm")){
            title += " theo năm";
        }
        
        pieChart.setTitle(title);
        
        for (Map.Entry<String,Integer> entry:data.entrySet()){
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChart.getData().forEach(dt-> {
            String percent = moneyFormat((int)dt.getPieValue());
            Tooltip tool = new Tooltip(percent);
            Tooltip.install(dt.getNode(), tool);
        });
        setVisible(1);
    }
    
    @FXML
    void loadLineChart(ActionEvent event) throws SQLException {
        lineChart.getData().clear();
        LocalDate ngayBD = this.ngayBD.getValue();
        LocalDate ngayKT = this.ngayKT.getValue();
        String loaiTK = this.loaiTK.getValue().toString();
        
        XYChart.Series<String, Number> dataDT =  new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> dataLN = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> dataCP = new XYChart.Series<String, Number>();
        
        if(this.cbDoanhThu.isSelected() ){
            dataDT = changeHashToSeries(tinhDoanhThu(ngayBD, ngayKT, loaiTK));
            dataDT.setName(" Doanh thu ");
            this.lineChart.getData().add(dataDT);
        }
        if(this.cbLoiNhuan.isSelected()){
            dataLN = changeHashToSeries(tinhLoiNhuan(ngayBD, ngayKT, loaiTK));
            dataLN.setName(" Lợi nhuận ");
            this.lineChart.getData().add(dataLN);
        }
        if(this.cbChiPhi.isSelected()){
            dataCP = changeHashToSeries(tinhChiPhi(ngayBD, ngayKT, loaiTK));
            dataCP.setName(" Chi phí ");
            this.lineChart.getData().add(dataCP);
        }
 
        yLine.setLabel("Tổng tiền(VNĐ)");
        String title = "Biểu đồ";
        if(this.loaiTK.getValue().equals("Ngày")){
            xLine.setLabel("Ngày");
            if(!(dataDT.getName() == null)){
                title += dataDT.getName();
            }
            if(!(dataLN.getName()== null)){
                title += dataLN.getName();
            }
            if(!(dataCP.getName()== null)){
                title += dataCP.getName();
            }
            lineChart.setTitle(title + "theo ngày");
        }
        if(this.loaiTK.getValue().equals("Tháng")){
            xLine.setLabel("Tháng");
            if(!(dataDT.getName()== null)){
                title += dataDT.getName();
            }
            if(!(dataLN.getName()== null)){
                title += dataLN.getName();
            }
            if(!(dataCP.getName()== null)){
                title += dataCP.getName();
            }
            lineChart.setTitle(title + "theo tháng");
        }
        if(this.loaiTK.getValue().equals("Năm")){
            xLine.setLabel("Năm");
            if(!(dataDT.getName() == null)){
                title += dataDT.getName();
            }
            if(!(dataLN.getName()== null)){
                title += dataLN.getName();
            }
            if(!(dataCP.getName()== null)){
                title += dataCP.getName();
            }
            lineChart.setTitle(title + "theo năm");
        }
        setVisible(2);
    }
    
    @FXML
    void loadBarChart(ActionEvent event) throws SQLException {
        barChart.getData().clear();
        LocalDate ngayBD = this.ngayBD.getValue();
        LocalDate ngayKT = this.ngayKT.getValue();
        String loaiTK = this.loaiTK.getValue().toString();
   
        XYChart.Series<String, Number> dataDT =  new XYChart.Series<String,Number>();
        XYChart.Series<String, Number> dataLN = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> dataCP = new XYChart.Series<String, Number>();
        
        if(this.cbDoanhThu.isSelected() ){
            dataDT = changeHashToSeries(tinhDoanhThu(ngayBD, ngayKT, loaiTK));
            dataDT.setName(" Doanh thu ");
            this.barChart.getData().add(dataDT);
        }
        if(this.cbLoiNhuan.isSelected()){
            dataLN = changeHashToSeries(tinhLoiNhuan(ngayBD, ngayKT, loaiTK));
            dataLN.setName(" Lợi nhuận ");
            this.barChart.getData().add(dataLN);
        }
        if(this.cbChiPhi.isSelected()){
            dataCP = changeHashToSeries(tinhChiPhi(ngayBD, ngayKT, loaiTK));
            dataCP.setName(" Chi phí ");
            this.barChart.getData().add(dataCP);
        }
 
        yBar.setLabel("Tổng tiền(VNĐ)");
        String title = "Biểu đồ";
        if(this.loaiTK.getValue().equals("Ngày")){
            xBar.setLabel("Ngày");

            if(!(dataDT.getName() == null)){
                title += dataDT.getName();
            }
            if(!(dataLN.getName()== null)){
                title += dataLN.getName();
            }
            if(!(dataCP.getName()== null)){
                title += dataCP.getName();
            }
            barChart.setTitle(title + "theo ngày");
        }
        if(this.loaiTK.getValue().equals("Tháng")){
            xBar.setLabel("Tháng");
            if(!(dataDT.getName()== null)){
                title += dataDT.getName();
            }
            if(!(dataLN.getName()== null)){
                title += dataLN.getName();
            }
            if(!(dataCP.getName()== null)){
                title += dataCP.getName();
            }
            barChart.setTitle(title + "theo tháng");
        }
        if(this.loaiTK.getValue().equals("Năm")){
            xBar.setLabel("Năm");
            if(!(dataDT.getName() == null)){
                title += dataDT.getName();
            }
            if(!(dataLN.getName()== null)){
                title += dataLN.getName();
            }
            if(!(dataCP.getName()== null)){
                title += dataCP.getName();
            }
            barChart.setTitle(title + "theo năm");
        }
        
        setVisible(3);
    }

    
    @FXML
    void loadAreaChart(ActionEvent event) throws SQLException {
        areaChart.getData().clear();
        LocalDate ngayBD = this.ngayBD.getValue();
        LocalDate ngayKT = this.ngayKT.getValue();
        String loaiTK = this.loaiTK.getValue().toString();
        
        XYChart.Series<String, Number> dataDT =  new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> dataLN = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> dataCP = new XYChart.Series<String, Number>();
        
        if(this.cbDoanhThu.isSelected() ){
            dataDT = changeHashToSeries(tinhDoanhThu(ngayBD, ngayKT, loaiTK));
            dataDT.setName(" Doanh thu ");
            this.areaChart.getData().add(dataDT);
        }
        if(this.cbLoiNhuan.isSelected()){
            dataLN = changeHashToSeries(tinhLoiNhuan(ngayBD, ngayKT, loaiTK));
            dataLN.setName(" Lợi nhuận ");
            this.areaChart.getData().add(dataLN);
        }
        if(this.cbChiPhi.isSelected()){
            dataCP = changeHashToSeries(tinhChiPhi(ngayBD, ngayKT, loaiTK));
            dataCP.setName(" Chi phí ");
            this.areaChart.getData().add(dataCP);
        }
 
        yArea.setLabel("Tổng tiền(VNĐ)");
        String title = "Biểu đồ";
        if(this.loaiTK.getValue().equals("Ngày")){
            xArea.setLabel("Ngày");
            
            if(!(dataDT.getName() == null)){
                title += dataDT.getName();
            }
            if(!(dataLN.getName()== null)){
                title += dataLN.getName();
            }
            if(!(dataCP.getName()== null)){
                title += dataCP.getName();
            }
            areaChart.setTitle(title + "theo ngày");
        }
        if(this.loaiTK.getValue().equals("Tháng")){
            xArea.setLabel("Tháng");
            if(!(dataDT.getName()== null)){
                title += dataDT.getName();
            }
            if(!(dataLN.getName()== null)){
                title += dataLN.getName();
            }
            if(!(dataCP.getName()== null)){
                title += dataCP.getName();
            }
            areaChart.setTitle(title + "theo tháng");
        }
        if(this.loaiTK.getValue().equals("Năm")){
            xArea.setLabel("Năm");
            if(!(dataDT.getName() == null)){
                title += dataDT.getName();
            }
            if(!(dataLN.getName()== null)){
                title += dataLN.getName();
            }
            if(!(dataCP.getName()== null)){
                title += dataCP.getName();
            }
            areaChart.setTitle(title + "theo năm");
        }
        
        setVisible(4);
    }
    
    public HashMap<String,Integer> tinhDoanhThu(LocalDate ngayBD, LocalDate ngayKT, String loaiTK) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        DonHangService dhs = new DonHangService(conn);
        ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
        
        HashMap<String,Integer> doanhThu = new HashMap<>();
        if(loaiTK.equals("Ngày")){
            int i = 0;
            while((ngayBD.plusDays(i)).isAfter(ngayKT)== false){
                int dt = ctdhs.getDoanhThuByDate((ngayBD.plusDays(i)).format(dateFormatter));

                if(dt != 0){
                    doanhThu.put((ngayBD.plusDays(i)).format(dateFormatter), dt);
                }
                i++;
            }
        }
        else if(loaiTK.equals("Tháng")){
            int i = 0;
            while(((ngayBD.plusMonths(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusMonths(i).getMonthValue() == ngayKT.getMonthValue()){
                
                int dt = ctdhs.getDoanhThuByMonth(ngayBD.plusMonths(i).format(dateFormatter));
                
                if(dt != 0){
                    doanhThu.put((ngayBD.plusMonths(i)).getMonth().toString()
                            + "-".concat(Integer.toString(ngayBD.plusMonths(i).getYear())), dt);
                }
                i++;
            }
        }
        else{
            int i = 0;
            while(((ngayBD.plusYears(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusYears(i).getYear() == ngayKT.getYear()){
                int dt = ctdhs.getDoanhThuByYear((ngayBD.plusYears(i)).format(dateFormatter));

                if(dt != 0){
                    doanhThu.put(Integer.toString(ngayBD.plusYears(i).getYear()), dt);
                }
                i++;
            }
        }
        conn.close();
        return doanhThu;
    }
    
    public HashMap<String,Integer> tinhChiPhi(LocalDate ngayBD, LocalDate ngayKT, String loaiTK) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        NhaCungCapService nccs = new NhaCungCapService(conn);
        
        HashMap<String,Integer> chiPhi = new HashMap<>();
        if(loaiTK.equals("Ngày")){
            int i = 0;
            while((ngayBD.plusDays(i)).isAfter(ngayKT)== false){
                int cp = nccs.tinhChiPhiByDate((ngayBD.plusDays(i)).format(dateFormatter));

                if(cp != 0){
                    chiPhi.put((ngayBD.plusDays(i)).format(dateFormatter), cp );
                }
                i++;
            }
        }
        else if(loaiTK.equals("Tháng")){
            int i = 0;
            while(((ngayBD.plusMonths(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusMonths(i).getMonthValue() == ngayKT.getMonthValue()){
                int cp = nccs.tinhChiPhiByMonth((ngayBD.plusMonths(i)).format(dateFormatter));
                
                if(cp != 0){
                    chiPhi.put((ngayBD.plusMonths(i)).getMonth().toString()
                            + "-".concat(Integer.toString(ngayBD.plusMonths(i).getYear())), cp);
                }
                i++;
            }
        }
        else{
            int i = 0;
            while(((ngayBD.plusYears(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusYears(i).getYear() == ngayKT.getYear()){
                int cpN = nccs.tinhChiPhiByYear((ngayBD.plusYears(i)).format(dateFormatter));

                if(cpN != 0){
                    chiPhi.put(Integer.toString(ngayBD.plusYears(i).getYear()), cpN);
                }
                i++;
            } 
        }
        conn.close();
        return chiPhi;
    }
    
    public HashMap<String,Integer> tinhLoiNhuan(LocalDate ngayBD, LocalDate ngayKT, String loaiTK) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        DonHangService dhs = new DonHangService(conn);
        ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
        NhaCungCapService nccs = new NhaCungCapService(conn);
        
        HashMap<String,Integer> loiNhuan = new HashMap<>();

        if(loaiTK.equals("Ngày")){
            int i = 0;
            while((ngayBD.plusDays(i)).isAfter(ngayKT)== false){
        
                int chiPhiHN = nccs.tinhChiPhiUntilDate((ngayBD.plusDays(i)).format(dateFormatter));
                int doanhThuHN = ctdhs.getDoanhThuUntilDate((ngayBD.plusDays(i)).format(dateFormatter));
                int lnHN = doanhThuHN - chiPhiHN;
                
                loiNhuan.put(ngayBD.plusDays(i).format(dateFormatter), lnHN);

                i++;
            }

        }
        else if(loaiTK.equals("Tháng")){
            int i = 0;
            while(((ngayBD.plusMonths(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusMonths(i).getMonthValue() == ngayKT.getMonthValue()){

                int chiPhiTN = nccs.tinhChiPhiUntilDate((ngayBD.plusMonths(i)).format(dateFormatter));
                int doanhThuTN = ctdhs.getDoanhThuUntilDate((ngayBD.plusMonths(i)).format(dateFormatter));
                int lnTN = doanhThuTN - chiPhiTN;
                
                loiNhuan.put((ngayBD.plusMonths(i)).getMonth().toString()
                        + "-".concat(Integer.toString(ngayBD.plusMonths(i).getYear())), lnTN );
                i++;
            }
            
        }
        else{
            int i = 0;
            while(((ngayBD.plusYears(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusYears(i).getYear() == ngayKT.getYear()){

                int chiPhiN = nccs.tinhChiPhiUntilYear((ngayBD.plusYears(i)).format(dateFormatter));
                int doanhThuN = ctdhs.getDoanhThuUntilYear((ngayBD.plusYears(i)).format(dateFormatter));
                int lnN = doanhThuN - chiPhiN;
                
                loiNhuan.put(Integer.toString(ngayBD.plusYears(i).getYear()), lnN );

                i++;
            }
        }
        
        conn.close();
        return loiNhuan;
    }
    
    
    public XYChart.Series changeHashToSeries(HashMap<String,Integer> data){
        
        TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(data);
        Set<Map.Entry<String, Integer>> sort = sorted.entrySet();
        XYChart.Series setData = new XYChart.Series<>();
        for (Map.Entry<String,Integer> dt:sort){
            setData.getData().add(new XYChart.Data(dt.getKey(), dt.getValue()));
        } 
        return setData;
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
            this.ngayBD.setValue(LocalDate.now().minusDays(15));
        }
//        if(loaiTK.getValue().equals("Ngày")){
//            final Callback<DatePicker, DateCell> dayCellFactory = 
//                new Callback<DatePicker, DateCell>() {
//                    @Override
//                    public DateCell call(final DatePicker datePicker) {
//                        return new DateCell() {
//                            @Override
//                            public void updateItem(LocalDate item, boolean empty) {
//                                super.updateItem(item, empty);
//
//                                if (item.isBefore(
//                                        ngayBD.getValue().plusDays(15))
//                                    ) {
//                                        setDisable(true);
//                                        setStyle("-fx-background-color: #ffc0cb;");
//                                        ngayKT.setValue(ngayBD.getValue().plusDays(15));
//                                }   
//                        }
//                    };
//                }
//            };
//            ngayKT.setDayCellFactory(dayCellFactory);
//        }
    }

    @FXML
    void selectedNgayKT(ActionEvent event) {
        if((this.ngayKT.getValue()).isBefore(this.ngayBD.getValue())){
            Utils.getBox("Vui lòng chọn ngày sau ngày bắt đầu!!!", Alert.AlertType.INFORMATION).show();
            this.ngayKT.setValue(LocalDate.now());
        }
//        if(loaiTK.getValue().equals("Ngày")){
//            final Callback<DatePicker, DateCell> dayCellFactory = 
//                new Callback<DatePicker, DateCell>() {
//                    @Override
//                    public DateCell call(final DatePicker datePicker) {
//                        return new DateCell() {
//                            @Override
//                            public void updateItem(LocalDate item, boolean empty) {
//                                super.updateItem(item, empty);
//
//                                if (item.isBefore(
//                                        ngayKT.getValue().minusDays(15))
//                                    ) {
//                                        setDisable(true);
//                                        setStyle("-fx-background-color: #ffc0cb;");
//                                        ngayBD.setValue(ngayKT.getValue().minusDays(15));
//                                }   
//                        }
//                    };
//                }
//            };
//            ngayBD.setDayCellFactory(dayCellFactory);
//        }
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
                this.lineChart.setVisible(true);
                this.barChart.setVisible(false);
                this.areaChart.setVisible(false);
                break;
            case 3:
                this.pieChart.setVisible(false);
                this.lineChart.setVisible(false);
                this.barChart.setVisible(true);
                this.areaChart.setVisible(false);
                break;
            case 4:
                this.pieChart.setVisible(false);
                this.lineChart.setVisible(false);
                this.barChart.setVisible(false);
                this.areaChart.setVisible(true);
                break;
        }
    }
    
    @FXML
    void checkChiPhi(ActionEvent event) {
        if(cbDoanhThu.isSelected() || cbLoiNhuan.isSelected()){
            btPieChart.setDisable(true);
        }
        else if(!(cbChiPhi.isSelected() || cbLoiNhuan.isSelected()))
            btPieChart.setDisable(false);
    }
    @FXML
    void checkDoanhThu(ActionEvent event) {
        if(cbChiPhi.isSelected() || cbLoiNhuan.isSelected()){
            btPieChart.setDisable(true);
        }
        else if(!(cbChiPhi.isSelected() || cbLoiNhuan.isSelected()))
            btPieChart.setDisable(false);
    }
    @FXML
    void checkLoiNhuan(ActionEvent event) {
        if(cbDoanhThu.isSelected() || cbChiPhi.isSelected()){
             btPieChart.setDisable(true);
        }
        else if(!(cbChiPhi.isSelected() || cbLoiNhuan.isSelected()))
            btPieChart.setDisable(false);
    }
    
    
    public String moneyFormat(int money){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return(formatter.format(money)+" VNĐ");
    }
    
}
