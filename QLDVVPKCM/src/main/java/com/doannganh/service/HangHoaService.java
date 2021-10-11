/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.HangHoa;
import java.math.BigDecimal;
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
public class HangHoaService {
    private Connection conn;

    public HangHoaService(Connection conn) {
        this.conn = conn;
    }
    
    public List<HangHoa> getHangHoas() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT hanghoa.*, tenloai FROM hanghoa, loaihanghoa"
                + " WHERE hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                + " ORDER BY hanghoa_id");
        
        List<HangHoa> hangHoa = new ArrayList<>();
        while (r.next()) {
            HangHoa hh = new HangHoa();
            hh.setHanghoa_id(r.getInt("hanghoa_id"));
            hh.setTenhanghoa(r.getString("tenhanghoa"));
            hh.setThuonghieu(r.getString("thuonghieu"));
            hh.setSoluongtrongkho(r.getBigDecimal("soluongtrongkho"));
            hh.setGianhap(r.getBigDecimal("gianhap"));
            hh.setGiaban(r.getBigDecimal("giaban"));
            hh.setNgaysanxuat(r.getDate("ngaysanxuat"));
            hh.setNgayhethan(r.getDate("ngayhethan"));
            hh.setLoaihanghoa_id(r.getString("tenloai"));
            
            hangHoa.add(hh);
        }
        return hangHoa;
    }
    
    public List<HangHoa> getHangHoa(String tuKhoa, String traCuu) throws SQLException {
        if (tuKhoa == null)
            throw new SQLDataException();
        String sql = "";
        if (traCuu == "" || tuKhoa == "")
            sql = "SELECT hanghoa.*, tenloai FROM hanghoa, loaihanghoa"
                    + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                    + " ORDER BY hanghoa_id";
        if (traCuu == "Mã hàng")
            sql = "SELECT hanghoa.*, tenloai FROM hanghoa, loaihanghoa"
                    + " WHERE hanghoa_id like concat('%', ?, '%')"
                    + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                    + " ORDER BY hanghoa_id";
        if (traCuu == "Tên hàng")
            sql = "SELECT hanghoa.*, tenloai"
                    + " FROM hanghoa, loaihanghoa"
                    + " WHERE tenhanghoa like concat('%', ?, '%')"
                    + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                    + " ORDER BY hanghoa_id";
        if (traCuu == "Thương hiệu")
            sql = "SELECT hanghoa.*, tenloai"
                    + " FROM hanghoa, loaihanghoa"
                    + " WHERE thuonghieu like concat('%', ?, '%')"
                    + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                    + " ORDER BY hanghoa_id";
        if (traCuu == "Loại hàng")
            sql = "SELECT hanghoa.*, tenloai"
                    + " FROM hanghoa, loaihanghoa WHERE tenloai like concat('%', ?, '%')"
                    + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                    + " ORDER BY hanghoa_id";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, tuKhoa);
        ResultSet rs = stm.executeQuery();
        
        List<HangHoa> hangHoa = new ArrayList<>();
        while (rs.next()) {
            HangHoa hh = new HangHoa();
            hh.setHanghoa_id(rs.getInt("hanghoa_id"));
            hh.setTenhanghoa(rs.getString("tenhanghoa"));
            hh.setThuonghieu(rs.getString("thuonghieu"));
            hh.setSoluongtrongkho(rs.getBigDecimal("soluongtrongkho"));
            hh.setGianhap(rs.getBigDecimal("gianhap"));
            hh.setGiaban(rs.getBigDecimal("giaban"));
            hh.setNgaysanxuat(rs.getDate("ngaysanxuat"));
            hh.setNgayhethan(rs.getDate("ngayhethan"));
            hh.setLoaihanghoa_id(rs.getString("tenloai"));
            
            hangHoa.add(hh);
        }
        return hangHoa;
    }
    
    public boolean suaGiaBan(int id, String gb) throws SQLException {
        String sql = "UPDATE hanghoa SET giaban=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, gb);
        stm.setInt(2, id);
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
}
