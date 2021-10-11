/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.LoaiHangHoa;
import java.sql.Connection;
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
    
    public List<LoaiHangHoa> getLoaiTK() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT * FROM loaihanhoa");
        
        List<LoaiHangHoa> rs = new ArrayList<>();
        while (r.next()) {
            LoaiHangHoa lhh = new LoaiHangHoa();
            lhh.setLoaihanghoa_id(r.getInt("loaihanghoa_id"));
            lhh.setTenloai(r.getString("tenloai"));
            
            rs.add(lhh);
        }
        return rs;
    }
}
