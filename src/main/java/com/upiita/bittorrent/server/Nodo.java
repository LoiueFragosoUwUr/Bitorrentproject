package com.upiita.bittorrent.server;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.Serializable;
import java.util.List;

public class Nodo implements Serializable{

    private String ip;
    private int port;
    private String nameFile;
    private List<Integer> listaInt;
    /*THIS CLASS DEFINES what is a node how is it composed with the aim of 
    storage their properties*/
    public Nodo (){

    }

    public Nodo (String ip, int port, String nameFile, List<Integer> listaInt){ /* Name file, percentaje extension*/
        this.ip = ip;
        this.port = port;
        this.nameFile = nameFile;
        this.listaInt = listaInt;
    }

    public String getIp(){
        return this.ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public int getPort(){
        return this.port;
    }

    public void setPort(int port){
        this.port = port;
    }

    public String getNameFile(){
        return this.nameFile;
    }

    public void setNameFile(String nameFile){
        this.nameFile = nameFile;
    }

    public void setListaInt(List<Integer> listaInt) {
        this.listaInt = listaInt;
    }

    public List<Integer> getListaInt() {
        return listaInt;
    }

    @Override
    public String toString(){
        return "ip = " + this.ip + ", port = " + this.port + ", nameFile = " + this.nameFile;
    }   

}
