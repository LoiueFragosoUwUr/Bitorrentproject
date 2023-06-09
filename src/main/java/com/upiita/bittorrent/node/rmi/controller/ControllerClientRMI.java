/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.rmi.controller;

import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.node.controller.ClientManager;
import com.upiita.bittorrent.node.rmi.ClientRMI;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author iarog
 */
public class ControllerClientRMI extends UnicastRemoteObject implements ClientRMI {
    Properties props;
    public ControllerClientRMI() throws RemoteException{
         super();
        props = new Properties();
        try(FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\bittorrent.properties")){
            props.load(fis);
        }
        catch(Exception ex){
            System.exit(1);
        }
       
    }

    @Override
    public byte[] transferFile(String fileName, int fragment, String ip) throws RemoteException{
        
        System.out.println("El nodo: " + ip + " ha solicitado el fragmento: " + fragment + "del archivo: " + fileName);
        
        byte [] data = null;
        ClientManager clientManager = new ClientManager();
        List<FileInformation> listFiles = clientManager.getFilesToShare();
        FileInformation file = null;
        for(FileInformation f: listFiles){
            if(f.getNameFile().equals(fileName)){
                if(f.getFragments().contains(fragment)){
                    file = f;
                    break;
                }
            }
        }
        
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream( System.getProperty("user.dir") + props.getProperty("staticDirectoryClient")
                + props.getProperty("directoryFragments") + "\\" + ClientManager.accoplishFragment(fileName, fragment)))){
            byte [] buffer = new byte[(int) Math.ceil(file.getSize()/(Double.parseDouble(props.getProperty("sizePackage"))))];
            
            int bytesRead = 0;
            
            bytesRead = bis.read(buffer);
            data = buffer;
            bis.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return data;
        
    }

    @Override
    public boolean ackConnection() throws RemoteException{
        
        System.out.println("El tracker solicita confirmación de conexion");
        return true;
    }
    
}
