/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private AnchorPane table;

    @FXML
    private TextField txtMaHH;

    @FXML
    private TextField txtTenHH;

    @FXML
    private TextField txtLoaiHH;

    @FXML
    private TextField txtSoLuong;

    @FXML
    private TextField txtGiaNhap;

    @FXML
    private TextField txtGiaNiemYet;

    @FXML
    private DatePicker NgaySX;

    @FXML
    private DatePicker NgayHH;

    @FXML
    private TextField txtTinhTrang;

    @FXML
    private TextField txtThuongHieu;

    @FXML
    private TextField txtNSX;

    @FXML
    private TextField txtSLDB;

    @FXML
    private TableView<HangHoa> tbHH;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadTable();
            loadHHQLT("","");
            this.txtMaHH.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtMaHH.getText(), "Mã");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.txtTenHH.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtTenHH.getText(), "Tên");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.txtLoaiHH.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtLoaiHH.getText(), "Loại");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.txtSoLuong.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtSoLuong.getText(), "Số lượng");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.txtSLDB.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtSLDB.getText(), "Số lượng đã bán");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.txtGiaNhap.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtGiaNhap.getText(), "Giá nhập");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.txtGiaNiemYet.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtGiaNiemYet.getText(), "Giá niêm yết");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.NgaySX.converterProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.NgaySX.getValue().format(dateFormatter), "Ngày sx");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.NgayHH.converterProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.NgayHH.getValue().format(dateFormatter), "Ngày hh");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.txtTinhTrang.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtTinhTrang.getText(), "Tình trạng");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.txtThuongHieu.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtThuongHieu.getText(), "Thương hiệu");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.txtNSX.textProperty().addListener((obj) -> {
                try {
                    loadHHQLT(this.txtNSX.getText(), "Nhà cung cấp");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    
    private void loadTable(){
        TableColumn<HangHoa, Integer> colMaHangHoa = new TableColumn("Mã Hàng");
        colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));
        
        TableColumn<HangHoa, String> colTenHangHoa = new TableColumn("Tên Hàng");
        colTenHangHoa.setCellValueFactory(new PropertyValueFactory("tenhanghoa"));
        colTenHangHoa.prefWidthProperty().set(225);
        
        
        TableColumn<HangHoa, String> colThuongHieu = new TableColumn("Thương Hiệu");
        colThuongHieu.setCellValueFactory(new PropertyValueFactory("thuonghieu"));
        
        TableColumn<HangHoa, BigDecimal> colSoLuong = new TableColumn("Số Lượng Trong Kho");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soluongtrongkho"));
        colSoLuong.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, BigDecimal> colGiaNhap = new TableColumn("Giá Nhập");
        colGiaNhap.setCellValueFactory(new PropertyValueFactory("gianhap"));
        colGiaNhap.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, BigDecimal> colGiaNiemYet = new TableColumn("Giá Niêm Yết");
        colGiaNiemYet.setCellValueFactory(new PropertyValueFactory("gianiemyet"));
        colGiaNiemYet.setStyle( "-fx-alignment: CENTER-RIGHT;");

        TableColumn<HangHoa, java.util.Date> colNgaySanXuat = new TableColumn("Ngày Sản Xuất");
        colNgaySanXuat.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
        colNgaySanXuat.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, java.util.Date> colNgayHetHan = new TableColumn("Ngày Hết Hạn");
        colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
        colNgayHetHan.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        
        TableColumn<HangHoa, Boolean> colTinhTrang = new TableColumn("Tình trạng");
        colTinhTrang.setCellValueFactory(new PropertyValueFactory("tinhtrang"));
        colTinhTrang.setStyle( "-fx-alignment: CENTER-RIGHT;");
        //colTinhTrang.setEditable(false);
        List l = new ArrayList<>();
        l.add(0,false);
        l.add(1,true);
        ObservableList listTT = FXCollections.observableList(l);
        colTinhTrang.setCellFactory((TableColumn<HangHoa, Boolean> p) -> {
            ComboBoxTableCell<HangHoa, Boolean> cell = new ComboBoxTableCell<>(listTT);
            return cell;
        });
        
        TableColumn<HangHoa, String> colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
        colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("tenloaihang"));
        colLoaiHangHoa.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, String> colNhaCungCap= new TableColumn("Nhà Cung Cấp");
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory("nhacungcap"));
        colNhaCungCap.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, String> colSLDaBan= new TableColumn("Số Lượng Đã Bán");
        colSLDaBan.setCellValueFactory(new PropertyValueFactory("slDaBan"));
        colSLDaBan.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        this.tbHH.getColumns().addAll(colMaHangHoa, colTenHangHoa, colLoaiHangHoa
                , colSoLuong,colSLDaBan, colGiaNhap, colGiaNiemYet, colNgaySanXuat, colNgayHetHan
                , colTinhTrang, colThuongHieu, colNhaCungCap);
    }

    
    
    public void loadHHQLT(String key, String loaiTC) throws SQLException{
        this.tbHH.getItems().clear();
        Connection conn = JdbcUtils.getConn();
        HangHoaService s = new HangHoaService(conn);
        this.tbHH.setItems(FXCollections.observableList(s.getHHQLT(key, loaiTC)));
        conn.close();
    }
}
