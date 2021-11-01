/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.ChiTietDonHang;
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
public class ChiTietDonHangService {
    private Connection conn;
    
    public ChiTietDonHangService(Connection conn) {
        this.conn = conn;
    }
    
    public void suaIdDH(int id) throws SQLException {
        String sql ="UPDATE hanghoa SET hanghoa_id=? WHERE hanghoa_id=0";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
    }
    
    public boolean themCTDH(ChiTietDonHang ctdh) throws SQLException {
        
        //suaKhoaNgoai0();
        String sql = "INSERT INTO chitietdonhang(donhang_id,hanghoa_id,soluong,dongia,giamgia)"
                + " VALUES(?, ?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, ctdh.getDonhang_id());
        stm.setInt(2, ctdh.getHanghoa_id());
        stm.setString(3, ctdh.getSoluong());
        stm.setString(4, ctdh.getDongia());
        stm.setString(5, ctdh.getGiamgia());
        //suaKhoaNgoai0();
        int row = stm.executeUpdate();
        //suaKhoaNgoai1();
        return row > 0;
    }
    
    public List<ChiTietDonHang> getChiTietByID(int id) throws SQLException{
        String sql = "SELECT * FROM qldvvpkcm.chitietdonhang " 
                + "Where donhang_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        List<ChiTietDonHang> chiTietDH= new ArrayList<>();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            ChiTietDonHang ct = new ChiTietDonHang();
            ct.setDonhang_id(rs.getInt("donhang_id"));
            ct.setDongia(rs.getString("dongia"));
            ct.setSoluong(rs.getString("soluong"));
            ct.setGiamgia(rs.getString("giamgia"));
            
            chiTietDH.add(ct);
        }
        return chiTietDH;
    }
    
    
    public int tongDHByID(int id) throws SQLException{
        String sql = "SELECT sum(dongia*(1-giamgia)) as tong FROM qldvvpkcm.chitietdonhang " 
                + "Where donhang_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        int tong = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            tong = rs.getInt("tong");
        }
        return tong;
    }
}
