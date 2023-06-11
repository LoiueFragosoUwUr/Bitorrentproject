/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upiita.bittorrent.server.rmi.controller;

import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.node.rmi.ClientRMI;
import com.upiita.bittorrent.server.dao.DAO;
import com.upiita.bittorrent.server.dao.file.FileNodoDAO;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author biosh
 */
public class ManagementConn extends TimerTask{

    public ManagementConn() {
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(this, 1000,5000);
    }
    @Override
    public void run (){
    manageSeeders();
    
    
    }
    
    private void manageSeeders(){
        DAO fileNodoDAO= new FileNodoDAO();
        List<Nodo> nodeList= fileNodoDAO.list();
        
        for (int i = 0; i < nodeList.size(); i++) {
            try {
            Registry registry = LocateRegistry.getRegistry(nodeList.get(i).getIp(),nodeList.get(i).getPort());
            ClientRMI clienteRMI = (ClientRMI) registry.lookup("FileTransfers");
            if (clienteRMI.ackConnection()) {
                System.out.println("Continua conectado||still connected");
                
            }
            } catch (Exception e) {
                fileNodoDAO.delete(nodeList.get(i).getIp());
            }
            
        }
    }
}
