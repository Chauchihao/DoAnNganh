/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.HangHoa;
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
        ResultSet r = stm.executeQuery("SELECT * FROM hanghoa");
        
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
            hh.setLoaihanghoa_id(r.getInt("loaihanghoa_id"));
            
            hangHoa.add(hh);
        }
        return hangHoa;
    }
    
    public List<HangHoa> getHangHoa(String tuKhoa) throws SQLException {
        if (tuKhoa == null)
            throw new SQLDataException();
        String sql = "SELECT *"
            + " FROM hanghoa WHERE (tenhanghoa like concat('%', ?, '%')) ";
        
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
            hh.setLoaihanghoa_id(rs.getInt("loaihanghoa_id"));
            
            hangHoa.add(hh);
        }
        return hangHoa;
    }
    
    /*public List<HangHoa> getHangHoa(int id, String ten, String thuongHieu, String loai) throws SQLException {
        String sql = "";
        if (id != 0)
            sql = "SELECT hanghoa_id, tenhanghoa, thuonghieu, soluongtrongkho"
            + ",gianhap, giaban, ngaysanxuat, ngayhethan"
            + " FROM hanghoa, loaihanghoa WHERE"
            + " (hanghoa_id like concat('%', ?, '%') OR tenhanghoa "
            + "like concat('%', ?, '%') OR thuonghieu like concat('%', ?, '%')"
            + " OR tenLoai like concat('%', ?, '%')) AND"
            + " hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id";
        else
            sql = "SELECT hanghoa_id, tenhanghoa, thuonghieu, soluongtrongkho"
            + ",gianhap, giaban, ngaysanxuat, ngayhethan"
            + " FROM hanghoa, loaihanghoa WHERE"
            + " (hanghoa_id like concat('%', ?, '%') OR tenhanghoa "
            + "like concat('%', ?, '%') OR thuonghieu like concat('%', ?, '%')"
            + " OR tenLoai like concat('%', ?, '%'))";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        if (id == 0)
            stm.setString(1, null);
        else
            stm.setInt(1, id);
        if (ten == "")
            stm.setString(2, null);
        else
            stm.setString(2, ten);
        if (thuongHieu == "")
            stm.setString(3, null);
        else
            stm.setString(3, thuongHieu);
        if (loai == "")
            stm.setString(4, null);
        else
            stm.setString(4, loai);
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
            //hh.setLoaihanghoa_id(rs.getString("tenloai"));
            
            hangHoa.add(hh);
        }
        return hangHoa;
    }*/
}
