/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.LoaiHangHoa;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.LoaiHangHoaService;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class HanghoaController implements Initializable {

    @FXML
    private TextField txtMaHH;

    @FXML
    private TextField txtTenHH;

    @FXML
    private TextField txtLoaiHH;

    @FXML
    private TextField txtSoLuong;

    @FXML
    private TextField txtGiaNhap;

    @FXML
    private TextField txtGiaNiemYet;
    
    @FXML
    private TextField txtTenLoaiHH;
    
    @FXML
    private DatePicker NgaySX;

    @FXML
    private DatePicker NgayHH;

    @FXML
    private ComboBox<String> cbTinhTrang;

    @FXML
    private TextField txtThuongHieu;

    @FXML
    private TextField txtNSX;

    @FXML
    private TableView<HangHoa> tbHH;
    
    @FXML
    private TableView<LoaiHangHoa> tbLoaiHH;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ObservableList<String> listLoaiHH = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            listLoaiHH = getListLoaiHH();
            loadTableLoaiHH();
            loadLoaiHHQLT();
            try {
                loadTableHH();
                loadHHQLT("","");
                loadCBData();
                
                this.txtMaHH.textProperty().addListener((obj) -> {
                    try {
                        loadHHQLT(this.txtMaHH.getText(), "Mã");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                this.txtTenHH.textProperty().addListener((obj) -> {
                    try {
                        loadHHQLT(this.txtTenHH.getText(), "Tên");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                this.txtLoaiHH.textProperty().addListener((obj) -> {
                    try {
                        loadHHQLT(this.txtLoaiHH.getText(), "Loại");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                this.txtSoLuong.textProperty().addListener((obj) -> {
                    try {
                        loadHHQLT(this.txtSoLuong.getText(), "Số lượng");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                this.txtGiaNhap.textProperty().addListener((obj) -> {
                    try {
                        loadHHQLT(this.txtGiaNhap.getText(), "Giá nhập");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                this.txtGiaNiemYet.textProperty().addListener((obj) -> {
                    try {
                        loadHHQLT(this.txtGiaNiemYet.getText(), "Giá niêm yết");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                this.NgaySX.valueProperty().addListener(obj -> {
                    try {
                        loadHHQLT(this.NgaySX.getValue().format(dateFormatter), "Ngày sx");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch(NullPointerException n){
                    try {
                        loadHHQLT("", "");
                    } catch (SQLException ex) {
                        Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                });
                this.NgayHH.valueProperty().addListener(obj -> {
                    try {
                        loadHHQLT(this.NgayHH.getValue().format(dateFormatter), "Ngày hh");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch(NullPointerException n){
                    try {
                        loadHHQLT("", "");
                    } catch (SQLException ex) {
                        Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                });
                this.cbTinhTrang.valueProperty().addListener(obj -> {
                    try {
                        String key = "";
                        if(this.cbTinhTrang.getValue().equals("Đang bán")){
                            key = "1";
                        }
                        else if(this.cbTinhTrang.getValue().equals("Ngừng bán"))
                            key = "0";
                        loadHHQLT(key, "Tình trạng");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                this.txtThuongHieu.textProperty().addListener((obj) -> {
                    try {
                        loadHHQLT(this.txtThuongHieu.getText(), "Thương hiệu");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                this.txtNSX.textProperty().addListener((obj) -> {
                    try {
                        loadHHQLT(this.txtNSX.getText(), "Nhà cung cấp");
                    } catch (SQLException ex) {
                        Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch(NullPointerException n){
                    try {
                        loadHHQLT("", "");
                    } catch (SQLException ex) {
                        Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                });
            } catch (SQLException ex) {
                Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            TableRow r = new TableRow();
            r.setOnMouseClicked(evt -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    HangHoaService s = new HangHoaService(conn);
                    HangHoa hh = this.tbHH.getSelectionModel().getSelectedItem();
                    int rindex = this.tbHH.getSelectionModel().getSelectedIndex();
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
                                    this.tbHH.getItems().set(rindex, hh);
                                } else
                                    Utils.getBox("Cập nhật giá bán thất bại!!!", Alert.AlertType.ERROR).show();
                            }
                        }
                        
                    }
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
        } catch (SQLException ex) {
            Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadTableHH() throws SQLException{
        TableColumn<HangHoa, Integer> colMaHangHoa = new TableColumn("Mã Hàng");
        colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));
        
        TableColumn<HangHoa, String> colTenHangHoa = new TableColumn("Tên Hàng");
        colTenHangHoa.setCellValueFactory(new PropertyValueFactory("tenhanghoa"));
        colTenHangHoa.prefWidthProperty().set(225);
        
        
        TableColumn<HangHoa, String> colThuongHieu = new TableColumn("Thương Hiệu");
        colThuongHieu.setCellValueFactory(new PropertyValueFactory("thuonghieu"));
        
        TableColumn<HangHoa, Integer> colSoLuong = new TableColumn("Số Lượng Trong Kho");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soluongtrongkho"));
        colSoLuong.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, BigDecimal> colGiaNhap = new TableColumn("Giá Nhập");
        colGiaNhap.setCellValueFactory(new PropertyValueFactory("gianhap"));
        colGiaNhap.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, String> colGiaNiemYet = new TableColumn("Giá Niêm Yết");
        colGiaNiemYet.setCellValueFactory(new PropertyValueFactory("gianiemyet"));
        colGiaNiemYet.setStyle( "-fx-alignment: CENTER-RIGHT;");

        TableColumn<HangHoa, java.util.Date> colNgaySanXuat = new TableColumn("Ngày Sản Xuất");
        colNgaySanXuat.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
        colNgaySanXuat.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, java.util.Date> colNgayHetHan = new TableColumn("Ngày Hết Hạn");
        colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
        colNgayHetHan.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        
        TableColumn<HangHoa, Boolean> colTinhTrang = new TableColumn("Tình trạng");
        colTinhTrang.setCellValueFactory(new PropertyValueFactory("tinhtrang"));
        colTinhTrang.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        List l = new ArrayList<>();
        l.add(true);
        l.add(false);
        
        ObservableList listTT = FXCollections.observableList(l);
//        colTinhTrang.setCellFactory((TableColumn<HangHoa, Boolean> p) -> {
//            ComboBoxTableCell<HangHoa, Boolean> cell = new ComboBoxTableCell<>(listTT);
//            return cell;
//        });

        
        colTinhTrang.setCellFactory(ComboBoxTableCell.forTableColumn(listTT));
        
//        colTinhTrang.setCellFactory(tc -> new TableCell<HangHoa, Boolean>() {
//            @Override
//            protected void updateItem(Boolean value, boolean empty) {
//                super.updateItem(value, empty) ;
//                if (empty) {
//                    setText(null);
//                } else {
//                    if (value == true)
//                        setText("Đang bán");
//                    else
//                        setText("Ngừng bán");
//                }
//            }
//        });
        
        colTinhTrang.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                HangHoaService hhs = new HangHoaService(conn);
                HangHoa hh = evt.getRowValue();
                Boolean c = evt.getOldValue();
                Boolean m = evt.getNewValue();
                hh.setTinhtrang(m);
                if (m.equals(c)) {
                    hh.setTinhtrang(c);
                } else {
                    if (hhs.suaTinhTrang(hh.getHanghoa_id(), hh.isTinhtrang())) {
                        Utils.getBox("Cập nhật thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        hh.setTinhtrang(c);
                        Utils.getBox("Cập nhật thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbHH.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        TableColumn<HangHoa, String> colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
        colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("tenloaihang"));
        colLoaiHangHoa.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, String> colNhaCungCap= new TableColumn("Nhà Cung Cấp");
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory("nhacungcap"));
        colNhaCungCap.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, String> colSLDaBan= new TableColumn("Số Lượng Đã Bán");
        colSLDaBan.setCellValueFactory(new PropertyValueFactory("slDaBan"));
        colSLDaBan.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        colTenHangHoa.setCellFactory(TextFieldTableCell.forTableColumn());
        colTenHangHoa.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                HangHoaService hhs = new HangHoaService(conn);
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
                    hh.setTenhanghoa(c);
                } else {
                    if (hhs.suaTenHH(hh.getHanghoa_id(), m)) {
                        Utils.getBox("Cập nhật tên hàng hóa thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        hh.setTenhanghoa(c);
                        Utils.getBox("Cập nhật tên hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbHH.refresh();
            } catch (SQLException ex) {
                    Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
            }   

        });
        listLoaiHH.clear();
        listLoaiHH.addAll(getListLoaiHH());
        colLoaiHangHoa.setCellFactory(ComboBoxTableCell.forTableColumn(listLoaiHH ));
        colLoaiHangHoa.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                HangHoaService hhs = new HangHoaService(conn);
                LoaiHangHoaService lhs = new LoaiHangHoaService(conn);
                
                HangHoa hh = evt.getRowValue();
                String c = String.valueOf(hh.getTenloaihang());
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                hh.setTenloaihang(m);
                if (m.equals(c)) {
                    hh.setTenloaihang(c);
                } else {
                    if (hhs.suaLoaiHH(hh.getHanghoa_id(),lhs.getLoaiHHByTen(m))) {
                        hh.setTenloaihang(m);
                        Utils.getBox("Cập nhật Loại hàng hóa thành công!!!", Alert.AlertType.INFORMATION).show();
                        loadHHQLT("","");
                    } else {
                        hh.setTenloaihang(c);
                        Utils.getBox("Cập nhật loại hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbHH.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        colGiaNiemYet.setCellFactory(TextFieldTableCell.forTableColumn());
        colGiaNiemYet.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                HangHoaService hhs = new HangHoaService(conn);
                HangHoa hh = evt.getRowValue();
                String c = hh.getGianiemyet();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                hh.setGianiemyet(m);
                if (m == "") {
                    hh.setGianiemyet(c);
                    Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                } else if (!m.matches("\\d+")){
                    Utils.getBox("Vui lòng chỉ nhập số!", Alert.AlertType.WARNING).show();
                    hh.setGianiemyet(c);
                }   
                else {
                    if (m.length() > 9)
                        Utils.getBox("Vui lòng nhập giá niêm yết < 1.000.000.000", Alert.AlertType.WARNING).show();
                    else if (Integer.parseInt(m) < 10000){
                            hh.setGianiemyet(c);
                            Utils.getBox("Vui lòng nhập giá niêm yết >= 10.000", Alert.AlertType.WARNING).show();
                    }
                    else if (Integer.parseInt(m) <= Integer.parseInt(hh.getGianhap())) {
                        Utils.getBox("Vui lòng nhập giá niêm yết > giá nhập: " + hh.getGianhap(), Alert.AlertType.WARNING).show();
                        hh.setGianiemyet(c);
                }
                else if (m.equals(c)) {
                    hh.setGianiemyet(c);
                } else {
                    if (hhs.suaGiaNiemYet(hh.getHanghoa_id(), m)){
                        Utils.getBox("Cập nhật giá niêm yết thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        hh.setTenhanghoa(c);
                        Utils.getBox("Cập nhật giá niêm yết thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbHH.refresh();
                }
            } catch (SQLException ex) {
                Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.tbHH.getColumns().addAll(colMaHangHoa, colTenHangHoa, colLoaiHangHoa
                    , colSoLuong,colSLDaBan, colGiaNhap, colGiaNiemYet, colNgaySanXuat, colNgayHetHan
                    , colTinhTrang, colThuongHieu, colNhaCungCap);
}

    private void loadTableLoaiHH(){
        TableColumn<LoaiHangHoa, Integer> colMaLoaiHH = new TableColumn("Mã Loại HH");
        colMaLoaiHH.setCellValueFactory(new PropertyValueFactory("loaihanghoa_id"));
        
        TableColumn<LoaiHangHoa, String> colTenLoaiHH = new TableColumn("Tên Loại HH");
        colTenLoaiHH.setCellValueFactory(new PropertyValueFactory("tenloai"));  
        
        colTenLoaiHH.setCellFactory(TextFieldTableCell.forTableColumn());
        colTenLoaiHH.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                LoaiHangHoaService lhhs = new LoaiHangHoaService(conn);
                LoaiHangHoa lhh = evt.getRowValue();
                String c = lhh.getTenloai();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                lhh.setTenloai(m);
                if (m == "") {
                    lhh.setTenloai(c);
                    Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                } else if (m.equals(c)) {
                    lhh.setTenloai(c);
                } else {
                    if (lhhs.suaTenLoaiHH(lhh.getLoaihanghoa_id(), m)) {
                        Utils.getBox("Cập nhật tên loại hàng hóa thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        lhh.setTenloai(c);
                        Utils.getBox("Cập nhật tên loại hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbLoaiHH.refresh();
                loadHHQLT("", "");
            } catch (SQLException ex) {
                Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        TableColumn<LoaiHangHoa, Integer> colSLDB = new TableColumn("Số Lượng Đã Bán");
            colSLDB.setCellValueFactory(new PropertyValueFactory("sldb"));

            TableColumn<LoaiHangHoa, Integer> colSLDN = new TableColumn("Số Lượng Đã Nhập");
            colSLDN.setCellValueFactory(new PropertyValueFactory("sldn"));
            colSLDN.setStyle( "-fx-alignment: CENTER-RIGHT;");

            TableColumn<LoaiHangHoa, Integer> colSLTK = new TableColumn("Số Lượng Trong Kho");
            colSLTK.setCellValueFactory(new PropertyValueFactory("sltk"));
            colSLTK.setStyle( "-fx-alignment: CENTER-RIGHT;");


            this.tbLoaiHH.getColumns().addAll(colMaLoaiHH, colTenLoaiHH, colSLDB
                    , colSLDN,colSLTK);
}

     
    @FXML
    void themLoaiHH(ActionEvent event) throws SQLException {
        try {
            Connection conn = JdbcUtils.getConn();
            LoaiHangHoaService s = new LoaiHangHoaService(conn);
            
            if( s.themLoaiHH(this.txtTenLoaiHH.getText())){
                Utils.getBox("Thêm loại hàng hóa thành công!!!", Alert.AlertType.INFORMATION).show();
                loadLoaiHHQLT();
            }
            else
                Utils.getBox("Thêm loại hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableHH();
        loadHHQLT("", "");
    }
    
    public void loadLoaiHHQLT() throws SQLException{
        this.tbHH.getItems().clear();
        Connection conn = JdbcUtils.getConn();
        LoaiHangHoaService s = new LoaiHangHoaService(conn);
        List<LoaiHangHoa> loaiHH = s.getLoaiHH();
        loaiHH.forEach(h->{
            try {
                h.setSldb(s.getSLDB(h.getLoaihanghoa_id()));
                h.setSldn(s.getSLDN(h.getLoaihanghoa_id()));
                h.setSltk(s.getSLTK(h.getLoaihanghoa_id()));
            } catch (SQLException ex) {
                Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.tbLoaiHH.setItems(FXCollections.observableList(loaiHH));
        conn.close();
    }
    
    public void loadHHQLT(String key, String loaiTC) throws SQLException{
        this.tbHH.getItems().clear();
        Connection conn = JdbcUtils.getConn();
        HangHoaService s = new HangHoaService(conn);
        
        List<HangHoa> hangHoa = s.getHHQLT(key, loaiTC);
        hangHoa.forEach(h->{
            try {
                h.setSlDaBan(s.getSLDB(h.getHanghoa_id()));
            } catch (SQLException ex) {
                Logger.getLogger(HanghoaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.tbHH.setItems(FXCollections.observableList(hangHoa));
        conn.close();
    }
    
    public void loadCBData(){
        ObservableList<String> list = FXCollections.observableArrayList("","Đang bán", "Ngừng bán");
        this.cbTinhTrang.setItems(list);
    }
    
   public ObservableList<String> getListLoaiHH() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        LoaiHangHoaService s = new LoaiHangHoaService(conn);
        
        List<LoaiHangHoa> lhh = s.getLoaiHH();
        
        List<String> tenloaihh = new ArrayList<>();
        
        lhh.forEach(lh ->{
            tenloaihh.add(lh.getTenloai());
        });
        ObservableList listLHH = FXCollections.observableList(tenloaihh);
        conn.close();
        return listLHH;
    }
}
