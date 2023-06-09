/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.averiguar;

/**
 *
 * @author biosh
 */
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileTransfer extends Remote {
    public String transferFile(byte[] data,String fileName) throws RemoteException,IOException;
//1
//    public void transfer throws RemoteException;
    
}
