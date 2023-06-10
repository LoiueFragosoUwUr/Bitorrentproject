/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.upiita.bittorrent.server.dao;

import java.util.List;

/**
 *
 * @author biosh
 */
public interface DAO<Object> {
    public void create(Object object);
    public void update(Object object);
    public void delete(String s);
    public List<Object> list() ;
    
}
