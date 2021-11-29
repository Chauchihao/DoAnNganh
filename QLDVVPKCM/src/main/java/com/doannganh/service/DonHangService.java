/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.DonHang;
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
public class DonHangService {
    private Connection conn;
    
    public DonHangService(Connection conn) {
        this.conn = conn;
    }
    
    public List<DonHang> getDonHang(String tuKhoa, String traCuu) throws SQLException {
        if (tuKhoa == null)
            throw new SQLDataException();
        String sql = "";
        PreparedStatement stm = null;
        if ("".equals(traCuu) || "".equals(tuKhoa)) {
            sql = "SELECT donhang.*, user.hoten, khachhang.hoten,"
                + " COUNT(chitietdonhang.donhang_id) AS tongCTDH,"
                + " SUM(soluong * dongia * (1 - giamgia)) AS tongtien"
                + " FROM donhang, user, khachhang, chitietdonhang"
                + " WHERE donhang.nhanvien_id = user.user_id"
                + " AND donhang.khachhang_id = khachhang.khachhang_id"
                + " AND donhang.donhang_id = chitietdonhang.donhang_id"
                + " GROUP BY donhang_id ORDER BY donhang_id DESC";
            stm = this.conn.prepareStatement(sql);
        }
        if ("Mã đơn hàng".equals(traCuu)) {
            sql = "SELECT donhang.*, user.hoten, khachhang.hoten,"
                + " COUNT(chitietdonhang.donhang_id) AS tongCTDH,"
                + " SUM(soluong * dongia * (1 - giamgia)) AS tongtien"
                + " FROM donhang, user, khachhang, chitietdonhang"
                + " WHERE donhang.donhang_id like concat('%', ?, '%')"
                + " AND donhang.nhanvien_id = user.user_id"
                + " AND donhang.khachhang_id = khachhang.khachhang_id"
                + " AND donhang.donhang_id = chitietdonhang.donhang_id"
                + " GROUP BY donhang_id ORDER BY donhang_id DESC";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Ngày mua hàng".equals(traCuu)) {
            sql = "SELECT donhang.*, user.hoten, khachhang.hoten,"
                + " COUNT(chitietdonhang.donhang_id) AS tongCTDH,"
                + " SUM(soluong * dongia * (1 - giamgia)) AS tongtien"
                + " FROM donhang, user, khachhang, chitietdonhang"
                + " WHERE ngayTaoDH <= ?"
                + " AND donhang.nhanvien_id = user.user_id"
                + " AND donhang.khachhang_id = khachhang.khachhang_id"
                + " AND donhang.donhang_id = chitietdonhang.donhang_id"
                + " GROUP BY donhang_id ORDER BY donhang_id DESC";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Nhân viên".equals(traCuu)) {
            sql = "SELECT donhang.*, user.hoten, khachhang.hoten,"
                + " COUNT(chitietdonhang.donhang_id) AS tongCTDH,"
                + " SUM(soluong * dongia * (1 - giamgia)) AS tongtien"
                + " FROM donhang, user, khachhang, chitietdonhang"
                + " WHERE user.hoten like concat('%', ?, '%')"
                + " AND donhang.nhanvien_id = user.user_id"
                + " AND donhang.khachhang_id = khachhang.khachhang_id"
                + " AND donhang.donhang_id = chitietdonhang.donhang_id"
                + " GROUP BY donhang_id ORDER BY donhang_id DESC";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Mã Nhân viên".equals(traCuu)) {
            sql = "SELECT donhang.*, user.hoten, khachhang.hoten,"
                + " COUNT(chitietdonhang.donhang_id) AS tongCTDH,"
                + " SUM(soluong * dongia * (1 - giamgia)) AS tongtien"
                + " FROM donhang, user, khachhang, chitietdonhang"
                + " WHERE user.user_id like concat('%', ?, '%')"
                + " AND donhang.nhanvien_id = user.user_id"
                + " AND donhang.khachhang_id = khachhang.khachhang_id"
                + " AND donhang.donhang_id = chitietdonhang.donhang_id"
                + " GROUP BY donhang_id ORDER BY donhang_id DESC";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Khách hàng".equals(traCuu)) {
            sql = "SELECT donhang.*, user.hoten, khachhang.hoten,"
                + " COUNT(chitietdonhang.donhang_id) AS tongCTDH,"
                + " SUM(soluong * dongia * (1 - giamgia)) AS tongtien"
                + " FROM donhang, user, khachhang, chitietdonhang"
                + " WHERE khachhang.hoten like concat('%', ?, '%')"
                + " AND donhang.nhanvien_id = user.user_id"
                + " AND donhang.khachhang_id = khachhang.khachhang_id"
                + " AND donhang.donhang_id = chitietdonhang.donhang_id"
                + " GROUP BY donhang_id ORDER BY donhang_id DESC";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if ("Mã Khách hàng".equals(traCuu)) {
            sql = "SELECT donhang.*, user.hoten, khachhang.hoten,"
                + " COUNT(chitietdonhang.donhang_id) AS tongCTDH,"
                + " SUM(soluong * dongia * (1 - giamgia)) AS tongtien"
                + " FROM donhang, user, khachhang, chitietdonhang"
                + " WHERE khachhang.khachhang_id like concat('%', ?, '%')"
                + " AND donhang.nhanvien_id = user.user_id"
                + " AND donhang.khachhang_id = khachhang.khachhang_id"
                + " AND donhang.donhang_id = chitietdonhang.donhang_id"
                + " GROUP BY donhang_id ORDER BY donhang_id DESC";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        
        ResultSet rs = stm.executeQuery();
        
        List<DonHang> donHang = new ArrayList<>();
        while (rs.next()) {
            DonHang dh = new DonHang();
            dh.setDonhang_id(rs.getInt("donhang_id"));
            dh.setNgaytaodh(rs.getString("ngayTaoDH"));
            dh.setHoTenNV(rs.getString("user.hoten"));
            dh.setHoTenKH(rs.getString("khachhang.hoten"));
            dh.setTongCTDH(rs.getInt("tongCTDH"));
            dh.setTongTien(rs.getString("tongtien"));
            dh.setKhachhang_id(rs.getInt("khachhang_id"));
            dh.setNhanvien_id(rs.getInt("nhanvien_id"));
            donHang.add(dh);
        }
        return donHang;
    }
    
    public int getIDNVByIDDH(int idDH) throws SQLException {
        String sql = "SELECT nhanvien_id FROM donhang"
                    + " WHERE donhang_id=?";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, idDH);
        ResultSet rs = stm.executeQuery();
        
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("nhanvien_id");
        }
        return id;
    }
    
