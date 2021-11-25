/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;


import com.doannganh.pojo.KhachHang;
import static com.doannganh.qldvvpkcm.QuanLyKhachHangController.getDayCellFactory;
import com.doannganh.service.DonHangService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.KhachHangService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class KhachhangController implements Initializable {

    @FXML
    private TableView<KhachHang> tableKH;

    @FXML
    private PieChart pieChart;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTable();
        loadKhachHang("", "");
    }    
    

    public int tongDH() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        DonHangService dhs = new DonHangService(conn);
        
        return dhs.tongDH();
    }
    
    public void loadTable(){

        TableColumn<KhachHang, Integer> colMaKhachHang = new TableColumn("Mã Khách Hàng");
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory("idKhachHang"));

        TableColumn<KhachHang, String> colTenKhachHang = new TableColumn("Họ tên");
        colTenKhachHang.setCellValueFactory(new PropertyValueFactory("hoTen"));

        TableColumn<KhachHang, String> colNgaySinh = new TableColumn("Ngày Sinh");
        colNgaySinh.setCellValueFactory(new PropertyValueFactory("ngaySinh"));

        TableColumn<KhachHang, String> colGioiTinh = new TableColumn("Giới Tính");
        colGioiTinh.setCellValueFactory(new PropertyValueFactory("gioiTinh"));

        TableColumn<KhachHang, String> colDiaChi = new TableColumn("Địa Chỉ");
        colDiaChi.setCellValueFactory(new PropertyValueFactory("diaChi"));

        TableColumn<KhachHang, String> colSdt = new TableColumn("Số Điện Thoại");
        colSdt.setCellValueFactory(new PropertyValueFactory("sdt"));
        colSdt.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<KhachHang, String> colDiemTichLuy = new TableColumn("Điểm Tích Lũy");
        colDiemTichLuy.setCellValueFactory(new PropertyValueFactory("diemTichLuy"));
        colDiemTichLuy.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        colMaKhachHang.setOnEditStart(evt -> {
            Utils.getBox("Không thể sửa mã khách hàng!!!", Alert.AlertType.ERROR).show();
        });
        
        colTenKhachHang.setCellFactory(TextFieldTableCell.forTableColumn());
        colTenKhachHang.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                KhachHangService khs = new KhachHangService(conn);
                KhachHang kh = evt.getRowValue();
                String c = kh.getHoTen();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                kh.setHoTen(m);
                if (m == "") {
                    kh.setHoTen(c);
                    Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                } else if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi họ tên để cập nhật!", Alert.AlertType.WARNING).show();
                } else {
                    if (khs.suaHoTen(kh.getIdKhachHang(), kh.getHoTen())) {
                        Utils.getBox("Cập nhật họ tên thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        kh.setHoTen(c);
                        Utils.getBox("Cập nhật họ tên thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tableKH.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        colNgaySinh.setCellFactory((TableColumn<KhachHang, String> param) -> {
            DatePicker dp = new DatePicker();
//            dp.setEditable(false);
            TableCell<KhachHang, String> cell = new TableCell<KhachHang, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty) {
                        setText("");
                        setGraphic(null);
                    }
                    else {
//                        setEditable(false);
                        
                        dp.setValue(LocalDate.parse(item));
                        dp.getEditor().setText(item);
//                        Callback<DatePicker, DateCell> dayCellFactory = getDayCellFactory();
//                        dp.setDayCellFactory(dayCellFactory);
                        
                        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            @Override
                            public String toString(LocalDate date) {
                                if (date != null) {
                                    return dateFormatter.format(date);
                                } else {
                                    return "";
                                }
                            }
                            @Override
                            public LocalDate fromString(String string) {
                                if (string != null && !string.isEmpty()) {
                                    return LocalDate.parse(string, dateFormatter);
                                } else {
                                    return null;
                                }
                            }
                        };
                        dp.setConverter(converter);
                        dp.setPromptText("yyyy-MM-dd");
                        
                        setText(item);
                    }
                }
            };
            
            cell.setOnMouseClicked((MouseEvent event) -> {
                KhachHang kh = tableKH.getSelectionModel().getSelectedItem();
                if (kh == null)
                    cell.setGraphic(null);
                else {
//                    if(event.getButton().equals(MouseButton.PRIMARY)) {
//                        cell.setEditable(true);
//                        dp.setEditable(false);
//                    }
//
//                    if( event.getClickCount()==2 && cell.isEditable() ) {
//                        cell.setText(null);
                        cell.setGraphic(dp);
                    }
            });
