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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 * @author Admin
 */
public class TraCuuHangHoaQuanLyTruongController implements Initializable {
    
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
        nd = getTTUser(nd);
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectFirst();
        loadTable();
        loadHangHoa("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        });
        
        this.tbHangHoa.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(event -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    HangHoaService s = new HangHoaService(conn);
                    HangHoa hh = this.tbHangHoa.getSelectionModel().getSelectedItem();
                    int rindex = this.tbHangHoa.getSelectionModel().getSelectedIndex();
                    if (nd.getLoaiuser_id() == 1) {
                        TextInputDialog dialog = new TextInputDialog(hh.getGiaban().toString());
                        dialog.setTitle("Cập nhật");
                        dialog.setHeaderText("Hãy nhập giá bán:");
                        dialog.setContentText("Giá bán:");
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()){
                            TextField tf = dialog.getEditor();
                            if (tf.getText().matches("\\d+") && tf.getText().isEmpty() == false) {
                                if (tf.getText().length() > 9)
                                    Utils.getBox("Chỉ được nhập số nhỏ hơn 1.000.000.000", Alert.AlertType.ERROR).show();
                                else if (Integer.parseInt(tf.getText()) < 10000)
                                        Utils.getBox("Chỉ được nhập số lớn hơn 10.000", Alert.AlertType.ERROR).show();
                                    else {
                                        hh.setGiaban(new BigDecimal(result.get()));
                                        if (s.suaGiaBan(hh.getHanghoa_id(), hh.getGiaban().toString())) {
                                            Utils.getBox("Cập nhật giá bán thành công!", Alert.AlertType.INFORMATION).show();
                                            this.tbHangHoa.getItems().set(rindex, hh);
                                        } else
                                            Utils.getBox("Cập nhật giá bán thất bại!!!", Alert.AlertType.ERROR).show();
                                    }
                            } else
                                Utils.getBox("Chỉ được nhập số", Alert.AlertType.ERROR).show();
                        }
                    }
                    if (nd.getLoaiuser_id() == 2) {
                        
                    }
                        
                } catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            return r;
        });
    }
    
    public void setTTUser(User u){
        nd = u;
    }
    
    public User getTTUser(User u){
        nd = u;
        return nd;
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
        /*colGiaBan.setEditable(true);
        colGiaBan.setCellFactory(TextFieldTableCell.forTableColumn());
        colGiaBan.setOnEditCommit(new EventHandler<CellEditEvent<HangHoa, BigDecimal>>() {
            @Override public void handle(CellEditEvent<HangHoa, BigDecimal> t) {
                ((HangHoa) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGiaban(t.getNewValue());
            }
        });*/

        TableColumn colNgaySanXuat = new TableColumn("Ngày Sản Xuất");
        colNgaySanXuat.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
        
        TableColumn colNgayHetHan = new TableColumn("Ngày Hết Hạn");
        colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
        
        TableColumn colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
        colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("loaihanghoa_id"));
        
        /*if (nd.getLoaiuser_id() == 2){
            this.tbHangHoa.setEditable(true);
            colMaHangHoa.setEditable(true);
            colMaHangHoa.setCellFactory(TextFieldTableCell.forTableColumn());
            colMaHangHoa.setOnEditCommit(new EventHandler<CellEditEvent<HangHoa, Integer>>() {
                @Override public void handle(CellEditEvent<HangHoa, Integer> t) {
                    ((HangHoa) t.getTableView().getItems().get(t.getTablePosition().getRow())).setHanghoa_id(t.getNewValue());
                }
            });
        }*/
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