    public DonHang getDHByIDDH(int idDH) throws SQLException {
        String sql = "SELECT donhang.*, user.hoten, khachhang.hoten,"
                + " COUNT(chitietdonhang.donhang_id) AS tongCTDH,"
                + " SUM(soluong * dongia * (1 - giamgia)) AS tongtien"
                + " FROM donhang, user, khachhang, chitietdonhang"
                + " WHERE donhang.donhang_id = ?"
                + " AND donhang.nhanvien_id = user.user_id"
                + " AND donhang.khachhang_id = khachhang.khachhang_id"
                + " AND donhang.donhang_id = chitietdonhang.donhang_id"
                + " GROUP BY donhang_id ORDER BY donhang_id DESC";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, idDH);
        ResultSet rs = stm.executeQuery();
        
        DonHang dh = new DonHang();
        while (rs.next()) {
            dh.setDonhang_id(rs.getInt("donhang_id"));
            dh.setNgaytaodh(rs.getString("ngayTaoDH"));
            dh.setNhanvien_id(rs.getInt("nhanvien_id"));
            dh.setHoTenNV(rs.getString("user.hoten"));
            dh.setKhachhang_id(rs.getInt("khachhang_id"));
            dh.setHoTenKH(rs.getString("khachhang.hoten"));
            dh.setTongCTDH(rs.getInt("tongCTDH"));
            dh.setTongTien(rs.getString("tongtien"));
        }
        return dh;
    }
    
    public boolean themDH(DonHang dh) throws SQLException {
        String sql = "INSERT INTO donhang(ngayTaoDH,nhanvien_id,khachhang_id)"
                    + " VALUES(?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, dh.getNgaytaodh());
        stm.setInt(2, dh.getNhanvien_id());
        stm.setInt(3, dh.getKhachhang_id());
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public int tongDH() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT COUNT(*) AS tong FROM donhang");
        
        int tong = -1;
        while (rs.next()) {
            tong = rs.getInt("tong");
        }
        return tong;
    }
    
    public List<Integer> getDHIDByDate(String date) throws SQLException{

            ArrayList<Integer> a = new ArrayList<>();
            String sql = "SELECT * FROM qldvvpkcm.donhang"
                    + " Where date(ngayTaoDH) = ?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, date);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                a.add(rs.getInt("donhang_id"));
            }
            return a;
    }
    
    public List<Integer> getDHIDByMonth(String date) throws SQLException{

            ArrayList<Integer> a = new ArrayList<>();
            String sql = "SELECT * FROM qldvvpkcm.donhang"
                     + " Where month(ngayTaoDH) = month(?) and year(ngayTaoDH) = year(?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, date);
            stm.setString(2, date);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                a.add(rs.getInt("donhang_id"));
            }
            return a;
    }
    public List<Integer> getDHIDByYear(String date) throws SQLException{

            ArrayList<Integer> a = new ArrayList<>();
            String sql = "SELECT * FROM qldvvpkcm.donhang"
                    + " Where year(ngayTaoDH) = year(?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, date);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                a.add(rs.getInt("donhang_id"));
            }
            return a;
    }
    public List<DonHang> getDHByIDNV(int idnv) throws SQLException{
        ArrayList<DonHang> a = new ArrayList<>();
        String sql = "SELECT * FROM qldvvpkcm.donhang"
                + " Where nhanvien_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, idnv);
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            DonHang dh = new DonHang();
            dh.setDonhang_id(rs.getInt("donhang_id"));
            dh.setNgaytaodh(rs.getString("ngayTaoDH"));
            dh.setNhanvien_id(rs.getInt("nhanvien_id"));
            dh.setKhachhang_id(rs.getInt("khachhang_id"));

            a.add(dh);
        }
        return a;
    }
}
