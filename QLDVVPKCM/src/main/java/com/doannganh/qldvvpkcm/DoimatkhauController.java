/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.User;
import static com.doannganh.qldvvpkcm.DangnhapController.nd;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.UserService;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class DoimatkhauController implements Initializable {

    @FXML
    private TextField txtMKCu;

    @FXML
    private TextField txtMKMoi;

    @FXML
    private TextField txtNhapLai;
    
    
    User u =  nd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    void doiMK(ActionEvent event) throws IOException {
        try {
            Connection conn = JdbcUtils.getConn();
            UserService us = new UserService(conn);
            String mkCu = DigestUtils.md5Hex(txtMKCu.getText());
            if(mkCu.equals(u.getMatkhau())){
                if(txtMKMoi.equals(txtNhapLai)){
                    u.setMatkhau(DigestUtils.md5Hex(txtMKMoi.getText()));
                    if(us.updateUser(u)){
                         Utils.getBox("Đổi mật khẩu thành công!", Alert.AlertType.INFORMATION).show();
                         App.setRoot("dangnhap");
                    }
                    else
                        Utils.getBox("Đổi mật khẩu thất bại!", Alert.AlertType.ERROR).show();
                }
                else
                    Utils.getBox("Mật khẩu mới không trùng khớp!", Alert.AlertType.ERROR).show();
            }
            else
                Utils.getBox("Nhập sai mật khẩu cũ!", Alert.AlertType.ERROR).show();
        } catch (SQLException ex) {
            Logger.getLogger(DoimatkhauController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
