/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.pojo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class HangHoa {
    private int hanghoa_id;
    private String tenhanghoa;
    private String thuonghieu;
    private BigDecimal soluongtrongkho;
    private BigDecimal gianhap;
    private BigDecimal giaban;
    private Date ngaysanxuat;
    private Date ngayhethan;
    private String hinhanh;
    private String loaihanghoa_id;

    /**
     * @return the hanghoa_id
     */
    public int getHanghoa_id() {
        return hanghoa_id;
    }

    /**
     * @param hanghoa_id the hanghoa_id to set
     */
    public void setHanghoa_id(int hanghoa_id) {
        this.hanghoa_id = hanghoa_id;
    }

    /**
     * @return the tenhanghoa
     */
    public String getTenhanghoa() {
        return tenhanghoa;
    }

    /**
     * @param tenhanghoa the tenhanghoa to set
     */
    public void setTenhanghoa(String tenhanghoa) {
        this.tenhanghoa = tenhanghoa;
    }

    /**
     * @return the thuonghieu
     */
    public String getThuonghieu() {
        return thuonghieu;
    }

    /**
     * @param thuonghieu the thuonghieu to set
     */
    public void setThuonghieu(String thuonghieu) {
        this.thuonghieu = thuonghieu;
    }

    /**
     * @return the soluongtrongkho
     */
    public BigDecimal getSoluongtrongkho() {
        return soluongtrongkho;
    }

    /**
     * @param soluongtrongkho the soluongtrongkho to set
     */
    public void setSoluongtrongkho(BigDecimal soluongtrongkho) {
        this.soluongtrongkho = soluongtrongkho;
    }

    /**
     * @return the gianhap
     */
    public BigDecimal getGianhap() {
        return gianhap;
    }

    /**
     * @param gianhap the gianhap to set
     */
    public void setGianhap(BigDecimal gianhap) {
        this.gianhap = gianhap;
    }

    /**
     * @return the giaban
     */
    public BigDecimal getGiaban() {
        return giaban;
    }

    /**
     * @param giaban the giaban to set
     */
    public void setGiaban(BigDecimal giaban) {
        this.giaban = giaban;
    }

    /**
     * @return the ngaysanxuat
     */
    public Date getNgaysanxuat() {
        return ngaysanxuat;
    }

    /**
     * @param ngaysanxuat the ngaysanxuat to set
     */
    public void setNgaysanxuat(Date ngaysanxuat) {
        this.ngaysanxuat = ngaysanxuat;
    }

    /**
     * @return the ngayhethan
     */
    public Date getNgayhethan() {
        return ngayhethan;
    }

    /**
     * @param ngayhethan the ngayhethan to set
     */
    public void setNgayhethan(Date ngayhethan) {
        this.ngayhethan = ngayhethan;
    }

    /**
     * @return the hinhanh
     */
    public String getHinhanh() {
        return hinhanh;
    }

    /**
     * @param hinhanh the hinhanh to set
     */
    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    /**
     * @return the loaihanghoa_id
     */
    public String getLoaihanghoa_id() {
        return loaihanghoa_id;
    }

    /**
     * @param loaihanghoa_id the loaihanghoa_id to set
     */
    public void setLoaihanghoa_id(String loaihanghoa_id) {
        this.loaihanghoa_id = loaihanghoa_id;
    }
}
