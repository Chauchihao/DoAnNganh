/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.LoaiHangHoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class LoaiHangHoaService {
    private Connection conn;
    
    public LoaiHangHoaService(Connection conn) {
        this.conn = conn;
    }
    
    public List<LoaiHangHoa> getLoaiHH() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT * FROM loaihanghoa");
        
        List<LoaiHangHoa> rs = new ArrayList<>();
        while (r.next()) {
            LoaiHangHoa lhh = new LoaiHangHoa();
            lhh.setLoaihanghoa_id(r.getInt("loaihanghoa_id"));
            lhh.setTenloai(r.getString("tenloai"));
            rs.add(lhh);
        }
        return rs;
    }
    
    public int getLoaiHHByTen(String ten) throws SQLException {
        
        String sql= "SELECT loaihanghoa_id FROM loaihanghoa WHERE tenloai=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ten);
        int id = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("loaihanghoa_id");
        }
        return id;
    }
    
    /*public List getTenLoai() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT tenloai FROM loaihanghoa");
        
        List rs = new ArrayList<>();
        while (r.next()) {
            LoaiHangHoa lhh = new LoaiHangHoa();
            lhh.setTenloai(r.getString("tenloai"));
            rs.add(lhh);
        }
        return rs;
    }*/
}
