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
import com.doannganh.service.LoaiHangHoaService;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.DateStringConverter;

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
        ("Mã hàng", "Tên hàng", "Thương hiệu", "Loại hàng", "Nhà cung cấp");
    User nd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                    TextInputDialog dialog = new TextInputDialog(hh.getGiaban().toString());
                    dialog.setTitle("Cập nhật");
                    dialog.setHeaderText("Hãy nhập giá bán:");
                    dialog.setContentText("Giá bán:");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        TextField tf = dialog.getEditor();
                        if (tf.getText().isEmpty())
                            Utils.getBox("Vui lòng không để trống!", Alert.AlertType.ERROR).show();
                        else if (!tf.getText().matches("\\d+"))
                            Utils.getBox("Vui lòng chỉ nhập số!", Alert.AlertType.ERROR).show();
                        else if (result.get().equals(hh.getGiaban().toString()))
                            Utils.getBox("Vui lòng thay đổi giá bán để cập nhật!", Alert.AlertType.ERROR).show();
                        else {
                            if (tf.getText().length() > 9)
                                Utils.getBox("Vui lòng nhập giá bán < 1.000.000.000", Alert.AlertType.ERROR).show();
                            else if (Integer.parseInt(tf.getText()) < 10000)
                                    Utils.getBox("Vui lòng nhập giá bán >= 10.000", Alert.AlertType.ERROR).show();
                            else if (Integer.parseInt(tf.getText()) <= Integer.parseInt(hh.getGianhap().toString())) {
                                Utils.getBox("Vui lòng nhập giá bán > giá nhập: " + hh.getGianhap().toString(), Alert.AlertType.ERROR).show();
                            }
                                else {
                                    hh.setGiaban(new BigDecimal(result.get()));
                                    if (s.suaGiaBan(hh.getHanghoa_id(), hh.getGiaban().toString())) {
                                        Utils.getBox("Cập nhật giá bán thành công!", Alert.AlertType.INFORMATION).show();
                                        this.tbHangHoa.getItems().set(rindex, hh);
                                    } else
                                        Utils.getBox("Cập nhật giá bán thất bại!!!", Alert.AlertType.ERROR).show();
                                }
                        } 
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
            Logger.getLogger(TraCuuHangHoaQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        TableColumn<HangHoa, BigDecimal> colGiaNhap = new TableColumn("Giá Nhập");
        colGiaNhap.setCellValueFactory(new PropertyValueFactory("gianhap"));
        
        TableColumn<HangHoa, BigDecimal> colGiaBan = new TableColumn("Giá Bán");
        colGiaBan.setCellValueFactory(new PropertyValueFactory("giaban"));

        TableColumn<HangHoa, java.util.Date> colNgaySanXuat = new TableColumn("Ngày Sản Xuất");
        colNgaySanXuat.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
        
        TableColumn<HangHoa, java.util.Date> colNgayHetHan = new TableColumn("Ngày Hết Hạn");
        colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
        
        TableColumn<HangHoa, String> colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
        colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("tenloaihang"));
        
        TableColumn<HangHoa, String> colNhaCungCap= new TableColumn("Nhà Cung Cấp");
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory("nhacungcap"));
        
        this.tbHangHoa.getColumns().addAll(colMaHangHoa, colTenHangHoa, colThuongHieu
                , colSoLuong, colGiaNhap, colGiaBan, colNgaySanXuat, colNgayHetHan
                , colLoaiHangHoa, colNhaCungCap);
    }
    
    public void logoutHandler(ActionEvent evt) throws IOException {
        try {
            Parent root;
            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("dangnhap.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TraCuuHangHoaQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
