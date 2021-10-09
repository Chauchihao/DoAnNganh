/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.LoaiUser;
import com.doannganh.pojo.User;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.LoaiUserService;
import com.doannganh.service.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class DangnhapController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private ComboBox<LoaiUser> cbLoaiUser;
    @FXML private TextField txtTaiKhoan;
    @FXML private PasswordField txtMatKhau;
    @FXML private Button btDangNhap;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = JdbcUtils.getConn();
            LoaiUserService s = new LoaiUserService(conn);
            this.cbLoaiUser.setItems(FXCollections.observableList(s.getLoaiTK()));
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DangnhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void dangNhapHandler(ActionEvent evt) {
        
        try {
            Connection conn = JdbcUtils.getConn();
            
            if (this.cbLoaiUser.getSelectionModel().isEmpty())
                Utils.getBox("Vui lòng chọn loại tài khoản!!!", Alert.AlertType.WARNING).show();
                else if (this.txtTaiKhoan.getText().isEmpty()) 
                    Utils.getBox("Vui lòng điền tài khoản!!!", Alert.AlertType.WARNING).show();
                    else if (this.txtMatKhau.getText().isEmpty()) 
                        Utils.getBox("Vui lòng điền mật khẩu!!!", Alert.AlertType.WARNING).show();
                        else {
                            UserService s = new UserService(conn);
                            User u = new User();
                            u.setLoaiuser_id(this.cbLoaiUser.getSelectionModel().getSelectedItem().getLoaiuser_id());
                            u.setTaikhoan(this.txtTaiKhoan.getText());
                            u.setMatkhau(this.txtMatKhau.getText());
                            
                            if (s.getUser(u.getTaikhoan()) == null)
                                    Utils.getBox("Tên tài khoản không tồn tại!!!", Alert.AlertType.WARNING).show();
                                else if (s.getUser(u.getTaikhoan()).getLoaiuser_id()!= u.getLoaiuser_id())
                                        Utils.getBox("Vui lòng chọn đúng loại tài khoản hoặc nhập đúng tài khoản!!!", Alert.AlertType.WARNING).show();
                                else if (s.login(u)){
                                    Parent trangchu;
                                    var path= "";
                                    Utils.getBox("Đăng nhập thành công!", Alert.AlertType.INFORMATION).show();
                                    try {

                                        if (u.getLoaiuser_id()== 1)
                                            path = "trangchuquanlytruong.fxml";
                                        if (u.getLoaiuser_id() == 2)
                                            path = "trangchuthukho.fxml";
                                        if (u.getLoaiuser_id() == 3)
                                            path = "trangchunhanvien.fxml";
                                        Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation(getClass().getResource(path));
                                        trangchu = loader.load();
                                        Scene scene = new Scene(trangchu);
                                        stage.setScene(scene);
                                        stage.show();
                                    } catch (IOException ex) {
                                        Logger.getLogger(DangnhapController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                    else
                                        Utils.getBox("Đăng nhập thất bại!!!", Alert.AlertType.WARNING).show();
                                        
                            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DangnhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
