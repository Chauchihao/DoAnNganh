/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;


import com.doannganh.pojo.KhachHang;
import com.doannganh.pojo.ThuCung;
import com.doannganh.service.DonHangService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.KhachHangService;
import com.doannganh.service.ThuCungService;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class KhachhangController implements Initializable {

    @FXML
    private TableView<KhachHang> tableKH;

    @FXML
    private TableView<ThuCung> tbThuCung;

    @FXML
    private TextField txtMaKH;

    @FXML
    private TextField txtHoTen;

    @FXML
    private DatePicker dpKH;

    @FXML
    private ComboBox<String> cbGioiTinhKH;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtSDT;

    @FXML
    private TextField txtDiemTL;

    @FXML
    private TextField txtMaTC;

    @FXML
    private TextField txtTenTC;
    
    @FXML
    private TextField txtMauLong;

    @FXML
    private TextField txtSucKhoe;

    @FXML
    private ComboBox<String> cbGioiTinhTC;

    @FXML
    private ComboBox<Integer> cbMaKH;

    @FXML
    private DatePicker dpTC;
    
    @FXML
    private TextField fMaKH;
    
    @FXML
    private TextField fTenKH;

    @FXML
    private TextField fDiemTL;

    @FXML
    private TextField fDiaChi;

    @FXML
    private TextField fSDT;

    @FXML
    private TextField fMaTC;

    @FXML
    private TextField fTenTC;

    @FXML
    private ComboBox<String> fGTKH;

    @FXML
    private ComboBox<String> fGTTC;

    @FXML
    private DatePicker fNgaySinhTC;

    @FXML
    private ComboBox<Integer> fIDKH;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadKhachHang("", "");
            loadTableKH();
            loadThuCung("", "");
            loadTableTC();
            loadCBGTKH();
            loadCBGTTC();
            loadCBIDTC();
            this.fMaKH.textProperty().addListener(l ->{
                try {
                    loadKhachHang(fMaKH.getText(), "Mã khách hàng");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.fTenKH.textProperty().addListener(l ->{
                try {
                        loadKhachHang(fTenKH.getText(), "Họ tên");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.fSDT.textProperty().addListener(l ->{
                try {
                    loadKhachHang(fSDT.getText(), "Số điện thoại");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.fDiaChi.textProperty().addListener(l ->{
                try {
                    loadKhachHang(fDiaChi.getText(), "Địa chỉ");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            this.fDiemTL.textProperty().addListener(l ->{
                try {
                    loadKhachHang(fDiemTL.getText(), "Điểm");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            this.fGTKH.valueProperty().addListener(l->{
                try {
                    loadKhachHang(fGTKH.getValue(), "Giới tính");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            this.fMaTC.textProperty().addListener(l->{
                try {
                    loadThuCung(fMaTC.getText(), "Mã thú cưng");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            
            this.fTenTC.textProperty().addListener(l->{
                try {
                    loadThuCung(fTenTC.getText(), "Tên");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            
            this.fNgaySinhTC.valueProperty().addListener(l->{
                try {
                    loadThuCung(fNgaySinhTC.getValue().format(dateFormatter), "Ngày sinh");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch(NullPointerException n){
                    try {
                        loadThuCung("", "");
                    } catch (SQLException ex) {
                        Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });
            
            this.fGTTC.valueProperty().addListener(l->{
                try {
                    loadThuCung(fGTTC.getValue(), "Giới tính");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            this.fIDKH.valueProperty().addListener(l->{
                try {
                    loadThuCung(String.valueOf(fIDKH.getValue()), "Mã khách hàng");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        } catch (SQLException ex) {
            Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    public void loadTableTC(){
        
        TableColumn<ThuCung, Integer> colMaThuCung = new TableColumn("Mã Thú Cưng");
        colMaThuCung.setCellValueFactory(new PropertyValueFactory("idThuCung"));

        TableColumn<ThuCung, String> colTenThuCung = new TableColumn("Tên");
        colTenThuCung.setCellValueFactory(new PropertyValueFactory("ten"));
        colTenThuCung.setPrefWidth(120);

        TableColumn<ThuCung, String> colNgaySinh = new TableColumn("Ngày Sinh");
        colNgaySinh.setCellValueFactory(new PropertyValueFactory("ngaySinh"));
        colNgaySinh.setStyle("-fx-alignment: CENTER-RIGHT;");
        colNgaySinh.setPrefWidth(120);

        TableColumn<ThuCung, String> colGioiTinh = new TableColumn("Giới Tính");
        colGioiTinh.setCellValueFactory(new PropertyValueFactory("gioiTinh"));
        colGioiTinh.setStyle("-fx-alignment: CENTER-RIGHT;");
        colGioiTinh.setPrefWidth(100);
        
        TableColumn<ThuCung, String> colMauLong = new TableColumn("Màu Lông");
        colMauLong.setCellValueFactory(new PropertyValueFactory("mauLong"));
        colMauLong.setStyle("-fx-alignment: CENTER-RIGHT;");
        colMauLong.setPrefWidth(100);

        TableColumn<ThuCung, String> colSucKhoe = new TableColumn("Tình Trạng Sức Khỏe");
        colSucKhoe.setCellValueFactory(new PropertyValueFactory("tinhTrangSucKhoe"));
        colSucKhoe.setStyle("-fx-alignment: CENTER-RIGHT;");
        colSucKhoe.setPrefWidth(200);
        
        TableColumn<ThuCung, Integer> colIDKH = new TableColumn("Mã Khách Hàng");
        colIDKH.setCellValueFactory(new PropertyValueFactory("idKhachHang"));
        colIDKH.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        tbThuCung.getSelectionModel().selectedItemProperty().addListener(cell->{
            ThuCung tc = tbThuCung.getSelectionModel().getSelectedItem();
            if(tc != null){
            txtMaTC.setText(String.valueOf(tc.getIdThuCung()));
            txtTenTC.setText(tc.getTen());
            txtMauLong.setText(tc.getMauLong());
            txtSucKhoe.setText(tc.getTinhTrangSucKhoe());
            cbGioiTinhTC.setValue(tc.getGioiTinh());
            dpTC.setValue(LocalDate.parse(tc.getNgaySinh(), dateFormatter));
            cbMaKH.setValue(tc.getIdKhachHang());
        }
        
        });
        this.tbThuCung.getColumns().addAll(colMaThuCung, colTenThuCung
                ,colGioiTinh, colNgaySinh, colMauLong, colSucKhoe, colIDKH);
    }
    private void loadTableKH(){
        
        TableColumn<KhachHang, Integer> colMaKhachHang = new TableColumn("Mã Khách Hàng");
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory("idKhachHang"));

        TableColumn<KhachHang, String> colTenKhachHang = new TableColumn("Họ tên");
        colTenKhachHang.setCellValueFactory(new PropertyValueFactory("hoTen"));
        colTenKhachHang.setPrefWidth(120);

        TableColumn<KhachHang, String> colGioiTinh = new TableColumn("Giới Tính");
        colGioiTinh.setCellValueFactory(new PropertyValueFactory("gioiTinh"));
        colGioiTinh.setStyle("-fx-alignment: CENTER-RIGHT;");
        

        TableColumn<KhachHang, String> colDiaChi = new TableColumn("Địa Chỉ");
        colDiaChi.setCellValueFactory(new PropertyValueFactory("diaChi"));
        colDiaChi.setStyle("-fx-alignment: CENTER-LEFT;");
        
        TableColumn<KhachHang, String> colSdt = new TableColumn("Số Điện Thoại");
        colSdt.setCellValueFactory(new PropertyValueFactory("sdt"));
        colSdt.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<KhachHang, String> colDiemTichLuy = new TableColumn("Điểm Tích Lũy");
        colDiemTichLuy.setCellValueFactory(new PropertyValueFactory("diemTichLuy"));
        colDiemTichLuy.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<KhachHang, Integer> colTongDH = new TableColumn("Tổng Đơn Hàng");
        colTongDH.setCellValueFactory(new PropertyValueFactory("tongDH"));
        colTongDH.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        tableKH.getSelectionModel().selectedItemProperty().addListener(cell->{
            KhachHang kh = tableKH.getSelectionModel().getSelectedItem();
            if(kh != null){
            txtMaKH.setText(String.valueOf(kh.getIdKhachHang()));
            txtDiaChi.setText(kh.getDiaChi());
            txtHoTen.setText(kh.getHoTen());
            txtSDT.setText(kh.getSdt());
            txtDiemTL.setText(kh.getDiemTichLuy());
            cbGioiTinhKH.setValue(kh.getGioiTinh());
            dpKH.setValue(LocalDate.parse(kh.getNgaySinh(), dateFormatter));
        }
        
        });
        this.tableKH.getColumns().addAll(colMaKhachHang, colTenKhachHang
                ,colGioiTinh, colSdt, colTongDH, colDiemTichLuy, colDiaChi);
}
    
    public void loadKhachHang(String tuKhoa, String traCuu) throws SQLException{
        this.tableKH.getItems().clear();
        Connection conn = JdbcUtils.getConn();
        KhachHangService khs = new KhachHangService(conn);
        List<KhachHang> listKH = khs.getKhachHang(tuKhoa, traCuu);
        listKH.forEach(k->{
            try {
                k.setTongDH(khs.getSLDHByID(k.getIdKhachHang()));
            } catch (SQLException ex) {
                Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.tableKH.setItems(FXCollections.observableList(listKH));
        conn.close();
    }
    
        
    public void loadThuCung(String tuKhoa, String traCuu) throws SQLException{
        this.tbThuCung.getItems().clear();
        Connection conn = JdbcUtils.getConn();
        ThuCungService tcs = new ThuCungService(conn);
        
        List<ThuCung> listTC = tcs.getThuCung(tuKhoa, traCuu);
        
        this.tbThuCung.setItems(FXCollections.observableList(listTC));
        conn.close();
    }
    
    
    @FXML
    void capNhatKH(ActionEvent event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        KhachHangService khs = new KhachHangService(conn);
        KhachHang kh = this.tableKH.getSelectionModel().getSelectedItem();
        if(kiemTraKH()){
            kh.setIdKhachHang(Integer.parseInt(txtMaKH.getText()));
            kh.setDiaChi(txtDiaChi.getText());
            kh.setHoTen(txtHoTen.getText());
            kh.setGioiTinh(cbGioiTinhKH.getValue());
            kh.setNgaySinh(dpKH.getValue().format(dateFormatter));
            kh.setSdt(txtSDT.getText());
            kh.setDiemTichLuy(txtDiemTL.getText());

            if(khs.updateKH(kh)){
                Utils.getBox("Cập nhật khách hàng thành công!!", Alert.AlertType.INFORMATION).show();
            }
            else
                Utils.getBox("Cập nhật khách hàng thất bại!!", Alert.AlertType.ERROR).show();
            this.tableKH.getColumns().clear();
            loadKhachHang("", "");
            loadTableKH();
        }
    }

    @FXML
    void themKH(ActionEvent event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        KhachHangService khs = new KhachHangService(conn);
        if(kiemTraKH()){
            KhachHang kh = new KhachHang();
            kh.setDiaChi(txtDiaChi.getText());
            kh.setHoTen(txtHoTen.getText());
            kh.setGioiTinh(cbGioiTinhKH.getValue());
            kh.setNgaySinh(dpKH.getValue().format(dateFormatter));
            kh.setSdt(txtSDT.getText());
            kh.setDiemTichLuy(String.valueOf(0));

            if(khs.themKH(kh)){
                Utils.getBox("Thêm khách hàng thành công!!", Alert.AlertType.INFORMATION).show();
            }
            else
                Utils.getBox("Thêm khách hàng thất bại!!", Alert.AlertType.ERROR).show();
            this.tableKH.getColumns().clear();
            loadKhachHang("", "");
            loadTableKH();
        }
    }

    @FXML
    void capNhatTC(ActionEvent event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        ThuCungService tcs = new ThuCungService(conn);
        ThuCung tc = this.tbThuCung.getSelectionModel().getSelectedItem();
        
        txtMaTC.setText(String.valueOf(tc.getIdThuCung()));
        txtTenTC.setText(tc.getTen());
        txtMauLong.setText(tc.getMauLong());
        cbGioiTinhTC.setValue(tc.getGioiTinh());
        dpTC.setValue(LocalDate.parse(tc.getNgaySinh(), dateFormatter));
        txtSucKhoe.setText(tc.getTinhTrangSucKhoe());
        cbMaKH.setValue(tc.getIdKhachHang());
        
        if(tcs.updateTC(tc)){
            Utils.getBox("Cập nhật thú cưng thành công!!", Alert.AlertType.INFORMATION).show();
        }
        else
            Utils.getBox("Cập nhật thú cưng thất bại!!", Alert.AlertType.ERROR).show();
        
        this.tbThuCung.getColumns().clear();
        loadThuCung("", "");
        loadTableTC();
    }

    @FXML
    void themTC(ActionEvent event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        ThuCungService tcs = new ThuCungService(conn);
        
        ThuCung tc = new ThuCung();
        tc.setTen(txtTenTC.getText());
        tc.setMauLong(txtMauLong.getText());
        tc.setGioiTinh(cbGioiTinhTC.getValue());
        tc.setNgaySinh(dpTC.getValue().format(dateFormatter));
        tc.setTinhTrangSucKhoe(txtSucKhoe.getText());
        tc.setIdKhachHang(cbMaKH.getValue());
        
        if(tcs.themTC(tc)){
            Utils.getBox("Thêm Thú cưng thành công!!", Alert.AlertType.INFORMATION).show();
        }
        else
            Utils.getBox("Thêm Thú cưng thất bại!!", Alert.AlertType.ERROR).show();
        
        this.tbThuCung.getColumns().clear();
        loadThuCung("", "");
        loadTableTC();
    }
    
    private boolean kiemTraKH(){
        boolean flag = true;
        if(!(this.txtSDT.getText().matches("\\d+")) || this.txtSDT.getText().length() != 10 ){
            Utils.getBox("Vui lòng nhập đúng số điện thoại!", Alert.AlertType.ERROR).show();
            flag = false;
            return flag;
        }
        if(!(this.txtDiemTL.getText().matches("\\d+")) ){
            Utils.getBox("Vui lòng nhập đúng điểm tích lũy!", Alert.AlertType.ERROR).show();
            flag = false;
            return flag;
        }
        return flag;
    }
    
    
    public int tongDH() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        DonHangService dhs = new DonHangService(conn);
        
        return dhs.tongDH();
    }
    
    private void loadCBIDTC(){
        try {
            Connection conn = JdbcUtils.getConn();
            KhachHangService khs = new KhachHangService(conn);
            
            List<KhachHang> kh = khs.getKhachHang("", "");
            
            List<Integer> id = new ArrayList<>();
            kh.forEach(k->{
                id.add(k.getIdKhachHang());
            });
            ObservableList<Integer> list = FXCollections.observableArrayList(id);
            this.cbMaKH.setItems(list);
            this.fIDKH.setItems(list);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadCBGTKH(){
        ObservableList<String> list = FXCollections.observableArrayList("", "Nam","Nữ","Khác");
        this.cbGioiTinhKH.setItems(list);
        this.cbGioiTinhKH.getSelectionModel().selectFirst();
        this.fGTKH.setItems(list);
        this.fGTKH.getSelectionModel().selectFirst();
    }
    private void loadCBGTTC(){
        ObservableList<String> list = FXCollections.observableArrayList("", "Đực","Cái");
        this.cbGioiTinhTC.setItems(list);
        this.cbGioiTinhTC.getSelectionModel().selectFirst();
        this.fGTTC.setItems(list);
        this.fGTTC.getSelectionModel().selectFirst();
    }
}
