/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.rmi.main;

import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.node.controller.ClientManager;
import com.upiita.bittorrent.node.rmi.ClientRMI;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

/**
 *
 * @author iarog
 */
public class DownloadClientRMI extends Thread{
    
    private final Nodo node;
    Properties props;
    
    public DownloadClientRMI (Nodo node){
        super();
        this.node = node;
        props = new Properties();
        try(FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\bittorrent.properties")){
            props.load(fis);
        }
        catch(Exception ex){
            System.exit(1);
        }
    }
    
    private void startDownload() {
        try{
            ClientManager clientManager = new ClientManager();
            Registry registry = LocateRegistry.getRegistry(node.getIp(), node.getPort());
            
            ClientRMI clientRMI = (ClientRMI) registry.lookup(props.getProperty("lookupClient"));
            FileInformation fileInformation = node.getFiles().get(0);
            
            for(int i = 0; i < fileInformation.getFragments().size(); i++){      
               if(!clientManager.verifyFragment(fileInformation.getNameFile(), fileInformation.getFragments().get(i))){
                   byte [] buffer = clientRMI.transferFile(fileInformation.getNameFile(), fileInformation.getFragments().get(i));
                   try(FileOutputStream fos = new FileOutputStream(ClientManager.accoplishFragment(fileInformation.getNameFile(), fileInformation.getFragments().get(i)))){
                       fos.write(buffer);
                       fos.close();
                   }
                   catch(Exception ex){
                       
                   }
                   
               }
            }   
            
        }
        catch(Exception ex){
            
        }
        
    }
    
    @Override
    public void run(){
        startDownload();
    }
    
}
