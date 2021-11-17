/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.pojo;

/**
 *
 * @author Admin
 */
public class LoaiHangHoa {
    private int loaihanghoa_id;
    private String tenloai;
    private int sldb;
    private int sldn;
    private int sltk;
    
    @Override
    public String toString() {
        return this.tenloai;
    }

    /**
     * @return the loaihanghoa_id
     */
    public int getLoaihanghoa_id() {
        return loaihanghoa_id;
    }

    /**
     * @param loaihanghoa_id the loaihanghoa_id to set
     */
    public void setLoaihanghoa_id(int loaihanghoa_id) {
        this.loaihanghoa_id = loaihanghoa_id;
    }

    /**
     * @return the tenloai
     */
    public String getTenloai() {
        return tenloai;
    }

    /**
     * @param tenloai the tenloai to set
     */
    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    /**
     * @return the sldb
     */
    public int getSldb() {
        return sldb;
    }

    /**
     * @param sldb the sldb to set
     */
    public void setSldb(int sldb) {
        this.sldb = sldb;
    }

    /**
     * @return the sldn
     */
    public int getSldn() {
        return sldn;
    }

    /**
     * @param sldn the sldn to set
     */
    public void setSldn(int sldn) {
        this.sldn = sldn;
    }

    /**
     * @return the sltk
     */
    public int getSltk() {
        return sltk;
    }

    /**
     * @param sltk the sltk to set
     */
    public void setSltk(int sltk) {
        this.sltk = sltk;
    }

    
}
