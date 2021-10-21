/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.User;
import com.doannganh.service.NhaCungCapService;
import com.doannganh.service.JdbcUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class QuanLyNhaCungCapController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField txtTraCuu;
    @FXML private TableView<NhaCungCap> tbNCC;
    @FXML private ComboBox<String> cbTraCuu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.cbTraCuu.setItems(list);
        //this.cbTraCuu.getSelectionModel().selectFirst();
        loadTable();
        loadNCC();
        
        //this.txtTraCuu.textProperty().addListener((obj) -> {
            //loadNCC(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        //});
        
        /*this.tbNCC.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(evt -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    NhaCungCapService s = new NhaCungCapService(conn);
                    NhaCungCap hh = this.tbNCC.getSelectionModel().getSelectedItem();
                    int rindex = this.tbNCC.getSelectionModel().getSelectedIndex();
                    TextInputDialog dialog = new TextInputDialog(hh.getGianiemyet());
                    dialog.setTitle("Cập nhật");
                    dialog.setHeaderText("Hãy nhập giá niêm yết:");
                    dialog.setContentText("Giá niêm yết:");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        TextField tf = dialog.getEditor();
                        if (tf.getText().isEmpty())
                            Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                        else if (!tf.getText().matches("\\d+"))
                            Utils.getBox("Vui lòng chỉ nhập số!", Alert.AlertType.WARNING).show();
                        else if (result.get().equals(hh.getGianiemyet()))
                            Utils.getBox("Vui lòng thay đổi giá niêm yết để cập nhật!", Alert.AlertType.WARNING).show();
                        else {
                            if (tf.getText().length() > 9)
                                Utils.getBox("Vui lòng nhập giá niêm yết < 1.000.000.000", Alert.AlertType.WARNING).show();
                            else if (Integer.parseInt(tf.getText()) < 10000)
                                    Utils.getBox("Vui lòng nhập giá niêm yết >= 10.000", Alert.AlertType.WARNING).show();
                            else if (Integer.parseInt(tf.getText()) <= Integer.parseInt(hh.getGianhap())) {
                                Utils.getBox("Vui lòng nhập giá niêm yết > giá nhập: " + hh.getGianhap(), Alert.AlertType.WARNING).show();
                            }
                                else {
                                    hh.setGianiemyet(result.get());
                                    if (s.suaGiaNiemYet(hh.getHanghoa_id(), hh.getGianiemyet())) {
                                        Utils.getBox("Cập nhật giá bán thành công!", Alert.AlertType.INFORMATION).show();
                                        this.tbNCC.getItems().set(rindex, hh);
                                    } else
                                        Utils.getBox("Cập nhật giá bán thất bại!!!", Alert.AlertType.ERROR).show();
                                }
                        } 
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return r;
        });*/
    }
    
    public void loadNCC(){
        try {
            this.tbNCC.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            NhaCungCapService s = new NhaCungCapService(conn);
            this.tbNCC.setItems(FXCollections.observableList(
                    s.getNCC()));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTable(){
        TableColumn<NhaCungCap, Integer> colMaNCC = new TableColumn("Mã");
        colMaNCC.setCellValueFactory(new PropertyValueFactory("nhacungcap_id"));
        
        TableColumn<NhaCungCap, String> colTenCongTy = new TableColumn("Tên Công Ty");
        colTenCongTy.setCellValueFactory(new PropertyValueFactory("tencongty"));
        
        TableColumn<NhaCungCap, String> colDiaChi = new TableColumn("Địa Chỉ");
        colDiaChi.setCellValueFactory(new PropertyValueFactory("diachi"));
        
        TableColumn<NhaCungCap, String> colTinhThanh = new TableColumn("Tỉnh Thành");
        colTinhThanh.setCellValueFactory(new PropertyValueFactory("tinhthanh"));
        
        TableColumn<NhaCungCap, String> colQuocGia = new TableColumn("Quốc Gia");
        colQuocGia.setCellValueFactory(new PropertyValueFactory("quocgia"));
        
        TableColumn<NhaCungCap, String> colEmail = new TableColumn("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

        TableColumn<NhaCungCap, String> colSoDT = new TableColumn("Số điện thoại");
        colSoDT.setCellValueFactory(new PropertyValueFactory("sodt"));
        
        this.tbNCC.getColumns().addAll(colMaNCC, colTenCongTy, colDiaChi
                , colTinhThanh, colQuocGia, colEmail, colSoDT);
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
            Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
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
