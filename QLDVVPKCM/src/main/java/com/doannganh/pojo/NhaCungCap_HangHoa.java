/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.pojo;


public class NhaCungCap_HangHoa {
    private int nhacungcap_id;
    private int hanghoa_id;
    private int soLuong;
    private String ngaynhap;
    private String gianhap;

    /**
     * @return the nhacungcap_id
     */
    public int getNhacungcap_id() {
        return nhacungcap_id;
    }

    /**
     * @param nhacungcap_id the nhacungcap_id to set
     */
    public void setNhacungcap_id(int nhacungcap_id) {
        this.nhacungcap_id = nhacungcap_id;
    }

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
     * @return the soLuong
     */
    public int getSoLuong() {
        return soLuong;
    }

    /**
     * @param soLuong the soLuong to set
     */
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    /**
     * @return the ngaynhap
     */
    public String getNgaynhap() {
        return ngaynhap;
    }

    /**
     * @param ngaynhap the ngaynhap to set
     */
    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    /**
     * @return the gianhap
     */
    public String getGianhap() {
        return gianhap;
    }

    /**
     * @param gianhap the gianhap to set
     */
    public void setGianhap(String gianhap) {
        this.gianhap = gianhap;
    }
}