//            dp.setOnAction((aevt)->{
//                try {
//                    Connection conn = JdbcUtils.getConn();
//                    KhachHangService khs = new KhachHangService(conn);
//                    KhachHang kh = (KhachHang) tableKH.getSelectionModel().getSelectedItem();
//                    String ngay = dp.getValue().format(dateFormatter);
//                    if(kh != null) {
//                        if (ngay != kh.getNgaySinh()) {
//                            if (khs.suaNgaySinh(tableKH.getItems().get(cell.getIndex()).getIdKhachHang(), ngay)) {
//                                kh.setNgaySinh(ngay);
//                                Utils.getBox("Cập nhật ngày sinh thành công!", Alert.AlertType.INFORMATION).show();
//                            }
//                        } else
//                            Utils.getBox("Vui lòng thay đổi để cập nhật!!!", Alert.AlertType.WARNING).show();
//                    }
//                } catch (SQLException ex) {
//                    Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
//                }

//            });
            cell.setOnKeyPressed((KeyEvent event) -> {
                if(event.getCode().equals(KeyCode.ENTER))
                {
                    try {
                        Connection conn = JdbcUtils.getConn();
                        KhachHangService khs = new KhachHangService(conn);
                        KhachHang kh = (KhachHang) tableKH.getSelectionModel().getSelectedItem();
                        String ngay = dp.getEditor().getText();
                        if(kh != null) {
                            if (ngay != kh.getNgaySinh()) {
                                if (khs.suaNgaySinh(tableKH.getItems().get(cell.getIndex()).getIdKhachHang(), ngay)) {
                                    kh.setNgaySinh(ngay);
                                    Utils.getBox("Cập nhật ngày sinh thành công!", Alert.AlertType.INFORMATION).show();
                                }
                            } else
                                Utils.getBox("Vui lòng thay đổi để cập nhật!!!", Alert.AlertType.WARNING).show();
                            cell.setEditable(false);
                            tableKH.refresh();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
            
        });
            return cell;
    });
        
        colGioiTinh.setCellFactory((TableColumn<KhachHang, String> p) -> {
            ComboBoxTableCell<KhachHang, String> cell = new ComboBoxTableCell<>("Nam", "Nữ");
            return cell;
        });
        colGioiTinh.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                KhachHangService tcs = new KhachHangService(conn);
                KhachHang tc = evt.getRowValue();
                String c = tc.getGioiTinh();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                tc.setGioiTinh(m);
                if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi giới tính để cập nhật!", Alert.AlertType.WARNING).show();
                } else {
                    if (tcs.suaGioiTinh(tc.getIdKhachHang(), tc.getGioiTinh())) {
                        Utils.getBox("Cập nhật giới tính thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        tc.setGioiTinh(c);
                        Utils.getBox("Cập nhật giới tính thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tableKH.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        colDiaChi.setCellFactory(TextFieldTableCell.forTableColumn());
        colDiaChi.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                KhachHangService khs = new KhachHangService(conn);
                KhachHang kh = evt.getRowValue();
                String c = kh.getDiaChi();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                kh.setDiaChi(m);
                if (m == "") {
                    kh.setDiaChi(c);
                    Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                } else if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi địa chỉ để cập nhật!!!", Alert.AlertType.WARNING).show();
                } else {
                    if (khs.suaDiaChi(kh.getIdKhachHang(), kh.getDiaChi())) {
                        Utils.getBox("Cập nhật địa chỉ thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        kh.setDiaChi(c);
                        Utils.getBox("Cập nhật địa chỉthất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tableKH.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        colSdt.setCellFactory(TextFieldTableCell.forTableColumn());
        colSdt.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                KhachHangService khs = new KhachHangService(conn);
                KhachHang kh = evt.getRowValue();
                String c = kh.getSdt();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                kh.setSdt(m);
                if (m == "") {
                    kh.setSdt(c);
                    Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                } else if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi số điện thoại để cập nhật!!!", Alert.AlertType.WARNING).show();
                } else if (!m.matches("\\d+")) {
                    kh.setSdt(c);
                    Utils.getBox("Vui lòng chỉ nhập số!!!", Alert.AlertType.WARNING).show();
                } else if (m.length() != 10) {
                    kh.setSdt(c);
                    Utils.getBox("Vui lòng nhập 10 số!!!", Alert.AlertType.WARNING).show();
                } else{
                    if (khs.suaSDT(kh.getIdKhachHang(), kh.getSdt())) {
                        Utils.getBox("Cập nhật số điện thoại thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        kh.setSdt(c);
                        Utils.getBox("Cập nhật số điện thoại thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tableKH.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        });
        
        colDiemTichLuy.setOnEditStart(evt -> {
            Utils.getBox("Không thể sửa điểm tích lũy!!!", Alert.AlertType.ERROR).show();
        });
        
        this.tableKH.getColumns().addAll(colMaKhachHang, colTenKhachHang
                , colNgaySinh, colGioiTinh, colDiaChi
                , colSdt, colDiemTichLuy);
    }
    
    public void loadKhachHang(String tuKhoa, String traCuu){
        try {
            this.tableKH.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            KhachHangService khs = new KhachHangService(conn);
            this.tableKH.setItems(FXCollections.observableList(
                    khs.getKhachHang(tuKhoa, traCuu)));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
//    public void themKH(ActionEvent evt) throws IOException {
//        if (this.txtHoTen.getText().isEmpty())
//            Utils.getBox("Vui lòng nhập họ tên!!!", Alert.AlertType.WARNING).show();
//        else if (this.dpNgaySinh.getEditor().getText().isEmpty())
//            Utils.getBox("Vui lòng chọn ngày sinh!!!", Alert.AlertType.WARNING).show();
//        else if (this.txtDiaChi.getText().isEmpty())
//            Utils.getBox("Vui lòng nhập địa chỉ!!!", Alert.AlertType.WARNING).show();
//        else if (this.txtSDT.getText().isEmpty())
//            Utils.getBox("Vui lòng nhập số điện thoại!!!", Alert.AlertType.WARNING).show();
//        else if (!this.txtSDT.getText().matches("\\d+"))
//            Utils.getBox("Vui lòng chỉ nhập số!!!", Alert.AlertType.WARNING).show();
//        else if (this.txtSDT.getText().length() != 10)
//            Utils.getBox("Vui lòng nhập 10 số!!!", Alert.AlertType.WARNING).show();
//        else {
//            try {
//                Connection conn = JdbcUtils.getConn();
//                KhachHangService khs = new KhachHangService(conn);
//                KhachHang kh = new KhachHang();
//                kh.setHoTen(this.txtHoTen.getText());
//                kh.setNgaySinh(this.dpNgaySinh.getEditor().getText());
//                kh.setGioiTinh(this.cbGioiTinh.getSelectionModel().getSelectedItem());
//                kh.setDiaChi(this.txtDiaChi.getText());
//                kh.setSdt(this.txtSDT.getText());
//                kh.setDiemTichLuy("0");
//                if(khs.themKH(kh)) {
//                    Utils.getBox("Thêm khách hàng thành công!",Alert.AlertType.INFORMATION).show();
//                    loadKhachHang(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
//                    listMaKH.clear();
//                    listMaKH = FXCollections.observableList(khs.getIDKhachHang());
//                } else
//                    Utils.getBox("Thêm khách hàng thất bại!",Alert.AlertType.ERROR).show();
//                this.tbKhachHang.refresh();
//            } catch (SQLException ex) {
//                Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
    public int slKHT() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        KhachHangService khs = new KhachHangService(conn);
        
        return khs.slKHT();
    }

    public int slKHTT() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        KhachHangService khs = new KhachHangService(conn);
        
        return khs.slKHTT();
    }
}
