/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.model;

import java.util.Objects;

/**
 *
 * @author iarog
 */
public class FileInformation {
    
    private String nameFile;
    private double size;
    private double percentage;
    private int fragments;

    public FileInformation(String nameFile, double size, double percentage, int fragments) {
        this.nameFile = nameFile;
        this.size = size;
        this.percentage = percentage;
        this.fragments = fragments;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getFragments() {
        return fragments;
    }

    public void setFragments(int fragments) {
        this.fragments = fragments;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.nameFile);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.size) ^ (Double.doubleToLongBits(this.size) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.percentage) ^ (Double.doubleToLongBits(this.percentage) >>> 32));
        hash = 53 * hash + this.fragments;
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
        final FileInformation other = (FileInformation) obj;
        if (Double.doubleToLongBits(this.size) != Double.doubleToLongBits(other.size)) {
            return false;
        }
        if (Double.doubleToLongBits(this.percentage) != Double.doubleToLongBits(other.percentage)) {
            return false;
        }
        if (this.fragments != other.fragments) {
            return false;
        }
        if (!Objects.equals(this.nameFile, other.nameFile)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FileInformation{" + "nameFile=" + nameFile + ", size=" + size + ", percentage=" + percentage + ", fragments=" + fragments + '}';
    }

}
