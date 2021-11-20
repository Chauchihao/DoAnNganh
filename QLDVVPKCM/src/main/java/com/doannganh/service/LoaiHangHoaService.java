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
    
    public String getLoaiHHByid(int id) throws SQLException {
        
        String sql= "SELECT tenloai FROM loaihanghoa WHERE loaihanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        String ten = "";
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            ten = rs.getString("tenloai");
        }
        return ten;
    }
    
    public int getSLDB(int id) throws SQLException{
        String sql = "SELECT ifnull(sum(chitietdonhang.soluong), 0) as soluongdb "
                   + "FROM qldvvpkcm.chitietdonhang, qldvvpkcm.hanghoa, qldvvpkcm.loaihanghoa "
                   + "WHERE  hanghoa.loaihanghoa_id  = ? "
                   + "AND loaihanghoa.loaihanghoa_id = hanghoa.loaihanghoa_id "
                   + "AND chitietdonhang.hanghoa_id = hanghoa.hanghoa_id";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        int sldb = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sldb = rs.getInt("soluongdb");
        }
        return sldb;   
    }
    
    public int getSLDN(int id) throws SQLException{
        String sql = "SELECT ifnull(sum(soluong), 0) as soluongdn\n" 
                    +"FROM qldvvpkcm.hanghoa, qldvvpkcm.nhacungcap_hanghoa\n" 
                    +"WHERE hanghoa.loaihanghoa_id  = ?\n" 
                    +"AND nhacungcap_hanghoa.hanghoa_id = hanghoa.hanghoa_id";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        int sldn = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sldn = rs.getInt("soluongdn");
        }
        return sldn;   
    }
    
    public int getSLTK(int id) throws SQLException{
        String sql = "SELECT ifnull(sum(soluongtrongkho), 0) as soluongtk\n" 
                    +"FROM  qldvvpkcm.hanghoa\n" 
                    +"WHERE  hanghoa.loaihanghoa_id  = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        int sltk = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sltk = rs.getInt("soluongtk");
        }
        return sltk;   
    }
    
    public boolean suaTenLoaiHH(int id, String ten)  throws SQLException {
        String sql ="UPDATE loaihanghoa SET tenloai=? WHERE loaihanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ten);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean themLoaiHH(String loaihh) throws SQLException {
        String sql = "INSERT INTO loaihanghoa(tenloai)"
                    + " VALUES(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, loaihh);
        
        int row = stm.executeUpdate();
        
        return row > 0;
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
