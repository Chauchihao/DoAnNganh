/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.NhaCungCap;
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
public class NhaCungCapService {
    private Connection conn;
    
    public NhaCungCapService(Connection conn) {
        this.conn = conn;
    }
    
    public List<NhaCungCap> getNCC() throws SQLException {
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
}
