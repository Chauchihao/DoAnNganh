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
    
    public boolean themDH(DonHang dh) throws SQLException {
        String sql = "";
        PreparedStatement stm;
        if (dh.getKhachhang_id() != 0) {
            sql = "INSERT INTO donhang(ngayTaoDH,nhanvien_id,khachhang_id)"
                    + " VALUES(?, ?, ?)";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, dh.getNgaytaodh());
            stm.setInt(2, dh.getNhanvien_id());
            stm.setInt(3, dh.getKhachhang_id());
        } else {
            sql = "INSERT INTO donhang(ngayTaoDH,nhanvien_id)"
                    + " VALUES(?, ?)";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, dh.getNgaytaodh());
            stm.setInt(2, dh.getNhanvien_id());
        }
        
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
                    + " Where month(ngayTaoDH) = month(?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, date);
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
}
