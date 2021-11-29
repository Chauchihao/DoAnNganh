/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UserService {
    private Connection conn;

    public UserService(Connection conn) {
        this.conn = conn;
    }
    
    public boolean login(User u) throws SQLException{
        String sql = "SELECT * FROM user WHERE taikhoan=? AND matkhau=? AND loaiuser_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, u.getTaikhoan());
        stm.setString(2, u.getMatkhau());
        stm.setInt(3, u.getLoaiuser_id());
        ResultSet rs = stm.executeQuery();
        
        if (rs.next())
            return true;
        return false;
    }
    
    public User getUser(String taiKhoan) throws SQLException {
        String sql = "SELECT user_id, hoten, ngaysinh, gioitinh, cmnd, taikhoan"
                + ", ngayVaoLam, email, diachi, sdt, trangthai, loaiuser_id"
                + " FROM user WHERE taikhoan=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, taiKhoan);
        
        ResultSet rs = stm.executeQuery();
        User u = null;
        while (rs.next()) {
            u = new User();
            u.setUser_id(rs.getInt("user_id"));
            u.setHoten(rs.getString("hoTen"));
            u.setNgaysinh(rs.getString("ngaysinh"));
            u.setGioitinh(rs.getString("gioitinh"));
            u.setCmnd(rs.getString("cmnd"));
            u.setTaikhoan(rs.getString("taikhoan"));
            u.setNgayvaolam(rs.getString("ngayVaoLam"));
            u.setEmail(rs.getString("email"));
            u.setDiachi(rs.getString("diachi"));
            u.setSdt(rs.getString("sdt"));
            u.setTrangthai(rs.getBoolean("trangthai"));
            u.setLoaiuser_id(rs.getInt("loaiuser_id"));
        }
        return u;
    }
    public User getUserByID(int id) throws SQLException {
        String sql = "SELECT user.*\n" +
                    "FROM qldvvpkcm.user \n" +
                    "WHERE user_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        
        ResultSet rs = stm.executeQuery();
        User u = null;
        while (rs.next()) {
            u = new User();
            u.setUser_id(rs.getInt("user_id"));
            u.setHoten(rs.getString("hoten"));
            u.setNgaysinh(rs.getString("ngaysinh"));
            u.setGioitinh(rs.getString("gioitinh"));
            u.setCmnd(rs.getString("cmnd"));
            u.setTaikhoan(rs.getString("taikhoan"));
            u.setNgayvaolam(rs.getString("ngayVaoLam"));
            u.setEmail(rs.getString("email"));
            u.setDiachi(rs.getString("diachi"));
            u.setSdt(rs.getString("sdt"));
            u.setTrangthai(rs.getBoolean("trangthai"));
            u.setLoaiuser_id(rs.getInt("loaiuser_id"));
        }
        return u;
    }
    
    public User getUserBySDT(int id) throws SQLException {
        String sql = "SELECT user_id, hoten, ngaysinh, gioitinh, cmnd, taikhoan"
                + ", ngayVaoLam, email, diachi, sdt, trangthai, loaiuser_id"
                + " FROM user WHERE sdt=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        
        ResultSet rs = stm.executeQuery();
        User u = null;
        while (rs.next()) {
            u = new User();
            u.setUser_id(rs.getInt("user_id"));
            u.setHoten(rs.getString("hoten"));
            u.setNgaysinh(rs.getString("ngaysinh"));
            u.setGioitinh(rs.getString("gioitinh"));
            u.setCmnd(rs.getString("cmnd"));
            u.setTaikhoan(rs.getString("taikhoan"));
            u.setNgayvaolam(rs.getString("ngayVaoLam"));
            u.setEmail(rs.getString("email"));
            u.setDiachi(rs.getString("diachi"));
            u.setSdt(rs.getString("sdt"));
            u.setTrangthai(rs.getBoolean("trangthai"));
            u.setLoaiuser_id(rs.getInt("loaiuser_id"));
        }
        return u;
    }
    
    public User getUserByCMND(int id) throws SQLException {
        String sql = "SELECT user_id, hoten, ngaysinh, gioitinh, cmnd, taikhoan"
                + ", ngayVaoLam, email, diachi, sdt, trangthai, loaiuser_id"
                + " FROM user WHERE cmnd=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        
        ResultSet rs = stm.executeQuery();
        User u = null;
        while (rs.next()) {
            u = new User();
            u.setUser_id(rs.getInt("user_id"));
            u.setHoten(rs.getString("hoten"));
            u.setNgaysinh(rs.getString("ngaysinh"));
            u.setGioitinh(rs.getString("gioitinh"));
            u.setCmnd(rs.getString("cmnd"));
            u.setTaikhoan(rs.getString("taikhoan"));
            u.setNgayvaolam(rs.getString("ngayVaoLam"));
            u.setEmail(rs.getString("email"));
            u.setDiachi(rs.getString("diachi"));
            u.setSdt(rs.getString("sdt"));
            u.setTrangthai(rs.getBoolean("trangthai"));
            u.setLoaiuser_id(rs.getInt("loaiuser_id"));
        }
        return u;
    }
        
    public int getSLDH(int id, int loaiUser) throws SQLException{
        String sql = "SELECT count(donhang_id) as sldh\n" 
                +"From donhang\n" 
                +"Where donhang.nhanvien_id = ?";
        
        if(loaiUser == 2){
            sql ="SELECT count(hanghoa_id) as sldh"
                +" From qldvvpkcm.nhacungcap_hanghoa"
                +" Where nhanvien_id = ?";
        }
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        
        ResultSet rs = stm.executeQuery();
        int sl = 0;
        while (rs.next()) {
            sl = rs.getInt("sldh");
        }
        return sl;

    }
    
    
    public List<User> getListUserQLT(String key, String loaiTC) throws SQLException{
        String sql = "SELECT * FROM qldvvpkcm.user";
        
        if(loaiTC.equals("Mã NV")){
            sql += " WHERE user_id like concat('%', ?, '%')";
        }
        if(loaiTC.equals("Họ tên")){
            sql += " WHERE hoten like concat('%', ?, '%')";
        }
        if(loaiTC.equals("sdt")){
            sql += " WHERE sdt like concat('%', ?, '%')";
        }
        if(loaiTC.equals("Loại NV")){
            sql += " WHERE loaiuser_id like concat('%', ?, '%')";
        }
        if(loaiTC.equals("Ngày vào làm")){
            sql += " WHERE ngayVaoLam <= ?";
        }
        if(loaiTC.equals("Trạng thái")){
            sql += " WHERE trangthai like concat('%', ?, '%')";
        }
        if(loaiTC.equals("CMND")){
            sql += " WHERE cmnd like concat('%', ?, '%')";
        }
        PreparedStatement stm = this.conn.prepareStatement(sql);
        if(!(loaiTC.equals("") && key.equals("")) ){
            stm.setString(1, key);
        }
        List<User> users = new ArrayList<>();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            User u = new User();
            u.setUser_id(rs.getInt("user_id"));
            u.setHoten(rs.getString("hoten"));
            u.setNgaysinh(rs.getString("ngaysinh"));
            u.setGioitinh(rs.getString("gioitinh"));
            u.setCmnd(rs.getString("cmnd"));
            u.setTaikhoan(rs.getString("taikhoan"));
            u.setNgayvaolam(rs.getString("ngayVaoLam"));
            u.setEmail(rs.getString("email"));
            u.setDiachi(rs.getString("diachi"));
            u.setSdt(rs.getString("sdt"));
            u.setTrangthai(rs.getBoolean("trangthai"));
            u.setLoaiuser_id(rs.getInt("loaiuser_id"));
            
            users.add(u);
        }
        return users;
    }
    
    public boolean updateUser(User u) throws SQLException{
        String sql ="UPDATE qldvvpkcm.user Set hoten = ?, ngaysinh = ?"
                + ", gioitinh = ?, cmnd = ?, taikhoan = ?,\n" 
                +"ngayVaoLam = ?, email = ?, diachi = ?"
                + ", sdt = ?, trangthai = ?, loaiuser_id = ?\n" 
                + "WHERE user_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, u.getHoten());
        stm.setString(2, u.getNgaysinh());
        stm.setString(3, u.getGioitinh());
        stm.setString(4, u.getCmnd());
        stm.setString(5, u.getTaikhoan());
        stm.setString(6, u.getNgayvaolam());
        stm.setString(7, u.getEmail());
        stm.setString(8, u.getDiachi());
        stm.setString(9, u.getSdt());
        stm.setBoolean(10, u.isTrangthai());
        stm.setInt(11, u.getLoaiuser_id());
        stm.setInt(12, u.getUser_id());
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean checkTaiKhoan(String tenTK) throws SQLException{
        String sql ="SELECT taikhoan FROM qldvvpkcm.user\n" +
                    "WHERE taikhoan = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, tenTK);
        
        String kq = "";
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            kq = rs.getString("taikhoan");
        }
        
        return (kq.equals(""));
    }
    
        
    public boolean checkCMND(String CMND) throws SQLException{
        String sql ="SELECT cmnd FROM qldvvpkcm.user\n" +
                    "WHERE cmnd = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, CMND);
        
        String kq = "";
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            kq = rs.getString("cmnd");
        }
        
        return (kq.equals(""));
    }
    
    public boolean checkSDT(String SDT) throws SQLException{
        String sql ="SELECT sdt FROM qldvvpkcm.user\n" +
                    "WHERE sdt = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, SDT);
        
        String kq = "";
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            kq = rs.getString("sdt");
        }
        
        return (kq.equals(""));
    }
    
    public boolean themUser(User u) throws SQLException{
        String sql ="INSERT INTO qldvvpkcm.user(hoten, ngaysinh, gioitinh, cmnd, taikhoan"
                    + ", matkhau, ngayVaoLam, email, diachi, sdt,trangthai, loaiuser_id)\n" 
                +"VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, u.getHoten());
        stm.setString(2, u.getNgaysinh());
        stm.setString(3, u.getGioitinh());
        stm.setString(4, u.getCmnd());
        stm.setString(5, u.getTaikhoan());
        stm.setString(6, u.getMatkhau());
        stm.setString(7, u.getNgayvaolam());
        stm.setString(8, u.getEmail());
        stm.setString(9, u.getDiachi());
        stm.setString(10, u.getSdt());
        stm.setBoolean(11, u.isTrangthai());
        stm.setInt(12, u.getLoaiuser_id());
        int row = stm.executeUpdate();
        
        return row > 0;
    }
}