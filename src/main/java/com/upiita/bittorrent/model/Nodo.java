package com.upiita.bittorrent.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Nodo implements Serializable{

    private String ip;
    private int port;
    private List<FileInformation> files;

    public Nodo(String ip, int port, List<FileInformation> files) {
        this.ip = ip;
        this.port = port;
        this.files = files;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<FileInformation> getFiles() {
        return files;
    }

    public void setFiles(List<FileInformation> files) {
        this.files = files;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.ip);
        hash = 17 * hash + this.port;
        hash = 17 * hash + Objects.hashCode(this.files);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nodo other = (Nodo) obj;
        if (this.port != other.port) {
            return false;
        }
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (!Objects.equals(this.files, other.files)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nodo{" + "ip=" + ip + ", port=" + port + ", files=" + files + '}';
    }
    
}
