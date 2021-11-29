/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.ChiTietDonHang;
import com.doannganh.pojo.DonHang;
import com.doannganh.pojo.KhachHang;
import com.doannganh.pojo.NhaCungCap_HangHoa;
import com.doannganh.pojo.User;
import static com.doannganh.qldvvpkcm.NhanvienController.moneyFormat;
import com.doannganh.service.ChiTietDonHangService;
import com.doannganh.service.DonHangService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.KhachHangService;
import com.doannganh.service.NhaCungCapService;
import com.doannganh.service.UserService;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class DonhangController implements Initializable {

    @FXML
    private ListView<String> listCTDH;
    
    @FXML
    private TableView<DonHang> tbDonHang;

    @FXML
    private TableView<NhaCungCap_HangHoa> tbNhapHang;
    
    @FXML
    private TextField fMaDH;

    @FXML
    private TextField fMaNV;

    @FXML
    private TextField fMaKH;
    
    @FXML
    private DatePicker fNgayTaoDH;
    
    @FXML
    private TextField fMaHH;

    @FXML
    private TextField fTenHH;

    @FXML
    private TextField fSoLuong;

    @FXML
    private TextField fGiaNhap;

    @FXML
    private TextField fMaNCC;

    @FXML
    private DatePicker fNgayNhap;

    @FXML
    private TextField fTenNCC;

    @FXML
    private TextField fMaTK;

    @FXML
    private TextField fTenTK;
    
    
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadDonHang("", "");
            loadTableDH();
            loadNhapHang("", "");
            loadTableNH();
            
            this.fMaDH.textProperty().addListener((obj) -> {
                    try {
                        loadDonHang(this.fMaDH.getText(), "Mã đơn hàng");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            this.fMaNV.textProperty().addListener((obj) -> {
                try {
                    loadDonHang(this.fMaNV.getText(), "Mã Nhân viên");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.fMaKH.textProperty().addListener((obj) -> {
                try {
                    loadDonHang(this.fMaKH.getText(), "Mã Khách hàng");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            this.fNgayTaoDH.valueProperty().addListener(obj -> {
                try {
                    loadDonHang(this.fNgayTaoDH.getValue().format(dateFormatter), "Ngày mua hàng");
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch(NullPointerException n){
                try {
                    loadDonHang("", "");
                } catch (SQLException ex) {
                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            });
            this.fMaHH.textProperty().addListener((obj) -> {
                    try {
                        loadNhapHang(this.fMaHH.getText(), "Mã hàng hóa");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
//            this.fTenHH.textProperty().addListener((obj) -> {
//                try {
//                    loadNhapHang(this.fTenHH.getText(), "Tên hàng hóa");
//                } catch (SQLException ex) {
//                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            this.fSoLuong.textProperty().addListener((obj) -> {
//                try {
//                    loadNhapHang(this.fSoLuong.getText(), "Số lượng");
//                } catch (SQLException ex) {
//                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            
//            this.fNgayNhap.valueProperty().addListener(obj -> {
//                try {
//                    loadNhapHang(this.fNgayNhap.getValue().format(dateFormatter), "Ngày nhập");
//                } catch (SQLException ex) {
//                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                catch(NullPointerException n){
//                try {
//                    loadNhapHang("", "");
//                } catch (SQLException ex) {
//                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                }
//            });
//            this.fMaNCC.textProperty().addListener((obj) -> {
//                try {
//                    loadNhapHang(this.fMaNCC.getText(), "Mã nhà cung cấp");
//                } catch (SQLException ex) {
//                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            this.fTenNCC.textProperty().addListener((obj) -> {
//                try {
//                    loadNhapHang(this.fTenNCC.getText(), "Tên công ty");
//                } catch (SQLException ex) {
//                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            this.fMaTK.textProperty().addListener((obj) -> {
//                try {
//                    loadNhapHang(this.fMaTK.getText(), "Mã nhân viên");
//                } catch (SQLException ex) {
//                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            this.fTenTK.textProperty().addListener((obj) -> {
//                try {
//                    loadNhapHang(this.fTenTK.getText(), "Tên nhân viên");
//                } catch (SQLException ex) {
//                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
        } catch (SQLException ex) {
            Logger.getLogger(DonhangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
     public void loadTableDH(){
        
        TableColumn<DonHang, Integer> colMaDonHang = new TableColumn("Mã Đơn Hàng");
        colMaDonHang.setCellValueFactory(new PropertyValueFactory("donhang_id"));

        TableColumn<DonHang, String> colNgayTaoDon = new TableColumn("Ngày Tạo Đơn");
        colNgayTaoDon.setCellValueFactory(new PropertyValueFactory("ngaytaodh"));
        colNgayTaoDon.setStyle("-fx-alignment: CENTER-RIGHT;");
        colNgayTaoDon.setPrefWidth(100);
        
        TableColumn<DonHang, Integer> colMaNhanVien = new TableColumn("Mã Nhân Viên");
        colMaNhanVien.setCellValueFactory(new PropertyValueFactory("nhanvien_id"));
        
        TableColumn<DonHang, Integer> colMaKhachHang = new TableColumn("Mã Khách Hàng");
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory("khachhang_id"));
        
        TableColumn<DonHang, String> colTongTien = new TableColumn("Tổng Tiền");
        colTongTien.setCellValueFactory(new PropertyValueFactory("tongTien"));
        colTongTien.setStyle("-fx-alignment: CENTER-RIGHT;");

        
        tbDonHang.getSelectionModel().selectedItemProperty().addListener(cell->{
            DonHang dh = tbDonHang.getSelectionModel().getSelectedItem();
            if(dh != null){
                try {
                    listCTDH.getItems().clear();
                    Connection conn = JdbcUtils.getConn();
                    UserService us = new UserService(conn);
                    DonHangService dhs = new DonHangService(conn);
                    ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
                    KhachHangService khs = new KhachHangService(conn);
                    
                    
                    
                    listCTDH.getItems().add("Mã đơn hàng: "+String.valueOf(dh.getDonhang_id())
                            + "\t\t\t\t\t\t\t\t\t\t\tNgày tạo đơn: "+ dh.getNgaytaodh());
                    listCTDH.getItems().add("Chi tiết đơn hàng: ");
                    listCTDH.getItems().add("Mã hàng hóa \t\t\t\t Tên hàng hóa \t\t\t\t Số lượng \t\t\t Giá \t\t\tGiảm giá" );
                    List<ChiTietDonHang> listCtdh = ctdhs.getCTDH(dh.getDonhang_id());
                    
                    listCtdh.forEach(ctdh ->{
                        listCTDH.getItems().add("\t" +ctdh.getHanghoa_id()+"\t\t\t\t" + ctdh.getTenhang().substring(0, 20).toUpperCase()
                                + "\t\t\t"+ ctdh.getSoluong()+"\t\t\t"+ moneyFormat(Integer.parseInt(ctdh.getDongia()))
                                +" \t\t\t" + ctdh.getGiamgia() );
                    });
                    listCTDH.getItems().add("TỔNG TIỀN: \t"+ moneyFormat(ctdhs.tongDHByID(dh.getDonhang_id())));
                    if(dh.getKhachhang_id() != 1){
                        KhachHang kh = khs.getKhachHangByID(dh.getKhachhang_id());
                        listCTDH.getItems().add("Thông tin khách hàng:");
                        listCTDH.getItems().add("\tMã khách hàng: " + kh.getIdKhachHang());
                        listCTDH.getItems().add("\tHọ tên: "+ kh.getHoTen() );
                        listCTDH.getItems().add("\tNgày sinh: " + kh.getNgaySinh());
                        listCTDH.getItems().add("\tGiới tính: " + kh.getGioiTinh());
                        listCTDH.getItems().add("\tSố điện thoại: "+ kh.getSdt());
                        listCTDH.getItems().add("\tĐịa chỉ: " + kh.getDiaChi());
                        listCTDH.getItems().add("\tĐiểm tích lũy: " + kh.getDiemTichLuy());
                    }
                    
                    User u = us.getUserByID(dh.getNhanvien_id());
                    if(u != null){
                        listCTDH.getItems().add("Thông tin nhân viên:");
                        listCTDH.getItems().add("\tMã nhân viên: " + u.getUser_id());
                        listCTDH.getItems().add("\tHọ tên: "+ u.getHoten() );
                        listCTDH.getItems().add("\tNgày sinh: " + u.getNgaysinh());
                        listCTDH.getItems().add("\tGiới tính: " + u.getGioitinh());
                        listCTDH.getItems().add("\tSố điện thoại: "+ u.getSdt());
                        listCTDH.getItems().add("\tĐịa chỉ: " + u.getDiachi());
                        listCTDH.getItems().add("\tNgày vào làm: " + u.getNgayvaolam());
                        listCTDH.getItems().add("\tTài khoản: "+ u.getTaikhoan());
                        listCTDH.getItems().add("\tEmail: " + u.getEmail());
                        if(u.getLoaiuser_id() == 3 )
                            listCTDH.getItems().add("\tChức vụ: " + "Nhân viên");
                        if(u.getLoaiuser_id() == 2 )
                            listCTDH.getItems().add("\tChức vụ: " + "Thủ kho");
                        }
                } catch (SQLException ex) {
                    Logger.getLogger(DonhangController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        
        });
        this.tbDonHang.getColumns().addAll(colMaDonHang, colNgayTaoDon, colTongTien, 
                colMaNhanVien,colMaKhachHang);
    }
     
    public void loadTableNH(){
        
        TableColumn<NhaCungCap_HangHoa, Integer> colMaHangHoa = new TableColumn("Mã Hàng Hóa");
        colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));

        TableColumn<NhaCungCap_HangHoa, String> colTenHH = new TableColumn("Tên Hàng Hóa");
        colTenHH.setCellValueFactory(new PropertyValueFactory("tenHH"));
        colTenHH.setStyle("-fx-alignment: CENTER-LEFT;");
        colTenHH.setPrefWidth(260);
        
        TableColumn<NhaCungCap_HangHoa, Integer> colMaNhanVien = new TableColumn("Mã Nhân Viên");
        colMaNhanVien.setCellValueFactory(new PropertyValueFactory("nhanvien_id"));
        
        TableColumn<NhaCungCap_HangHoa, String> colTenNV = new TableColumn("Họ Tên Nhân Viên");
        colTenNV.setCellValueFactory(new PropertyValueFactory("tenNV"));
        colTenNV.setStyle("-fx-alignment: CENTER-LEFT;");
        colTenNV.setPrefWidth(120);
        
        TableColumn<NhaCungCap_HangHoa, Integer> colMaNhaCC = new TableColumn("Mã Nhà Cung Cấp");
        colMaNhaCC.setCellValueFactory(new PropertyValueFactory("nhacungcap_id"));

        TableColumn<NhaCungCap_HangHoa, String> colTenNCC = new TableColumn("Tên Nhà Cung Cấp");
        colTenNCC.setCellValueFactory(new PropertyValueFactory("tenNCC"));
        colTenNCC.setStyle("-fx-alignment: CENTER-RIGHT;");
        colTenNCC.setPrefWidth(120);
        
        TableColumn<NhaCungCap_HangHoa, String> colNgayNhap = new TableColumn("Ngày Hết Hạn");
        colNgayNhap.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
        colNgayNhap.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<NhaCungCap_HangHoa, String> colNgayHH = new TableColumn("Ngày Nhập");
        colNgayHH.setCellValueFactory(new PropertyValueFactory("ngaynhap"));
        colNgayHH.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<NhaCungCap_HangHoa, Integer> colSoLuong = new TableColumn("Số Lượng Nhập");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soLuong"));
        
        TableColumn<NhaCungCap_HangHoa, String> colGiaNhap = new TableColumn("Giá Nhập");
        colGiaNhap.setCellValueFactory(new PropertyValueFactory("gianhap"));
        colGiaNhap.setStyle("-fx-alignment: CENTER-RIGHT;");

        colGiaNhap.setCellFactory(tc -> new TableCell<NhaCungCap_HangHoa, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    value = moneyFormat(Integer.parseInt(value));
                    setText(value);
                }
            }
        });
        
        
        tbNhapHang.getSelectionModel().selectedItemProperty().addListener(cell->{
            NhaCungCap_HangHoa dh = tbNhapHang.getSelectionModel().getSelectedItem();
            if(dh != null){

        }
        
        });
        
        TableColumn<NhaCungCap_HangHoa, String> colTongTien = new TableColumn("Tổng Tiền");
        colTongTien.setCellValueFactory(new PropertyValueFactory("ghichu"));
        colTongTien.setStyle("-fx-alignment: CENTER-RIGHT;");
        colTenNCC.setPrefWidth(150);
        
        this.tbNhapHang.getColumns().addAll(colMaHangHoa,colTenHH, colSoLuong, colGiaNhap, colTongTien
                , colNgayNhap, colMaNhaCC, colTenNCC, colMaNhanVien ,colTenNV);
    }
    
     
    public void loadDonHang(String tuKhoa, String traCuu) throws SQLException{
        this.tbDonHang.getItems().clear();
        Connection conn = JdbcUtils.getConn();
        DonHangService dhs = new DonHangService(conn);
        ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
        
        List<DonHang> listDH = dhs.getDonHang(tuKhoa, traCuu);
        listDH.forEach(dh->{
            try {
                dh.setTongTien(moneyFormat(ctdhs.tongDHByID(dh.getDonhang_id())));
            } catch (SQLException ex) {
                Logger.getLogger(DonhangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.tbDonHang.setItems(FXCollections.observableList(listDH));
        conn.close();
    }
    
    public void loadNhapHang(String tuKhoa, String traCuu) throws SQLException{
        this.tbNhapHang.getItems().clear();
        Connection conn = JdbcUtils.getConn();
        NhaCungCapService nccs = new NhaCungCapService(conn);
        
        List<NhaCungCap_HangHoa> listNCC = nccs.getNCCHH(tuKhoa, traCuu);
        
        listNCC.forEach(ncc->{
            try {
                ncc.setGhichu(moneyFormat(nccs.tinhChiPhiDH(ncc.getHanghoa_id(), ncc.getNgaynhap(), ncc.getNhacungcap_id())));
            } catch (SQLException ex) {
                Logger.getLogger(DonhangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.tbNhapHang.setItems(FXCollections.observableList(listNCC));
        conn.close();
    }
    
    public static String moneyFormat(int money){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return(formatter.format(money)+" VNĐ");
    }
}
