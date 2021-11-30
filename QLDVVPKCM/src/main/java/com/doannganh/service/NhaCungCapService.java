/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.NhaCungCap_HangHoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhaCungCapService {
    private Connection conn;
    
    public NhaCungCapService(Connection conn) {
        this.conn = conn;
    }
    
    public List<NhaCungCap> getNCCs() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT * FROM nhacungcap");
        
        List<NhaCungCap> rs = new ArrayList<>();
        while (r.next()) {
            NhaCungCap ncc = new NhaCungCap();
            ncc.setNhacungcap_id(r.getInt("nhacungcap_id"));
            ncc.setTencongty(r.getString("tencongty"));
            ncc.setDiachi(r.getString("diachi"));
            ncc.setTinhthanh(r.getString("tinhthanh"));
            ncc.setQuocgia(r.getString("quocgia"));
            ncc.setEmail(r.getString("email"));
            ncc.setSodt(r.getString("sodt"));
            rs.add(ncc);
        }
        return rs;
    }
    
    public List<NhaCungCap> getNCC(String tuKhoa, String traCuu) throws SQLException {
        if (tuKhoa == null)
            throw new SQLDataException();
        String sql = "";
        PreparedStatement stm = null;
        if (traCuu == "" || tuKhoa == "") {
            sql = "SELECT nhacungcap.*, COUNT(hanghoa_id) AS tongmathang"
                + " FROM nhacungcap, nhacungcap_hanghoa"
                + " WHERE nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " GROUP BY nhacungcap_id ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
        }
        if (traCuu == "Mã nhà cung cấp") {
            sql = "SELECT nhacungcap.*, COUNT(hanghoa_id) AS tongmathang"
                + " FROM nhacungcap, nhacungcap_hanghoa"
                + " WHERE nhacungcap.nhacungcap_id like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " GROUP BY nhacungcap_id ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Tên công ty") {
            sql = "SELECT nhacungcap.*, COUNT(hanghoa_id) AS tongmathang"
                + " FROM nhacungcap, nhacungcap_hanghoa"
                + " WHERE tencongty like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " GROUP BY nhacungcap_id ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        
        ResultSet rs = stm.executeQuery();
        
        List<NhaCungCap> nhaCungCap = new ArrayList<>();
        while (rs.next()) {
            NhaCungCap ncc = new NhaCungCap();
            ncc.setNhacungcap_id(rs.getInt("nhacungcap_id"));
            ncc.setTencongty(rs.getString("tencongty"));
            ncc.setDiachi(rs.getString("diachi"));
            ncc.setTinhthanh(rs.getString("tinhthanh"));
            ncc.setQuocgia(rs.getString("quocgia"));
            ncc.setEmail(rs.getString("email"));
            ncc.setSodt(rs.getString("sodt"));
            ncc.setTongmathang(rs.getInt("tongmathang"));
            
            nhaCungCap.add(ncc);
        }
        return nhaCungCap;
    }
    
    public List<NhaCungCap_HangHoa> getNCCHHByIDNV(int idnv) throws SQLException{
        String sql= "SELECT * FROM qldvvpkcm.nhacungcap_hanghoa WHERE nhanvien_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1,idnv);
        
        ResultSet rs = stm.executeQuery();
        
        List<NhaCungCap_HangHoa> ncchhs = new ArrayList<>();
        while(rs.next()){
            NhaCungCap_HangHoa ncchh = new NhaCungCap_HangHoa();
            ncchh.setNhanvien_id(rs.getInt("nhanvien_id"));
            ncchh.setNhacungcap_id(rs.getInt("nhacungcap_id"));
            ncchh.setHanghoa_id(rs.getInt("hanghoa_id"));
            ncchh.setSoLuong(rs.getInt("soluong"));
            ncchh.setNgaynhap(rs.getString("ngaynhap"));
            ncchh.setNgaysanxuat(rs.getString("ngaysanxuat"));
            ncchh.setNgayhethan(rs.getString("ngayhethan"));
            ncchh.setGianhap(rs.getString("gianhap"));
            ncchh.setGhichu(rs.getString("ghichu"));
            
            ncchhs.add(ncchh);
        }
        return ncchhs;
    }
    
    public NhaCungCap getNCCByID(int id) throws SQLException{
        String sql= "SELECT * FROM qldvvpkcm.nhacungcap WHERE nhacungcap_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1,id);
        
        ResultSet rs = stm.executeQuery();
        
        NhaCungCap ncc = new NhaCungCap();
        while(rs.next()){
            ncc.setNhacungcap_id(rs.getInt("nhacungcap_id"));
            ncc.setTencongty(rs.getString("tencongty"));
            ncc.setDiachi(rs.getString("diachi"));
            ncc.setTinhthanh(rs.getString("tinhthanh"));
            ncc.setQuocgia(rs.getString("quocgia"));
            ncc.setEmail(rs.getString("email"));
            ncc.setSodt(rs.getString("sodt"));
        }
        return ncc;
    }
    
        
    public NhaCungCap_HangHoa getNCCHH(int idhh, int idncc, String date) throws SQLException{
        String sql= "SELECT * FROM qldvvpkcm.nhacungcap_hanghoa WHERE nhacungcap_id = ?"
                + " AND hanghoa_id = ? AND ngaynhap = ? ";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1,idncc);
        stm.setInt(2,idhh);
        stm.setString(3,date);
        
        
        ResultSet rs = stm.executeQuery();
        
        NhaCungCap_HangHoa ncc = new NhaCungCap_HangHoa();
        while(rs.next()){
            ncc.setNhacungcap_id(rs.getInt("nhacungcap_id"));
            ncc.setHanghoa_id(rs.getInt("hanghoa_id"));
            ncc.setSoLuong(rs.getInt("soluong"));
            ncc.setNgaynhap(rs.getString("ngaynhap"));
            ncc.setNgaysanxuat(rs.getString("ngaysanxuat"));
            ncc.setNgayhethan(rs.getString("ngayhethan"));
            ncc.setGianhap(rs.getString("gianhap"));
            ncc.setNhanvien_id(rs.getInt("nhanvien_id"));
            ncc.setGhichu(rs.getString("ghichu"));
        }
        return ncc;
    }
    
    
    public int getNCCByTen(String ten) throws SQLException {
        
        String sql= "SELECT nhacungcap_id FROM nhacungcap WHERE tencongty=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ten);
        int id = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("nhacungcap_id");
        }
        return id;
    }
    
    public String getTenCTByid(int id) throws SQLException {
        
        String sql= "SELECT tencongty FROM nhacungcap WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        String tenCT = "";
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            tenCT = rs.getString("tencongty");
        }
        return tenCT;
    }
    
    public boolean suaTenCongTy(int id, String ten)  throws SQLException {
        String sql ="UPDATE nhacungcap SET tencongty=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ten);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaDiaChi(int id, String dc)  throws SQLException {
        String sql ="UPDATE nhacungcap SET diachi=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, dc);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaTinhThanh(int id, String tt)  throws SQLException {
        String sql ="UPDATE nhacungcap SET tinhthanh=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, tt);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaQuocGia(int id, String qg)  throws SQLException {
        String sql ="UPDATE nhacungcap SET quocgia=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, qg);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaEmail(int id, String email)  throws SQLException {
        String sql ="UPDATE nhacungcap SET email=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, email);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaSoDT(int id, String sodt)  throws SQLException {
        String sql ="UPDATE nhacungcap SET sodt=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, sodt);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean themNCC(NhaCungCap ncc) throws SQLException {
        String sql = "INSERT INTO nhacungcap(tencongty, diachi, tinhthanh"
                + ", quocgia, email, sodt)"
                + " VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ncc.getTencongty());
        stm.setString(2, ncc.getDiachi());
        stm.setString(3, ncc.getTinhthanh());
        stm.setString(4, ncc.getQuocgia());
        stm.setString(5, ncc.getEmail());
        stm.setString(6, ncc.getSodt());
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
     public List<NhaCungCap_HangHoa> getNCCHH(String tuKhoa, String traCuu) throws SQLException {
        if (tuKhoa == null)
            throw new SQLDataException();
        String sql = "";
        PreparedStatement stm = null;
        if ("".equals(traCuu) || "".equals(tuKhoa)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user, qldvvpkcm.hanghoa"
                + " WHERE nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " ORDER BY hanghoa_id";
            stm = this.conn.prepareStatement(sql);
        }
        if ("Mã hàng hóa".equals(traCuu)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user,qldvvpkcm.hanghoa"
                + " WHERE hanghoa.hanghoa_id like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Tên hàng hóa".equals(traCuu)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user,qldvvpkcm.hanghoa"
                + " WHERE hanghoa.tenhanghoa like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Số lượng".equals(traCuu)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user,qldvvpkcm.hanghoa"
                + " WHERE nhacungcap_hanghoa.soluong like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        
        if ("Ngày nhập".equals(traCuu)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user,qldvvpkcm.hanghoa"
                + " WHERE date(nhacungcap_hanghoa.ngaynhap) <= ?"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Giá nhập".equals(traCuu)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user,qldvvpkcm.hanghoa"
                + " WHERE gianhap like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Mã nhà cung cấp".equals(traCuu)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user,qldvvpkcm.hanghoa"
                + " WHERE nhacungcap.nhacungcap_id like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Tên công ty".equals(traCuu)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user,qldvvpkcm.hanghoa"
                + " WHERE tencongty like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Mã nhân viên".equals(traCuu)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user,qldvvpkcm.hanghoa"
                + " WHERE user.user_id like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Tên nhân viên".equals(traCuu)) {
            sql = "SELECT nhacungcap_hanghoa.*,tenhanghoa, hoten, tencongty"
                + " FROM qldvvpkcm.nhacungcap_hanghoa, qldvvpkcm.nhacungcap, qldvvpkcm.user,qldvvpkcm.hanghoa"
                + " WHERE hoten like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND user.user_id = nhacungcap_hanghoa.nhanvien_id"
                + " ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        ResultSet rs = stm.executeQuery();
        
        List<NhaCungCap_HangHoa> nhaCungCapHH = new ArrayList<>();
        while (rs.next()) {
            NhaCungCap_HangHoa ncchh = new NhaCungCap_HangHoa();
            ncchh.setHanghoa_id(rs.getInt("hanghoa_id"));
            ncchh.setNhacungcap_id(rs.getInt("nhacungcap_id"));
            ncchh.setSoLuong(rs.getInt("soluong"));
            ncchh.setNgaynhap(rs.getString("ngaynhap"));
            ncchh.setNgaysanxuat(rs.getString("ngaysanxuat"));
            ncchh.setNgaysanxuat(rs.getString("ngayhethan"));
            ncchh.setGianhap(rs.getString("gianhap"));
            ncchh.setNhanvien_id(rs.getInt("nhanvien_id"));
            ncchh.setTenNV(rs.getString("hoten"));
            ncchh.setTenHH(rs.getString("tenhanghoa"));    
            ncchh.setTenNCC(rs.getString("tencongty"));  
            
            nhaCungCapHH.add(ncchh);
        }
        return nhaCungCapHH;
    }
    
    
    public int tinhChiPhiDH(int idhh, String ngayNhap, int idncc) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where date(ngaynhap) = ? AND nhacungcap_id  = ?"
                + " AND hanghoa_id = ? ";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ngayNhap);
        stm.setInt(2, idncc);
        stm.setInt(3, idhh);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiByDate(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where date(ngaynhap) = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiByMonth(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where month(ngaynhap) = month(?) and year(ngaynhap) = year(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        stm.setString(2, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiByYear(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where year(ngaynhap) = year(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiUntilDate(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where date(ngaynhap) <= ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiUntilMonth(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where date(ngaynhap) <= ? or (month(ngaynhap) = month(?) and year(ngaynhap) = year(?))";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        stm.setString(2, date);
        stm.setString(3, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiUntilYear(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where year(ngaynhap) <= year(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
}
