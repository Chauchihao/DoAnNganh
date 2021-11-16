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
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @FXML private AnchorPane apDangNhap;
    @FXML private VBox vb;
    @FXML private Label lbDangNhap;
    Label lb = new Label("");
    public static User nd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = JdbcUtils.getConn();
            LoaiUserService s = new LoaiUserService(conn);
            this.cbLoaiUser.setItems(FXCollections.observableList(s.getLoaiTK()));
            this.cbLoaiUser.getSelectionModel().selectLast();
            apDangNhap.setOnKeyPressed(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    if (dangnhap()) {
                        var path= "";
                        try {
                            if (nd.getLoaiuser_id()== 1)
                            {
                                App.setRoot("trangchuquanlytruong");
                                TrangChuQuanLyTruongController.setTTUser(nd);
                            }
                            if (nd.getLoaiuser_id() == 2){
                                App.setRoot("trangchuthukho");
                                TrangChuController.setTTUser(nd);
                            }

                            if (nd.getLoaiuser_id() == 3){
                                App.setRoot("trangchunhanvien");
                                TrangChuController.setTTUser(nd);
                            }
                            /*if (nd.getLoaiuser_id()== 1)
                                path = "trangchuquanlytruong.fxml";
                            if (nd.getLoaiuser_id() == 2)
                                path = "trangchuthukho.fxml";
                            if (nd.getLoaiuser_id() == 3)
                                path = "trangchunhanvien.fxml";
                            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                            Parent root = (Parent) loader.load();
                            TrangChuController controller = (TrangChuController) loader.getController();
                            controller.setTTUser(nd);
                            stage.setScene(new Scene(root));*/
                        } catch (IOException ex) {
                            Logger.getLogger(DangnhapController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DangnhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public boolean dangnhap() {
        try {
            Connection conn = JdbcUtils.getConn();
            //this.lb.setStyle(this.vb.getChildren().get(0).getStyle());
            this.lb.setAlignment(Pos.CENTER);
            this.lb.setPrefWidth(this.vb.getPrefWidth());
            this.lb.setStyle("-fx-background-color: cornsilk;" +
                "-fx-padding: 10;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 5;" +
                "-fx-font-size: 20;");
            if (this.cbLoaiUser.getSelectionModel().isEmpty()) {
                this.vb.getChildren().remove(this.lb);
                this.lb.setText("Vui lòng chọn loại tài khoản!!!");
                this.vb.getChildren().add(1, this.lb);
            } else if (this.txtTaiKhoan.getText().isEmpty())  {
                this.vb.getChildren().remove(this.lb);
                this.lb.setText("Vui lòng nhập tài khoản!!!");
                this.vb.getChildren().add(1, this.lb);
            } else if (this.txtMatKhau.getText().isEmpty()) {
                    this.vb.getChildren().remove(this.lb);
                    this.lb.setText("Vui lòng nhập mật khẩu!!!");
                    this.vb.getChildren().add(1, lb);
            } else {
                UserService s = new UserService(conn);
                User u = new User();
                u.setLoaiuser_id(this.cbLoaiUser.getSelectionModel().getSelectedItem().getLoaiuser_id());
                u.setTaikhoan(this.txtTaiKhoan.getText());
                u.setMatkhau(this.txtMatKhau.getText());

                if (s.getUser(u.getTaikhoan()) == null) {
                    this.vb.getChildren().remove(this.lb);
                    this.lb.setText("Tài khoản không tồn tại!!!");
                    this.vb.getChildren().add(1, lb);
                } else if (s.getUser(u.getTaikhoan()).getLoaiuser_id()!= u.getLoaiuser_id()) {
                    this.vb.getChildren().remove(this.lb);
                    this.lb.setText("Sai loại tài khoản hoặc tài khoản!!!");
                    this.vb.getChildren().add(1, lb);
                } else if (s.login(u)) {
                    if ((s.getUser(u.getTaikhoan()).isTrangthai()== true)) {
                        nd = s.getUser(u.getTaikhoan());
                        return true;
                    } else {
                        this.vb.getChildren().remove(this.lb);
                        this.lb.setText("Tài khoản đã bị khóa!!");
                        this.vb.getChildren().add(1, lb);
                        return false;
                    }
                } else {
                    this.vb.getChildren().remove(this.lb);
                    this.lb.setText("Sai tài khoản hoặc mật khẩu!!!");
                    this.vb.getChildren().add(1, lb);
                    return false;
                }
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DangnhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void dangNhapHandler(ActionEvent evt) throws IOException{
        try {
            if (dangnhap()) {
                var path= "";
                if (nd.getLoaiuser_id()== 1)
                {
                    App.setRoot("trangchuquanlytruong");
                    TrangChuQuanLyTruongController.setTTUser(nd);
                }
                if (nd.getLoaiuser_id() == 2){
                    App.setRoot("trangchuthukho");
                    TrangChuController.setTTUser(nd);
                }
                    
                if (nd.getLoaiuser_id() == 3){
                    App.setRoot("trangchunhanvien");
                    TrangChuController.setTTUser(nd);
                }
                    
//                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
//                Parent root = (Parent) loader.load();
//                TrangChuController controller = loader.getController();
//                controller.setTTUser(nd);

//                Scene scene = new Scene(root);
//                Stage stage = new Stage();
                //stage.setMaximized(true);
//                stage.setScene(scene);
//                stage = App.setSize(stage);
                //stage.sizeToScene();
//                stage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(DangnhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
