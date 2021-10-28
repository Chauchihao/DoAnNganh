/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.ChiTietDonHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class ChiTietDonHangService {
    private Connection conn;
    
    public ChiTietDonHangService(Connection conn) {
        this.conn = conn;
    }
    
    public void suaKhoaNgoai0() throws SQLException {
        Statement stm = this.conn.createStatement();
        stm.executeQuery("SET FOREIGN_KEY_CHECKS=0");
    }
    
    public void suaKhoaNgoai1() throws SQLException {
        Statement stm = this.conn.createStatement();
        stm.executeQuery("SET FOREIGN_KEY_CHECKS=1");
    }
    
    public void suaIdDH(int id) throws SQLException {
        String sql ="UPDATE hanghoa SET hanghoa_id=? WHERE hanghoa_id=0";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        suaKhoaNgoai1();
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
}
