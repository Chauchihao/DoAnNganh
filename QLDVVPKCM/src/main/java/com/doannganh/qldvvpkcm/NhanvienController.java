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
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private Button btCapNhat;

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
    private TextField tHoTen;

    @FXML
    private DatePicker tNgaySinh;

    @FXML
    private ComboBox<String> tGioiTinh;

    @FXML
    private TextField tDiaChi;

    @FXML
    private TextField tCMND;

    @FXML
    private TextField tSDT;

    @FXML
    private TextField tEmail;

    @FXML
    private TextField tTaiKhoan;

    @FXML
    private PasswordField tMatKhau;

    @FXML
    private DatePicker tNgayVaoLam;

    @FXML
    private ComboBox<String> tLoaiNV;

    @FXML
    private ComboBox<String> tTrangThai;

    @FXML
    private TreeTableView<String> tbLSGD;

    @FXML
    private ComboBox<String> cbTimKiem;
    
    @FXML
    private TextField txtTimKiem;
    
    @FXML
    private ComboBox<String> cbTimKiem1;

    @FXML
    private TextField txtTimKiem1;
  
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
            tNgaySinh.setEditable(false);
            tNgayVaoLam.setEditable(false);
            dpNgaySinh.setEditable(false);
            dpNgayVaoLam.setEditable(false);
            loadNV("", "");
            loadTableNV();
            loadDataCBLoaiNV();
            loadDataCBTT();
            loadDataCBGT();
            loaDataCBTK();
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
                        listCTGD.getItems().add("\t" +ctdh.getHanghoa_id()+"\t\t\t\t" + ctdh.getTenhang().substring(0, 25).toUpperCase()
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
                } catch (SQLException ex) {
                    Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
            
        });
  
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
        
        tbNhanVien.setOnMouseClicked(eh->{
            if(eh.getClickCount() > 1){
                User u = tbNhanVien.getSelectionModel().getSelectedItem();
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
                        this.cbLoaiNV.setValue("Thủ kkho");
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
    
    
    private void loadNV(String key, String loaiTC) throws SQLException{
        this.tbNhanVien.getItems().clear();
        Connection conn = JdbcUtils.getConn();
        UserService s = new UserService(conn);
        
        List<User> us = s.getListUserQLT(key, loaiTC);
        us.forEach(u->{
            try {
                u.setSldh(s.getSLDH(u.getUser_id(), u.getLoaiuser_id()));
            } catch (SQLException ex) {
                Logger.getLogger(NhanvienController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        this.tbNhanVien.setItems(FXCollections.observableList(us));
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
    void themNV(ActionEvent event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        UserService s = new UserService(conn);
        User u = new User();
        if(tHoTen.getText().isBlank()){
            Utils.getBox("Vui lòng nhập họ tên!", Alert.AlertType.WARNING).show();
        }
        else if(tNgaySinh.getValue() == null){
            Utils.getBox("Vui lòng nhập ngày sinh!", Alert.AlertType.WARNING).show();
        }
        
        else if(tCMND.getText().isBlank()){
            Utils.getBox("Vui lòng nhập số CMND/CCCD!", Alert.AlertType.WARNING).show();
        }
        else if(!(tCMND.getText().matches("\\d+"))){
            Utils.getBox("Vui lòng nhập số!", Alert.AlertType.WARNING).show();
        }
        else if(tCMND.getText().length() > 12){
            Utils.getBox("Nhập dư số cmnd!", Alert.AlertType.WARNING).show();
        }
        else if(tGioiTinh.getValue().isBlank()){
            Utils.getBox("Vui lòng chọn giới tính!", Alert.AlertType.WARNING).show();
        }
        else if(tTaiKhoan.getText().equals("")){
            Utils.getBox("Vui lòng nhập tên tài khoản!", Alert.AlertType.WARNING).show();
        }
        else if(tMatKhau.getText().equals("")){
            Utils.getBox("Vui lòng nhập mật khẩu!", Alert.AlertType.WARNING).show();
        }
        else if(tNgayVaoLam.getValue() == null){
            Utils.getBox("Vui lòng nhập ngày vào làm!", Alert.AlertType.WARNING).show();
        }
        else if(tEmail.getText().equals("")){
            Utils.getBox("Vui lòng nhập Email!", Alert.AlertType.WARNING).show();
        }
        else if(tDiaChi.getText().equals("")){
            Utils.getBox("Vui lòng nhập địa chỉ!", Alert.AlertType.WARNING).show();
        }
        else if(tSDT.getText().equals("")){
            Utils.getBox("Vui lòng nhập số điện thoại!", Alert.AlertType.WARNING).show();
        }
        else if(!(tSDT.getText().matches("\\d+"))){
            Utils.getBox("Vui lòng nhập số điện thoại!", Alert.AlertType.WARNING).show();
        }
        else if(tSDT.getText().length() > 10){
            Utils.getBox("Vui lòng nhập đúng số điện thoại!", Alert.AlertType.WARNING).show();
        }
        else if(tTrangThai.getValue().equals("")){
            Utils.getBox("Vui lòng chọn trạng thái!", Alert.AlertType.WARNING).show();
        }
        else if(tLoaiNV.getValue().equals("")){
            Utils.getBox("Vui lòng chọn loại nhân viên!", Alert.AlertType.WARNING).show();
        }
        else{
            u.setHoten(tHoTen.getText());
            u.setNgaysinh(tNgaySinh.getValue().format(dateFormatter));
            u.setGioitinh(tGioiTinh.getValue());
            u.setCmnd(tCMND.getText());
            u.setTaikhoan(tTaiKhoan.getText());
            u.setMatkhau(tMatKhau.getText());
            u.setNgayvaolam(tNgayVaoLam.getValue().format(dateFormatter));
            u.setEmail(tEmail.getText());
            u.setDiachi(tDiaChi.getText());
            u.setSdt(tSDT.getText());

            if(tTrangThai.getValue().equals("Đang làm")){
                u.setTrangthai(true);
            }
            else
                u.setTrangthai(false);
            switch (tLoaiNV.getValue()) {
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
                tHoTen.clear();
                tNgaySinh.valueProperty().setValue(null);
                tCMND.clear();
                tTaiKhoan.clear();
                tMatKhau.clear();
                tNgayVaoLam.valueProperty().setValue(null);
                tEmail.clear();
                tDiaChi.clear();
                tSDT.clear();
            }
            else
                 Utils.getBox("Thêm nhân viên thất bại!", Alert.AlertType.ERROR).show();
        }
    }
    
    @FXML
    void timNV(ActionEvent event) throws SQLException {
        try{
            this.listLSGDNV.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            UserService us = new UserService(conn);
            DonHangService dhs = new DonHangService(conn);


            User u = new User();
            if(!(this.txtTimKiem.getText().matches("\\d+"))){
                Utils.getBox("Chỉ nhập số!", Alert.AlertType.ERROR).show();
            }
            else if(this.cbTimKiem.getValue().equals("Mã nhân viên")){
                u = us.getUserByID(Integer.parseInt(this.txtTimKiem.getText()));
            }
            else if(this.cbTimKiem.getValue().equals("CMND/CCCD")){
                if(this.txtTimKiem.getText().length() > 12){
                    Utils.getBox("Vui lòng nhập đúng số CMND/CCCD!", Alert.AlertType.ERROR).show();
                }
                else
                    u = us.getUserByCMND(Integer.parseInt(this.txtTimKiem.getText()));
            }
            else if(this.cbTimKiem.getValue().equals("Số điện thoại")){
                if(this.txtTimKiem.getText().length() >10){
                    Utils.getBox("Vui lòng nhập đúng số điện thoại!", Alert.AlertType.ERROR).show();
                }
                else
                    u = us.getUserBySDT(Integer.parseInt(this.txtTimKiem.getText()));
            }
            if(u.getLoaiuser_id() != 3){
             Utils.getBox("Chỉ tìm nhân viên bán hàng!", Alert.AlertType.ERROR).show();
            }
            else{
                List<DonHang> dh = dhs.getDHByIDNV(u.getUser_id());
                loadLSGDNV(dh);
            }
        }
        catch(NumberFormatException msg){
           Utils.getBox("Vui lòng nhập đúng thông tin!", Alert.AlertType.ERROR).show();
        }
        
    }
    
    @FXML
    void timTK(ActionEvent event) throws SQLException {
        try{
            this.listLSGDTK.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            UserService us = new UserService(conn);
            NhaCungCapService nccs = new NhaCungCapService(conn);

            User u = new User();
            if(!(this.txtTimKiem1.getText().matches("\\d+"))){
                Utils.getBox("Chỉ nhập số!", Alert.AlertType.ERROR).show();
            }
            else if(this.cbTimKiem1.getValue().equals("Mã nhân viên")){
                u = us.getUserByID(Integer.parseInt(this.txtTimKiem1.getText()));
            }
            else if(this.cbTimKiem1.getValue().equals("CMND/CCCD")){
                if(this.txtTimKiem1.getText().length() >12){
                    Utils.getBox("Vui lòng nhập đúng số CMND/CCCD!", Alert.AlertType.ERROR).show();
                }
                else{
                    u = us.getUserByCMND(Integer.parseInt(this.txtTimKiem1.getText()));                        
                }
            }
            else if(this.cbTimKiem1.getValue().equals("Số điện thoại")){
                if(this.txtTimKiem1.getText().length() >10){
                    Utils.getBox("Vui lòng nhập đúng số điện thoại!", Alert.AlertType.ERROR).show();
                }
                else{
                    u = us.getUserBySDT(Integer.parseInt(this.txtTimKiem1.getText()));
                }
            }
            if(u.getLoaiuser_id() != 2){
                 Utils.getBox("Chỉ tìm nhân viên có chức vụ là thủ kho!", Alert.AlertType.ERROR).show();
            }
            else{
                List<NhaCungCap_HangHoa> ncchh = nccs.getNCCHHByIDNV(u.getUser_id());
                loadSLGDTK(ncchh);
            }
        }
        catch(NumberFormatException e){
             Utils.getBox("Vui lòng nhập đúng thông tin!!", Alert.AlertType.ERROR).show();
        }
    }
    private void loadDataCBLoaiNV(){
        ObservableList<String> list = FXCollections.observableArrayList("","Quản lý trưởng", "Thủ kho", "Nhân viên");
        this.fLoaiNV.setItems(list);
        this.cbLoaiNV.setItems(list);
        this.cbLoaiNV.getSelectionModel().selectFirst();
        tLoaiNV.setItems(list);
        tLoaiNV.getSelectionModel().select(3);
    }
    private void loadDataCBTT(){
        ObservableList<String> list = FXCollections.observableArrayList("","Đang làm", "Đã nghỉ");
        this.fTrangThai.setItems(list);
        this.cbTrangThai.setItems(list);
        this.cbTrangThai.getSelectionModel().selectFirst();
        this.tTrangThai.setItems(list);
        this.tTrangThai.getSelectionModel().select(1);
    }
    private void loadDataCBGT(){
        ObservableList<String> list = FXCollections.observableArrayList("","Nam", "Nữ","Khác");
        this.cbGT.setItems(list);
        tGioiTinh.setItems(list);
        tGioiTinh.getSelectionModel().select(1);
    }
    private void loaDataCBTK(){
        ObservableList<String> list = FXCollections.observableArrayList("Mã nhân viên", "CMND/CCCD","Số điện thoại");
        this.cbTimKiem.setItems(list);
        this.cbTimKiem.getSelectionModel().selectFirst();
        this.cbTimKiem1.setItems(list);
        this.cbTimKiem1.getSelectionModel().selectFirst();
    }

    public static String moneyFormat(int money){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return(formatter.format(money)+" VNĐ");
    }
    
}
