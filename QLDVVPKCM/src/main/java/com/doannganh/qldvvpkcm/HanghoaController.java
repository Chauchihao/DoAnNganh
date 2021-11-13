/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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

    @FXML
    void loadTableHH(ActionEvent event) {

    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTable();
    }    
    private void loadTable(){
        TableColumn<HangHoa, Integer> colMaHangHoa = new TableColumn("Mã Hàng");
        colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));
        
        TableColumn<HangHoa, String> colTenHangHoa = new TableColumn("Tên Hàng");
        colTenHangHoa.setCellValueFactory(new PropertyValueFactory("tenhanghoa"));
        
        TableColumn<HangHoa, String> colThuongHieu = new TableColumn("Thương Hiệu");
        colThuongHieu.setCellValueFactory(new PropertyValueFactory("thuonghieu"));
        
        TableColumn<HangHoa, BigDecimal> colSoLuong = new TableColumn("Số Lượng");
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
        
        TableColumn<HangHoa, java.util.Date> colNgayHetHan = new TableColumn("Ngày Hết Hạn");
        colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
        
        
        TableColumn<HangHoa, Boolean> colTinhTrang = new TableColumn("Tình trạng");
        colTinhTrang.setCellValueFactory(new PropertyValueFactory("tinhtrang"));
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
        
        TableColumn<HangHoa, String> colNhaCungCap= new TableColumn("Nhà Cung Cấp");
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory("nhacungcap"));
        
        this.tbHH.getColumns().addAll(colMaHangHoa, colTenHangHoa, colLoaiHangHoa
                , colSoLuong, colGiaNhap, colGiaNiemYet, colNgaySanXuat, colNgayHetHan
                , colTinhTrang, colThuongHieu, colNhaCungCap);
    }

    
}
