/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.User;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TraCuuHangHoaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField txtTraCuu;
    @FXML private TableView<HangHoa> tbHangHoa;
    @FXML private ComboBox<String> cbTraCuu;
    ObservableList<String> list = FXCollections.observableArrayList
        ("Mã hàng", "Tên hàng", "Thương hiệu", "Loại hàng");
    User nd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTable();
        loadHangHoas();
        
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectFirst();
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        });
    }
    
    public void setTTUser(User u){
        nd = u;
    }
    
    public void loadHangHoas(){
        try {
            this.tbHangHoa.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            HangHoaService hhs = new HangHoaService(conn);
            this.tbHangHoa.setItems(FXCollections.observableList(
                    hhs.getHangHoas()));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TraCuuHangHoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadHangHoa(String tuKhoa, String traCuu){
        try {
            this.tbHangHoa.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            HangHoaService s = new HangHoaService(conn);
            this.tbHangHoa.setItems(FXCollections.observableList(
                    s.getHangHoa(tuKhoa, traCuu)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TraCuuHangHoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTable() {
        TableColumn colMaHangHoa = new TableColumn("Mã Hàng");
        colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));
        
        TableColumn colTenHangHoa = new TableColumn("Tên Hàng");
        colTenHangHoa.setCellValueFactory(new PropertyValueFactory("tenhanghoa"));
        
        TableColumn colThuongHieu = new TableColumn("Thương Hiệu");
        colThuongHieu.setCellValueFactory(new PropertyValueFactory("thuonghieu"));
        
        TableColumn colSoLuong = new TableColumn("Số Lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soluongtrongkho"));
        
        TableColumn colGiaNhap = new TableColumn("Giá Nhập");
        colGiaNhap.setCellValueFactory(new PropertyValueFactory("gianhap"));
        
        TableColumn colGiaBan = new TableColumn("Giá Bán");
        colGiaBan.setCellValueFactory(new PropertyValueFactory("giaban"));
        
        TableColumn colNgaySanXuat = new TableColumn("Ngày Sản Xuất");
        colNgaySanXuat.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
        
        TableColumn colNgayHetHan = new TableColumn("Ngày Hết Hạn");
        colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
        
        TableColumn colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
        colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("loaihanghoa_id"));
        
        this.tbHangHoa.getColumns().addAll(colMaHangHoa, colTenHangHoa, colThuongHieu
                , colSoLuong, colGiaNhap, colGiaBan, colNgaySanXuat, colNgayHetHan, colLoaiHangHoa);
    }
    
    public void logoutHandler(ActionEvent evt) throws IOException {
        Parent dangnhap;
        Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("dangnhap.fxml"));
        dangnhap = loader.load();
        Scene scene = new Scene(dangnhap);
        stage.setScene(scene);
        stage.show();

    }
    
    public void continueHandler(ActionEvent evt) throws IOException {
        Parent trangchu;
        var path="";
        //if (nd.getIdLoaiTK() == 1)
            //path = "UInhanvien.fxml";
        //if (nd.getIdLoaiTK() == 2)
            //path = "UIkhachhang.fxml";
        Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        trangchu = loader.load();
        Scene scene = new Scene(trangchu);
        TrangChuController controller = loader.getController();
        //controller.setTenTK(nd);
        stage.setScene(scene);
        stage.show();
    }
}
