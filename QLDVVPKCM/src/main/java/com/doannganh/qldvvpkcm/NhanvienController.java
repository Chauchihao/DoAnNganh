/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.ChiTietDonHang;
import com.doannganh.pojo.DonHang;
import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.KhachHang;
import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.NhaCungCap_HangHoa;
import com.doannganh.pojo.User;
import com.doannganh.service.ChiTietDonHangService;
import com.doannganh.service.DonHangService;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.KhachHangService;
import com.doannganh.service.NhaCungCapService;
import com.doannganh.service.UserService;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class NhanvienController implements Initializable {

    @FXML
    private TableView<User> tbNhanVien;

    @FXML
    private TextField txtHoTen;

    @FXML
    private DatePicker dpNgaySinh;

    @FXML
    private ComboBox<String> cbGT;

    @FXML
    private TextField txtCMND;

    @FXML
    private TextField txtTaiKhoan;

    @FXML
    private DatePicker dpNgayVaoLam;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtSDT;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<String> cbTrangThai;

    @FXML
    private ComboBox<String> cbLoaiNV;

    @FXML
    private TextField fMaNV;

    @FXML
    private TextField fHoTen;

    @FXML
    private TextField fSDT;

    @FXML
    private ComboBox<String> fLoaiNV;

    @FXML
    private ComboBox<String> fTrangThai;

    @FXML
    private DatePicker fNgayVaoLam;
    
    @FXML
    private TableView<User> tbDSNV;
  
    @FXML
    private TableView<User> tbDSTK;
    
    @FXML
    private ListView<NhaCungCap_HangHoa> listLSGDTK;
    
    @FXML
    private ListView<DonHang> listLSGDNV;

    @FXML
    private ListView<String> listCTGD;
    
    @FXML
    private ListView<String> listCTGDTK;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadNV("", "");
            loadTableNV();
            loadTableDSNV();
            loadTableDSTK();
            loadDataCBLoaiNV();
            loadDataCBTT();
            loadDataCBGT();
            this.fMaNV.textProperty().addListener((obj) -> {
                try {
                    loadNV(this.fMaNV.getText(), "Mã NV");
                } catch (SQLException ex) {
                    Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
                }
                });
            this.fHoTen.textProperty().addListener((obj) -> {
                try {
                    loadNV(this.fHoTen.getText(), "Họ tên");
                } catch (SQLException ex) {
                    Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
                }
                });
            this.fSDT.textProperty().addListener((obj) -> {
                try {
                    loadNV(this.fSDT.getText(), "sdt");
                } catch (SQLException ex) {
                    Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
                }
                });
            this.fLoaiNV.valueProperty().addListener((obj) -> {
                try {
                    if(this.fLoaiNV.getValue().equals("Quản lý trưởng")){
                        loadNV(String.valueOf(1), "Loại NV");
                    }
                    if(this.fLoaiNV.getValue().equals("Thủ kho")){
                        loadNV(String.valueOf(2), "Loại NV");
                    }
                    if(this.fLoaiNV.getValue().equals("Nhân viên")){
                        loadNV(String.valueOf(3), "Loại NV");
                    }
                    if(this.fLoaiNV.getValue().equals("")){
                        loadNV("", "");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.fNgayVaoLam.valueProperty().addListener((obj) -> {
                try {
                    loadNV(this.fNgayVaoLam.getValue().format(dateFormatter), "Ngày vào làm");
                } catch (SQLException ex) {
                    Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch(NullPointerException n){
                    try {
                        loadNV("", "");
                    } catch (SQLException ex) {
                        Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                });
            this.fTrangThai.valueProperty().addListener((obj) -> {
                try {                    
                    if(this.fTrangThai.getValue().equals("Đang làm")){
                        loadNV(String.valueOf(1), "Trạng thái");
                    }
                    if(this.fTrangThai.getValue().equals("Đã nghỉ")) {
                        loadNV(String.valueOf(0), "Trạng thái");
                    }
                    if(this.fTrangThai.getValue().equals("")) {
                        loadNV("", "");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        } catch (SQLException ex) {
            Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    private void loadTableNV() throws SQLException{
        TableColumn<User, Integer> colMaNhanVien = new TableColumn("Mã Nhân Viên");
        colMaNhanVien.setCellValueFactory(new PropertyValueFactory("user_id"));
        
        TableColumn<User, String> colHoTen = new TableColumn("Họ Tên");
        colHoTen.setCellValueFactory(new PropertyValueFactory("hoten"));
        colHoTen.prefWidthProperty().set(190);
        
        TableColumn<User, String> colSDT = new TableColumn("Số điện thoại");
        colSDT.setCellValueFactory(new PropertyValueFactory("sdt"));
        
        TableColumn<User, Integer> colSoLuongDH = new TableColumn("Số Lượng Đơn Hàng");
        colSoLuongDH.setCellValueFactory(new PropertyValueFactory("sldh"));
        colSoLuongDH.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<User, Integer> colLoaiNV = new TableColumn("Loại Nhân Viên");
        colLoaiNV.setCellValueFactory(new PropertyValueFactory("loaiuser_id"));
        colLoaiNV.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        colLoaiNV.setCellFactory(tc -> new TableCell<User, Integer>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
            super.updateItem(value, empty) ;
            if (empty) {
                setText(null);
            } else {
                if(null == value)setText("Nhân viên");
                else switch (value) {
                    case 1:
                        setText("Quản lý trưởng");
                        break;
                    case 2:
                        setText("Thủ kho");
                        break;
                    case 3:
                        setText("Nhân viên");
                        break;
                    }
                }
            }
            
        });
        
        
        TableColumn<User, Boolean> colTrangThai = new TableColumn("Trạng thái");
        colTrangThai.setCellValueFactory(new PropertyValueFactory("trangthai"));
        colTrangThai.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
         colTrangThai.setCellFactory(tc -> new TableCell<User, Boolean>() {
            @Override
            protected void updateItem(Boolean value, boolean empty) {
            super.updateItem(value, empty) ;
            if (empty) {
                setText(null);
            } else {
                if (value == true)
                    setText("Đang làm");
                else
                    setText("Đã nghỉ");
                }
            }
             
         });
        
        TableColumn<User, String> colNgayVaoLam = new TableColumn("Ngày Vào Làm");
        colNgayVaoLam.setCellValueFactory(new PropertyValueFactory("ngayvaolam"));
        
        tbNhanVien.getSelectionModel().selectedItemProperty().addListener(eh->{
            User u = tbNhanVien.getSelectionModel().getSelectedItem();
            if( u != null){
                this.txtHoTen.setText(u.getHoten());
                this.txtCMND.setText(u.getCmnd());
                this.txtDiaChi.setText(u.getDiachi());
                this.txtEmail.setText(u.getEmail());
                this.txtSDT.setText(u.getSdt());
                this.txtTaiKhoan.setText(u.getTaikhoan());
                this.cbGT.setValue(u.getGioitinh());
                switch (u.getLoaiuser_id()) {
                    case 1:
                        this.cbLoaiNV.setValue("Quản lý trưởng");
                        break;
                    case 2:
                        this.cbLoaiNV.setValue("Thủ kho");
                        break;
                    case 3:
                        this.cbLoaiNV.setValue("Nhân viên");
                        break;
                    }
                if(u.isTrangthai()){
                    this.cbTrangThai.setValue("Đang làm");
                }
                else
                    this.cbTrangThai.setValue("Đã nghỉ");
                
                this.dpNgaySinh.setValue(LocalDate.parse(u.getNgaysinh(), dateFormatter));
                this.dpNgayVaoLam.setValue(LocalDate.parse(u.getNgayvaolam(), dateFormatter));
            
        }
        });
        
        
        this.tbNhanVien.getColumns().addAll(colMaNhanVien, colHoTen, colSoLuongDH
                    , colSDT,colLoaiNV, colTrangThai, colNgayVaoLam);
    }
    
    
        private void loadTableDSNV() throws SQLException{
        TableColumn<User, Integer> colMaNhanVien = new TableColumn("Mã Nhân Viên");
        colMaNhanVien.setCellValueFactory(new PropertyValueFactory("user_id"));
        
        TableColumn<User, String> colHoTen = new TableColumn("Họ Tên");
        colHoTen.setCellValueFactory(new PropertyValueFactory("hoten"));
        colHoTen.prefWidthProperty().set(190);
        
        TableColumn<User, String> colSDT = new TableColumn("Số điện thoại");
        colSDT.setCellValueFactory(new PropertyValueFactory("sdt"));
        
        TableColumn<User, Integer> colSoLuongDH = new TableColumn("Số Lượng Đơn Hàng");
        colSoLuongDH.setCellValueFactory(new PropertyValueFactory("sldh"));
        colSoLuongDH.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        tbDSNV.getSelectionModel().selectedItemProperty().addListener(cell->{
            try {
                this.listLSGDNV.getItems().clear();
                Connection conn = JdbcUtils.getConn();
                DonHangService s = new DonHangService(conn);
                User u = tbDSNV.getSelectionModel().getSelectedItem();
                if( u != null){
                    loadLSGDNV(s.getDHByIDNV(u.getUser_id()));
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.tbDSNV.getColumns().addAll(colMaNhanVien, colHoTen, colSoLuongDH);
        
    }

    private void loadTableDSTK() throws SQLException{
        TableColumn<User, Integer> colMaNhanVien = new TableColumn("Mã Nhân Viên");
        colMaNhanVien.setCellValueFactory(new PropertyValueFactory("user_id"));
        
        TableColumn<User, String> colHoTen = new TableColumn("Họ Tên");
        colHoTen.setCellValueFactory(new PropertyValueFactory("hoten"));
        colHoTen.prefWidthProperty().set(190);
        
        TableColumn<User, String> colSDT = new TableColumn("Số điện thoại");
        colSDT.setCellValueFactory(new PropertyValueFactory("sdt"));
        
        TableColumn<User, Integer> colSoLuongDH = new TableColumn("Số Lượng Đơn Hàng");
        colSoLuongDH.setCellValueFactory(new PropertyValueFactory("sldh"));
        colSoLuongDH.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        tbDSTK.getSelectionModel().selectedItemProperty().addListener(cell->{
            try {
                this.listLSGDTK.getItems().clear();
                Connection conn = JdbcUtils.getConn();
                NhaCungCapService s = new NhaCungCapService(conn);
                User u = tbDSTK.getSelectionModel().getSelectedItem();
                
                if( u != null){
                    loadSLGDTK(s.getNCCHHByIDNV(u.getUser_id()));
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.tbDSTK.getColumns().addAll(colMaNhanVien, colHoTen, colSoLuongDH);
        
    }
    
    private void loadNV(String key, String loaiTC) throws SQLException{
        this.tbNhanVien.getItems().clear();
        Connection conn = JdbcUtils.getConn();
        UserService s = new UserService(conn);
        
        List<User> us = s.getListUserQLT(key, loaiTC);
        List<User> nv = new ArrayList<>();
        List<User> tk = new ArrayList<>();
        us.forEach(u->{
            try {
                u.setSldh(s.getSLDH(u.getUser_id(), u.getLoaiuser_id()));
                if(u.getLoaiuser_id() == 3){
                    nv.add(u);
                }
                if(u.getLoaiuser_id() == 2){
                    tk.add(u);
                }
            } catch (SQLException ex) {
                Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        this.tbNhanVien.setItems(FXCollections.observableList(us));
        this.tbDSNV.setItems(FXCollections.observableList(nv));
        this.tbDSTK.setItems(FXCollections.observableList(tk));

        conn.close();
    }
    
    
    
    @FXML
    void capNhatNV(ActionEvent event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        UserService s = new UserService(conn);
        User u = this.tbNhanVien.getSelectionModel().getSelectedItem();
        u.setHoten(txtHoTen.getText());
        u.setNgaysinh(dpNgaySinh.getValue().format(dateFormatter));
        u.setGioitinh(cbGT.getValue());
        u.setCmnd(txtCMND.getText());
        u.setTaikhoan(txtTaiKhoan.getText());
        u.setNgayvaolam(dpNgayVaoLam.getValue().format(dateFormatter));
        u.setEmail(txtEmail.getText());
        u.setDiachi(txtDiaChi.getText());
        u.setSdt(txtSDT.getText());
        if(cbTrangThai.getValue().equals("Đang làm")){
            u.setTrangthai(true);
        }
        else
            u.setTrangthai(false);
        switch (cbLoaiNV.getValue()) {
            case "Quản lý trưởng":
                u.setLoaiuser_id(1);
                break;
            case "Thủ kho":
                u.setLoaiuser_id(2);
                break;
            case "Nhân viên":
                u.setLoaiuser_id(3);
                break;
        }
        if(s.updateUser(u)){
            Utils.getBox("Cập nhật nhân viên thành công!", Alert.AlertType.INFORMATION).show();
            
        }
        else{
            Utils.getBox("Cập nhật nhân viên thất bại!", Alert.AlertType.ERROR).show();
        }
        loadNV("", "");
        loadTableNV();
        
    }
    
    @FXML
    void themNV(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
        Connection conn = JdbcUtils.getConn();
        UserService s = new UserService(conn);
        User u = new User();
        if(txtHoTen.getText().isBlank()){
            Utils.getBox("Vui lòng nhập họ tên!", Alert.AlertType.WARNING).show();
        }
        else if(dpNgaySinh.getValue() == null){
            Utils.getBox("Vui lòng nhập ngày sinh!", Alert.AlertType.WARNING).show();
        }
        
        else if(txtCMND.getText().isBlank()){
            Utils.getBox("Vui lòng nhập số CMND/CCCD!", Alert.AlertType.WARNING).show();
        }
        else if(!(txtCMND.getText().matches("\\d+"))){
            Utils.getBox("Vui lòng nhập số!", Alert.AlertType.WARNING).show();
        }
        else if(txtCMND.getText().length() != 12){
            Utils.getBox("Nhập sai số CMND/CCCD!", Alert.AlertType.WARNING).show();
        }
        else if(!(s.checkCMND(txtCMND.getText()))){
            Utils.getBox("Nhập trùng số CMND/CCCD!", Alert.AlertType.WARNING).show();
        }
        else if(cbGT.getValue().isBlank()){
            Utils.getBox("Vui lòng chọn giới tính!", Alert.AlertType.WARNING).show();
        }
        else if(txtTaiKhoan.getText().equals("")){
            Utils.getBox("Vui lòng nhập tên tài khoản!", Alert.AlertType.WARNING).show();
        }
        else if(!(s.checkTaiKhoan(txtTaiKhoan.getText()))){
            Utils.getBox("Trùng tên tài khoản!", Alert.AlertType.WARNING).show();
        }
        else if(dpNgayVaoLam.getValue() == null){
            Utils.getBox("Vui lòng nhập ngày vào làm!", Alert.AlertType.WARNING).show();
        }
        else if(txtEmail.getText().equals("")){
            Utils.getBox("Vui lòng nhập Email!", Alert.AlertType.WARNING).show();
        }
        else if(txtDiaChi.getText().equals("")){
            Utils.getBox("Vui lòng nhập địa chỉ!", Alert.AlertType.WARNING).show();
        }
        else if(txtSDT.getText().equals("")){
            Utils.getBox("Vui lòng nhập số điện thoại!", Alert.AlertType.WARNING).show();
        }
        else if(!(txtSDT.getText().matches("\\d+"))){
            Utils.getBox("Vui lòng nhập số điện thoại!", Alert.AlertType.WARNING).show();
        }
        else if(txtSDT.getText().length() != 10){
            Utils.getBox("Vui lòng nhập đúng số điện thoại!", Alert.AlertType.WARNING).show();
        }
        else if(!(s.checkSDT(txtSDT.getText()))){
            Utils.getBox("Trùng số điện thoại!", Alert.AlertType.WARNING).show();
        }
        else if(cbTrangThai.getValue().equals("")){
            Utils.getBox("Vui lòng chọn trạng thái!", Alert.AlertType.WARNING).show();
        }
        else if(cbLoaiNV.getValue().equals("")){
            Utils.getBox("Vui lòng chọn loại nhân viên!", Alert.AlertType.WARNING).show();
        }
        else{
            u.setHoten(txtHoTen.getText());
            u.setNgaysinh(dpNgaySinh.getValue().format(dateFormatter));
            u.setGioitinh(cbGT.getValue());
            u.setCmnd(txtCMND.getText());
            u.setTaikhoan(txtTaiKhoan.getText());
            u.setMatkhau(DigestUtils.md5Hex(String.valueOf(1)));
            u.setNgayvaolam(dpNgayVaoLam.getValue().format(dateFormatter));
            u.setEmail(txtEmail.getText());
            u.setDiachi(txtDiaChi.getText());
            u.setSdt(txtSDT.getText());

            if(cbTrangThai.getValue().equals("Đang làm")){
                u.setTrangthai(true);
            }
            else
                u.setTrangthai(false);
            switch (cbLoaiNV.getValue()) {
                case "Quản lý trưởng":
                    u.setLoaiuser_id(1);
                    break;
                case "Thủ kho":
                    u.setLoaiuser_id(2);
                    break;
                case "Nhân viên":
                    u.setLoaiuser_id(3);
                    break;
            }
            if(s.themUser(u)){
                 Utils.getBox("Thêm nhân viên thành công!", Alert.AlertType.INFORMATION).show();
                 loadNV("", "");
                loadTableNV();
                txtHoTen.clear();
                dpNgaySinh.valueProperty().setValue(null);
                txtCMND.clear();
                txtTaiKhoan.clear();
                dpNgayVaoLam.valueProperty().setValue(null);
                txtEmail.clear();
                txtDiaChi.clear();
                txtSDT.clear();
            }
            else
                 Utils.getBox("Thêm nhân viên thất bại!", Alert.AlertType.ERROR).show();
        }
    }

    private void loadLSGDNV(List<DonHang> dh) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        UserService us = new UserService(conn);
        DonHangService dhs = new DonHangService(conn);
        ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
        
        listLSGDNV.setCellFactory(p -> new ListCell<>(){
            @Override
            protected void updateItem(DonHang value, boolean empty) {
                super.updateItem(value, empty);
                if(empty){
                    setText(null);
                }
                else {
                    try {
                        if(value.getKhachhang_id() > 1){
                            setText("\t"+ value.getDonhang_id()+ "\t\t\t\t"+ value.getNgaytaodh()
                                    + "\t\t"+ value.getKhachhang_id()
                                + "\t\t\t"+ moneyFormat(ctdhs.tongDHByID(value.getDonhang_id())));
                        }
                        else if(value.getKhachhang_id() == 1){
                            setText("\t"+ value.getDonhang_id()+ "\t\t\t\t"+ value.getNgaytaodh()
                                    +"\t\t\t\t\t"+ moneyFormat(ctdhs.tongDHByID(value.getDonhang_id())));
                    }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
           
       });

        listLSGDNV.getItems().addAll(dh);

        listLSGDNV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DonHang>(){
            
            @Override
            public void changed(ObservableValue<? extends DonHang> ov, DonHang oldValue, DonHang newValue) {
                try {
                    listCTGD.getItems().clear();
                    Connection conn = JdbcUtils.getConn();
                    UserService us = new UserService(conn);
                    DonHangService dhs = new DonHangService(conn);
                    ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
                    KhachHangService khs = new KhachHangService(conn);
                    
                    
                    
                    listCTGD.getItems().add("Mã đơn hàng: "+String.valueOf(newValue.getDonhang_id())
                            + "\t\t\t\t\t\t\t\t\t\t\tNgày tạo đơn: "+ newValue.getNgaytaodh());
                    listCTGD.getItems().add("Chi tiết đơn hàng: ");
                    listCTGD.getItems().add("Mã hàng hóa \t\t\t\t Tên hàng hóa \t\t\t\t Số lượng \t\t\t Giá \t\t\tGiảm giá" );
                    List<ChiTietDonHang> listCtdh = ctdhs.getCTDH(newValue.getDonhang_id());
                        
                    listCtdh.forEach(ctdh ->{
                        listCTGD.getItems().add("\t" +ctdh.getHanghoa_id()+"\t\t\t\t" + ctdh.getTenhang().substring(0, 20).toUpperCase()
                                + "\t\t\t"+ ctdh.getSoluong()+"\t\t\t"+ moneyFormat(Integer.parseInt(ctdh.getDongia()))
                                +" \t\t\t" + ctdh.getGiamgia() );
                    });
                    listCTGD.getItems().add("TỔNG TIỀN: \t"+ moneyFormat(ctdhs.tongDHByID(newValue.getDonhang_id())));
                    if(newValue.getKhachhang_id() != 1){
                        KhachHang kh = khs.getKhachHangByID(newValue.getKhachhang_id());
                        listCTGD.getItems().add("Thông tin khách hàng:");
                        listCTGD.getItems().add("\tMã khách hàng: " + kh.getIdKhachHang());
                        listCTGD.getItems().add("\tHọ tên: "+ kh.getHoTen() );
                        listCTGD.getItems().add("\tNgày sinh: " + kh.getNgaySinh());
                        listCTGD.getItems().add("\tGiới tính: " + kh.getGioiTinh());
                        listCTGD.getItems().add("\tSố điện thoại: "+ kh.getSdt());
                        listCTGD.getItems().add("\tĐịa chỉ: " + kh.getDiaChi());
                        listCTGD.getItems().add("\tĐiểm tích lũy: " + kh.getDiemTichLuy());
                    }
                    User u = us.getUserByID(newValue.getNhanvien_id());
                    listCTGD.getItems().add("Thông tin nhân viên:");
                    listCTGD.getItems().add("\tMã nhân viên: " + u.getUser_id());
                    listCTGD.getItems().add("\tHọ tên: "+ u.getHoten() );
                    listCTGD.getItems().add("\tNgày sinh: " + u.getNgaysinh());
                    listCTGD.getItems().add("\tGiới tính: " + u.getGioitinh());
                    listCTGD.getItems().add("\tSố điện thoại: "+ u.getSdt());
                    listCTGD.getItems().add("\tĐịa chỉ: " + u.getDiachi());
                    listCTGD.getItems().add("\tNgày vào làm: " + u.getNgayvaolam());
                    listCTGD.getItems().add("\tTài khoản: "+ u.getTaikhoan());
                    listCTGD.getItems().add("\tEmail: " + u.getEmail());
                    if(u.getLoaiuser_id() == 3 )
                        listCTGD.getItems().add("\tChức vụ: " + "Nhân viên");
                    if(u.getLoaiuser_id() == 2 )
                        listCTGD.getItems().add("\tChức vụ: " + "Thủ kho");
                } catch (SQLException ex) {
                    Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
            
        });
    }
    
    private void loadSLGDTK(List<NhaCungCap_HangHoa> ncchh) throws SQLException{
         Connection conn = JdbcUtils.getConn();
        UserService us = new UserService(conn);
        
        listLSGDTK.setCellFactory(p -> new ListCell<>(){
            @Override
            protected void updateItem(NhaCungCap_HangHoa value, boolean empty) {
                super.updateItem(value, empty);
                if(empty){
                    setText(null);
                }
                else 
                    setText("\t"+ value.getHanghoa_id()+ "\t\t\t"+ value.getNgaynhap()
                        + "\t\t\t"+ value.getNhacungcap_id()
                        + "\t\t\t\t"+ moneyFormat(Integer.parseInt(value.getGianhap())));
                        
                }
            
       });
        listLSGDTK.getItems().addAll(ncchh);

        listLSGDTK.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NhaCungCap_HangHoa> (){
            
            @Override
            public void changed(ObservableValue<? extends NhaCungCap_HangHoa> ov, NhaCungCap_HangHoa oldValue, NhaCungCap_HangHoa newValue) {
                try {
                    listCTGDTK.getItems().clear();
                    Connection conn = JdbcUtils.getConn();
                    UserService us = new UserService(conn);
                    HangHoaService hhs = new HangHoaService(conn);
                    NhaCungCapService nccs =  new NhaCungCapService(conn);
                    
//                    NhaCungCap_HangHoa value = nccs.getNCCHH(newValue.getHanghoa_id(), newValue.getNhacungcap_id(), newValue.getNgaynhap());
                    
                    listCTGDTK.getItems().add("Tên hàng hóa" + "\t\t\t\tSố lượng nhập" + "\t\t\tGiá nhập"
                                                + "\t\t\t\tNgày sản xuất"+ "\t\t\tNgày hết hạn");
                    if(newValue != null)
                    {
                        HangHoa hh = hhs.getHangHoaByID(newValue.getHanghoa_id());

                        listCTGDTK.getItems().add(hh.getTenhanghoa().substring(0, 18) .toUpperCase()
                                + "\t\t\t" + newValue.getSoLuong() +"\t\t\t\t"+ moneyFormat(Integer.parseInt(newValue.getGianhap()))
                                + "\t\t\t" + newValue.getNgaysanxuat() + "\t\t\t"+newValue.getNgayhethan());

                        listCTGDTK.getItems().add("TỔNG TIỀN: \t" 
                                +  nccs.tinhChiPhiDH(newValue.getHanghoa_id(), newValue.getNgaynhap(), newValue.getNhacungcap_id()));

                        NhaCungCap ncc =  nccs.getNCCByID(newValue.getNhacungcap_id());

                        listCTGDTK.getItems().add("Thông tin nhà cung cấp: ");

                        listCTGDTK.getItems().add("\tMã nhà cung cấp: "+ ncc.getNhacungcap_id());
                        listCTGDTK.getItems().add("\tTên công ty: "+ ncc.getTencongty());
                        listCTGDTK.getItems().add("\tĐịa chỉ: "+ ncc.getDiachi());
                        listCTGDTK.getItems().add("\tTỉnh thành: "+ ncc.getTinhthanh());
                        listCTGDTK.getItems().add("\tQuốc gia: "+ ncc.getQuocgia());
                        listCTGDTK.getItems().add("\tEmail: "+ ncc.getEmail());
                        listCTGDTK.getItems().add("\tSố điện thoại: "+ ncc.getSodt());


                        User u = us.getUserByID(newValue.getNhanvien_id());
                        listCTGDTK.getItems().add("Thông tin nhân viên:");
                        listCTGDTK.getItems().add("\tMã nhân viên: " + u.getUser_id());
                        listCTGDTK.getItems().add("\tHọ tên: "+ u.getHoten() );
                        listCTGDTK.getItems().add("\tNgày sinh: " + u.getNgaysinh());
                        listCTGDTK.getItems().add("\tGiới tính: " + u.getGioitinh());
                        listCTGDTK.getItems().add("\tSố điện thoại: "+ u.getSdt());
                        listCTGDTK.getItems().add("\tĐịa chỉ: " + u.getDiachi());
                        listCTGDTK.getItems().add("\tNgày vào làm: " + u.getNgayvaolam());
                        listCTGDTK.getItems().add("\tTài khoản: "+ u.getTaikhoan());
                        listCTGDTK.getItems().add("\tEmail: " + u.getEmail());
                        listCTGDTK.getItems().add("\tChức vụ: " + "Thủ kho");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
            
        });
  
    }

    private void loadDataCBLoaiNV(){
        ObservableList<String> list = FXCollections.observableArrayList("","Quản lý trưởng", "Thủ kho", "Nhân viên");
        this.fLoaiNV.setItems(list);
        this.cbLoaiNV.setItems(list);
        this.cbLoaiNV.getSelectionModel().selectFirst();
    }
    private void loadDataCBTT(){
        ObservableList<String> list = FXCollections.observableArrayList("","Đang làm", "Đã nghỉ");
        this.fTrangThai.setItems(list);
        this.cbTrangThai.setItems(list);
        this.cbTrangThai.getSelectionModel().selectFirst();
    }
    private void loadDataCBGT(){
        ObservableList<String> list = FXCollections.observableArrayList("","Nam", "Nữ","Khác");
        this.cbGT.setItems(list);
    }

    public static String moneyFormat(int money){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return(formatter.format(money)+" VNĐ");
    }
    
}
