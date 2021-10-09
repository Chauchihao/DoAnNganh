/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Admin
 */
public class TraCuuSuaChuaHangHoaController implements Initializable {
    @FXML private TableView<HangHoa> tbHangHoa;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTable();
        loadHangHoa();
    }
    
    public void loadHangHoa(){
        try {
            this.tbHangHoa.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            HangHoaService vmbs = new HangHoaService(conn);
            this.tbHangHoa.setItems(FXCollections.observableList(
                    vmbs.getHangHoas()));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TraCuuSuaChuaHangHoaController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    private void loadTable() {
        TableColumn colMaVe = new TableColumn("Mã Vé");
        colMaVe.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));
        
        TableColumn colHangVe = new TableColumn("Hạng Vé");
        colHangVe.setCellValueFactory(new PropertyValueFactory("tenhanghoa"));
        
        TableColumn colGiaVe = new TableColumn("Giá Vé");
        colGiaVe.setCellValueFactory(new PropertyValueFactory("thuonghieu"));
        
        TableColumn colMaGhe = new TableColumn("Mã Ghế");
        colMaGhe.setCellValueFactory(new PropertyValueFactory("soluongtrongkho"));
        
        TableColumn colNgayXuatVe = new TableColumn("Ngày Xuất Vé");
        colNgayXuatVe.setCellValueFactory(new PropertyValueFactory("gianhap"));
        
        TableColumn colTenNguoiDat = new TableColumn("Tên Người Đặt");
        colTenNguoiDat.setCellValueFactory(new PropertyValueFactory("giaban"));
        
        TableColumn colTenKH = new TableColumn("Tên Khách Hàng");
        colTenKH.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
        
        TableColumn colMaCB = new TableColumn("Mã Chuyến Bay");
        colMaCB.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
        
        TableColumn colTrangThai = new TableColumn("Trạng Thái");
        colTrangThai.setCellValueFactory(new PropertyValueFactory("loaihanghoa_id"));
        
        
        this.tbHangHoa.getColumns().addAll(colMaVe, colMaCB, colHangVe, colMaGhe, colTenKH, colTenNguoiDat, colGiaVe, colNgayXuatVe, colTrangThai);
    }
    
}
