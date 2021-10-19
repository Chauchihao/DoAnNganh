/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.LoaiHangHoa;
import com.doannganh.pojo.User;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.LoaiHangHoaService;
import com.doannganh.service.NhaCungCapService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TraCuuHangHoaThuKhoController implements Initializable {

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
        if (this.txtTraCuu.getText().isEmpty())
            loadHangHoa("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
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
            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void loadTable(){
        try {
            this.tbHangHoa.setEditable(true);
            Connection conn;
            conn = JdbcUtils.getConn();
            HangHoaService s = new HangHoaService(conn);
            LoaiHangHoaService ss = new LoaiHangHoaService(conn);
            NhaCungCapService nccs = new NhaCungCapService(conn);
            
            TableColumn<HangHoa, Integer> colMaHangHoa = new TableColumn("Mã Hàng");
            colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));
            
            TableColumn<HangHoa, String> colTenHangHoa = new TableColumn("Tên Hàng");
            colTenHangHoa.setCellValueFactory(new PropertyValueFactory("tenhanghoa"));
            
            TableColumn<HangHoa, String> colThuongHieu = new TableColumn("Thương Hiệu");
            colThuongHieu.setCellValueFactory(new PropertyValueFactory("thuonghieu"));
            
            TableColumn<HangHoa, String> colSoLuong = new TableColumn("Số Lượng");
            colSoLuong.setCellValueFactory(new PropertyValueFactory("soluongtrongkho"));
            
            TableColumn<HangHoa, String> colGiaNhap = new TableColumn("Giá Nhập");
            colGiaNhap.setCellValueFactory(new PropertyValueFactory("gianhap"));
            
            TableColumn<HangHoa, String> colGiaNiemYet = new TableColumn("Giá Niêm Yết");
            colGiaNiemYet.setCellValueFactory(new PropertyValueFactory("gianiemyet"));
            
            TableColumn<HangHoa, String> colNgaySanXuat = new TableColumn("Ngày Sản Xuất");
            colNgaySanXuat.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
            
            TableColumn<HangHoa, String> colNgayHetHan = new TableColumn("Ngày Hết Hạn");
            colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
            
            TableColumn<HangHoa, Boolean> colTinhTrang = new TableColumn("Tình Trạng");
            colTinhTrang.setCellValueFactory(new PropertyValueFactory("tinhtrang"));
            
            TableColumn<HangHoa, String> colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
            colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("tenloaihang"));
            
            TableColumn<HangHoa, String> colNhaCungCap = new TableColumn("Nhà Cung Cấp");
            colNhaCungCap.setCellValueFactory(new PropertyValueFactory("nhacungcap"));
            
            colMaHangHoa.setOnEditStart(evt -> {
                Utils.getBox("Không thể sửa mã hàng hóa!", Alert.AlertType.ERROR).show();
            });
            colTenHangHoa.setCellFactory(TextFieldTableCell.forTableColumn());
            colTenHangHoa.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getTenhanghoa();
                    String m = "";
                    if (!"".equals(evt.getNewValue()))
                        m = evt.getNewValue();
                    hh.setTenhanghoa(m);
                    if (m == "") {
                        hh.setTenhanghoa(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else if (m.equals(c)) {
                        Utils.getBox("Vui lòng thay đổi tên hàng hóa để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaTenHH(hh.getHanghoa_id(), hh.getTenhanghoa())) {
                            Utils.getBox("Cập nhật tên hàng hóa thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setTenhanghoa(c);
                            Utils.getBox("Cập nhật tên hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    tbHangHoa.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            ObservableList listTH = FXCollections.observableList(s.getThuongHieu());
            colThuongHieu.setCellFactory(ComboBoxTableCell.<HangHoa, String>forTableColumn(listTH));
            colThuongHieu.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = String.valueOf(evt.getOldValue());
                    String m = String.valueOf(evt.getNewValue());
                    hh.setThuonghieu(m);
                    if (m.equals(c)) {
                        hh.setThuonghieu(c);
                        Utils.getBox("Vui lòng thay đổi thương hiệu để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaThuongHieu(hh.getHanghoa_id(), hh.getThuonghieu())) {
                            Utils.getBox("Cập nhật thương hiệu thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setThuonghieu(c);
                            Utils.getBox("Cập nhật thương hiệu thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    tbHangHoa.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            colSoLuong.setCellFactory(TextFieldTableCell.forTableColumn());
            colSoLuong.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getSoluongtrongkho();
                    String m = "";
                    if ("".equals(evt.getNewValue())) {
                        hh.setSoluongtrongkho(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else {
                        m = evt.getNewValue();
                        hh.setSoluongtrongkho(m);
                        if (!m.matches("\\d+")) {
                            hh.setSoluongtrongkho(c);
                            Utils.getBox("Vui lòng chỉ nhập số!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            hh.setSoluongtrongkho(c);
                            Utils.getBox("Vui lòng thay đổi số lượng để cập nhật!", Alert.AlertType.WARNING).show();
                        } else {
                            if (m.length() > 4) {
                                hh.setSoluongtrongkho(c);
                                Utils.getBox("Vui lòng nhập số lượng <= 1.000", Alert.AlertType.WARNING).show();
                            } else if (Integer.parseInt(m) < 0) {
                                hh.setSoluongtrongkho(c);
                                Utils.getBox("Vui lòng nhập số lượng >= 0", Alert.AlertType.WARNING).show();
                            } else {
                                if (s.suaSoLuong(hh.getHanghoa_id(), hh.getSoluongtrongkho())) {
                                    Utils.getBox("Cập nhật số lượng thành công!", Alert.AlertType.INFORMATION).show();
                                } else {
                                    hh.setSoluongtrongkho(c);
                                    Utils.getBox("Cập nhật số lượng thất bại!!!", Alert.AlertType.ERROR).show();
                                }
                            }
                        }
                    }
                    tbHangHoa.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            colGiaNhap.setCellFactory(TextFieldTableCell.forTableColumn());
            colGiaNhap.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getGianhap();
                    String m;
                    if ("".equals(evt.getNewValue())) {
                        hh.setGianhap(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else {
                        m = evt.getNewValue();
                        hh.setGianhap(m);
                        if (m.matches("\\d+") == false) {
                            hh.setGianhap(c);
                            Utils.getBox("Vui lòng nhập số!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            hh.setGianhap(c);
                            Utils.getBox("Vui lòng thay đổi giá nhập để cập nhật!", Alert.AlertType.WARNING).show();
                        } else {
                            if (m.length() > 9) {
                                hh.setGianhap(c);
                                Utils.getBox("Vui lòng nhập giá nhập < 1.000.000.000!", Alert.AlertType.WARNING).show();
                            } else if (Integer.parseInt(m) < 10000) {
                                hh.setGianhap(c);
                                Utils.getBox("Vui lòng nhập giá nhập >= 10.000", Alert.AlertType.WARNING).show();
                            } else if (Integer.parseInt(m) >= Integer.parseInt(hh.getGianiemyet())) {
                                hh.setGianhap(c);
                                Utils.getBox("Vui lòng nhập giá nhập < giá niêm yết: " + hh.getGianiemyet(), Alert.AlertType.WARNING).show();
                            } else {
                                if (s.suaGiaNhap(hh.getHanghoa_id(), hh.getGianhap(), nccs.getNCCByTen(hh.getNhacungcap()))) {
                                    
                                    Utils.getBox("Cập nhật giá nhập thành công!", Alert.AlertType.INFORMATION).show();
                                } else {
                                    hh.setGianhap(c);
                                    Utils.getBox("Cập nhật giá nhập thất bại!!!", Alert.AlertType.ERROR).show();
                                }
                            }
                        }
                        
                    }
                    tbHangHoa.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            colGiaNiemYet.setOnEditStart(evt -> {
                Utils.getBox("Không có quyền sửa giá niêm yết!", Alert.AlertType.ERROR).show();
            });
            colNgaySanXuat.setCellFactory(TextFieldTableCell.forTableColumn());
            colNgaySanXuat.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getNgaysanxuat();
                    String m = "";
                    if (!"".equals(evt.getNewValue()))
                        m = evt.getNewValue();
                    hh.setNgaysanxuat(m);
                    if (m == "") {
                        hh.setNgaysanxuat(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else if (m.equals(c)) {
                        Utils.getBox("Vui lòng thay đổi ngày sản xuất để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaNgaySX(hh.getHanghoa_id(), hh.getNgaysanxuat())) {
                            Utils.getBox("Cập nhật ngày sản xuất thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setNgaysanxuat(c);
                            Utils.getBox("Cập nhật ngày sản xuất thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    tbHangHoa.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            colNgayHetHan.setCellFactory(TextFieldTableCell.forTableColumn());
            colNgayHetHan.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getNgayhethan();
                    String m = "";
                    if (!"".equals(evt.getNewValue()))
                        m = evt.getNewValue();
                    hh.setNgayhethan(m);
                    if (m == "") {
                        hh.setNgayhethan(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else if (m.equals(c)) {
                        Utils.getBox("Vui lòng thay đổi ngày hết hạn để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaNgayHH(hh.getHanghoa_id(), hh.getNgayhethan())) {
                            Utils.getBox("Cập nhật ngày hết hạn thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setNgayhethan(c);
                            Utils.getBox("Cập nhật ngày hết hạn thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    tbHangHoa.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            ObservableList listLHH = FXCollections.observableList(ss.getLoaiHH());
            colLoaiHangHoa.setCellFactory(ComboBoxTableCell.<HangHoa, String>forTableColumn(listLHH));
            colLoaiHangHoa.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = String.valueOf(evt.getOldValue());
                    String m = String.valueOf(evt.getNewValue());
                    hh.setTenloaihang(m);
                    if (m.equals(c)) {
                        hh.setTenloaihang(c);
                        Utils.getBox("Vui lòng chọn loại hàng hóa để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaLoaiHH(hh.getHanghoa_id(), ss.getLoaiHHByTen(hh.getTenloaihang()))) {
                            Utils.getBox("Cập nhật loại hàng hóa thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setTenloaihang(c);
                            Utils.getBox("Cập nhật loại hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    tbHangHoa.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            ObservableList listNCC = FXCollections.observableList(nccs.getNCC());
            colNhaCungCap.setCellFactory(ComboBoxTableCell.<HangHoa, String>forTableColumn(listNCC));
            colNhaCungCap.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = String.valueOf(evt.getOldValue());
                    String m = String.valueOf(evt.getNewValue());
                    hh.setNhacungcap(m);
                    if (m.equals(c)) {
                        hh.setNhacungcap(c);
                        Utils.getBox("Vui lòng chọn loại hàng hóa để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaNhaCC(hh.getHanghoa_id(), nccs.getNCCByTen(hh.getNhacungcap()))) {
                            Utils.getBox("Cập nhật loại hàng hóa thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setNhacungcap(c);
                            Utils.getBox("Cập nhật loại hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    tbHangHoa.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            TableColumn colAction = new TableColumn();
            colAction.setCellFactory((obj) -> {
                Button btn = new Button("Xóa");
                
                btn.setOnAction(evt -> {
                    Utils.getBox("Bạn có xác nhận xóa hàng hóa không?", Alert.AlertType.CONFIRMATION)
                         .showAndWait().ifPresent(bt -> {
                            if (bt == ButtonType.OK) {
                                TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                                HangHoa hh = (HangHoa) cell.getTableRow().getItem();

                                if ("0".equals(hh.getSoluongtrongkho())) {
                                    try {
                                        if (s.deleteHH(hh.getHanghoa_id())){
                                            loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
                                            Utils.getBox("Đã xóa hàng hóa thành công", Alert.AlertType.INFORMATION).show();
                                        } else
                                            Utils.getBox("Đã xóa hàng hóa thất bại", Alert.AlertType.ERROR).show();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else
                                    Utils.getBox("Chưa thể xóa hàng hóa khi số lượng trong kho > 0", Alert.AlertType.WARNING).show();
                                
                            }
                         });
                });
                TableCell cell = new TableCell();
                cell.setGraphic(btn);
                return cell;
            });
            this.tbHangHoa.getColumns().addAll(colMaHangHoa, colTenHangHoa
                    , colThuongHieu, colSoLuong, colGiaNhap, colGiaNiemYet
                    , colNgaySanXuat, colNgayHetHan, colTinhTrang
                    , colLoaiHangHoa, colNhaCungCap);
        } catch (SQLException ex) {
            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
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
