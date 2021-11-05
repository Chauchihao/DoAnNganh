/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.ChiTietDonHang;
import com.doannganh.pojo.DonHang;
import com.doannganh.pojo.User;
import com.doannganh.service.ChiTietDonHangService;
import com.doannganh.service.DonHangService;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.UserService;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class DoiTraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TableView<DonHang> tbDH;
    @FXML private TableView<ChiTietDonHang> tbCTDH;
    @FXML private TextField txtTraCuu;
    @FXML private ComboBox<String> cbTraCuu;
    
    User nd = DangnhapController.nd;
    ObservableList<String> list = FXCollections.observableArrayList
        ("Mã đơn hàng", "Ngày mua hàng", "Nhân viên", "Khách hàng");
    int index = -1;
    boolean co = true;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectFirst();
        loadTableDH();
        loadDonHang("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        loadTableCTDH();
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            if (this.txtTraCuu.getText().isEmpty()) {
                loadDonHang("", this.cbTraCuu.getSelectionModel().getSelectedItem());
            }
            else
                loadDonHang(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
            this.tbCTDH.getItems().clear();
        });
        
        this.tbDH.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked((var e) -> {
                DonHang dh = this.tbDH.getSelectionModel().getSelectedItem();
                /*this.tbDH.editingCellProperty().addListener(ev -> {
                    loadCTDH(dh.getNhacungcap(), "Tên công ty");
                });*/
                loadCTDH(dh.getDonhang_id());
            });
            return r;
        });
    }    
    
    public void loadDonHang(String tuKhoa, String traCuu){
        try {
            this.tbDH.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            DonHangService dhs = new DonHangService(conn);
            this.tbDH.setItems(FXCollections.observableList(
                    dhs.getDonHang(tuKhoa, traCuu)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void loadTableDH(){
            
        TableColumn<DonHang, Integer> colMaDonHang = new TableColumn("Mã Đơn Hàng");
        colMaDonHang.setCellValueFactory(new PropertyValueFactory<>("donhang_id"));

        TableColumn<DonHang, String> colNgayTaoDH = new TableColumn("Ngày Mua Hàng");
        colNgayTaoDH.setCellValueFactory(new PropertyValueFactory("ngaytaodh"));

        TableColumn<DonHang, String> colNhanVien = new TableColumn("Nhân Viên");
        colNhanVien.setCellValueFactory(new PropertyValueFactory("hoTenNV"));

        TableColumn<DonHang, String> colKhachHang = new TableColumn("Khách Hàng");
        colKhachHang.setCellValueFactory(new PropertyValueFactory("hoTenKH"));

        TableColumn<DonHang, Integer> colTongCTDH = new TableColumn("Tổng Hàng Hóa");
        colTongCTDH.setCellValueFactory(new PropertyValueFactory("tongCTDH"));
        colTongCTDH.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<DonHang, String> colTongTien = new TableColumn("Tổng Tiền");
        colTongTien.setCellValueFactory(new PropertyValueFactory("tongTien"));
        colTongTien.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn colInHD = new TableColumn();
        colInHD.setCellFactory((obj) -> {
            Button btn = new Button("In Hóa Đơn");
            btn.setOnAction(evt -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    DonHangService dhs = new DonHangService(conn);
                    TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                    DonHang dh = (DonHang) cell.getTableRow().getItem();
                    dh.setNhanvien_id(dhs.getIDNVByIDDH(dh.getDonhang_id()));
                    index = cell.getTableRow().getIndex();
                    if (!cell.isEmpty()) {
                        inHoaDon(dh);
                    }
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

        colTongTien.setCellFactory(tc -> new TableCell<DonHang, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    BigDecimal tien = new BigDecimal(value);
                    setText(Utils.moneyBigDecimalFormat(tien));
                }
            }
        });

        this.tbDH.getColumns().addAll(colMaDonHang, colNgayTaoDH
                , colNhanVien, colKhachHang, colTongCTDH, colTongTien, colInHD);
        
    }
    
    public void loadCTDH(int id){
        try {
            this.tbCTDH.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
            this.tbCTDH.setItems(FXCollections.observableList(
                    ctdhs.getCTDH(id)));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void loadTableCTDH(){
        TableColumn<ChiTietDonHang, Integer> colMaHangHoa = new TableColumn("Mã Hàng Hóa");
        colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));

        TableColumn<ChiTietDonHang, String> colTenHang = new TableColumn("Tên Hàng Hóa");
        colTenHang.setCellValueFactory(new PropertyValueFactory("tenhang"));

        TableColumn<ChiTietDonHang, String> colSoLuong = new TableColumn("Số Lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soluong"));
        colSoLuong.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<ChiTietDonHang, String> colDonGia = new TableColumn("Đơn Giá");
        colDonGia.setCellValueFactory(new PropertyValueFactory("dongia"));
        colDonGia.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<ChiTietDonHang, Integer> colGiamGia = new TableColumn("Giảm Giá");
        colGiamGia.setCellValueFactory(new PropertyValueFactory("giamgia"));
        colGiamGia.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<ChiTietDonHang, String> colThanhTien = new TableColumn("Thành Tiền");
        colThanhTien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
        colThanhTien.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn colDoi = new TableColumn();
        colDoi.setCellFactory((obj) -> {
            Button btn = new Button("Đổi");
            btn.setOnAction(evt -> {
                TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                ChiTietDonHang ctdh = (ChiTietDonHang) cell.getTableRow().getItem();
                index = cell.getTableRow().getIndex();

            });
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

        TableColumn colTra = new TableColumn();
        colTra.setCellFactory((obj) -> {
            Button btn = new Button("Trả");
            btn.setOnAction(evt -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    HangHoaService hhs = new HangHoaService(conn);
                    DonHangService dhs = new DonHangService(conn);
                    ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
                    TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                    ChiTietDonHang ctdh = (ChiTietDonHang) cell.getTableRow().getItem();
                    DonHang dh = this.tbDH.getSelectionModel().getSelectedItem();
                    index = cell.getTableRow().getIndex();
                    if (!cell.isEmpty()) {
                        ctdh.setDonhang_id(dh.getDonhang_id());
                        ctdh.setHanghoa_id(hhs.getIDByTenHang(ctdh.getTenhang()));
                            int sl = Integer.parseInt(hhs.getSoLuongByIDHH(ctdh.getHanghoa_id())) + Integer.parseInt(ctdh.getSoluong());
                            hhs.suaSoLuong(ctdh.getHanghoa_id(), String.valueOf(sl));
                            ctdhs.xoaCTDH(ctdh);

                            loadDonHang(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
                            this.tbDH.getSelectionModel().select(index);
                            loadCTDH(dh.getDonhang_id());
                            Utils.getBox(ctdh.getTenhang() + " đã được trả thành công!", Alert.AlertType.INFORMATION).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

        colDonGia.setCellFactory(tc -> new TableCell<ChiTietDonHang, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    BigDecimal dg = new BigDecimal(value);
                    setText(Utils.moneyBigDecimalFormat(dg));
                }
            }
        });

        colThanhTien.setCellFactory(tc -> new TableCell<ChiTietDonHang, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    BigDecimal tien = new BigDecimal(value);
                    setText(Utils.moneyBigDecimalFormat(tien));
                }
            }
        });

        this.tbCTDH.getColumns().addAll(colMaHangHoa, colTenHang, colSoLuong
                , colDonGia, colGiamGia, colThanhTien, colDoi, colTra);
        
    }
    
    public void inHoaDon(DonHang dh){
        try {
            Connection conn = JdbcUtils.getConn();
            UserService s = new UserService(conn);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/qldvvpkcm", "root", "12345678");
            String reportPath = "/report/HoaDon.jrxml";
            Map<String, Object> params = new HashMap<>();  
            params.put("donhang_id", dh.getDonhang_id());  
            params.put("ngayTaoDH", dh.getNgaytaodh());
            params.put("hoten", s.getUserByID(dh.getNhanvien_id()).getHoten());
            JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(reportPath));
            JasperPrint jp = JasperFillManager.fillReport(jr, params, con);
            JasperViewer jv = new JasperViewer(jp, false);
            //asperViewer.viewReport(jp, false);
            jv.setTitle("Hóa Đơn");
            jv.setZoomRatio(new Float(0.75));
            jv.setVisible(true);
            con.close();
        } catch(ClassNotFoundException | SQLException | JRException ex) {
            ex.printStackTrace();
        }

    }
}
