/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.rmi.main;

import com.upiita.bittorrent.node.rmi.controller.ControllerClientRMI;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iarog
 */
public class ServerClientRMI extends Thread{
    
    private final int port;
    private final String rmiName;

    public ServerClientRMI(int port, String rmiName) {
        super();
        this.port = port;
        this.rmiName = rmiName;
    }
    @Override
    public void run(){
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(port);
            registry.bind(rmiName, new ControllerClientRMI());
            System.out.println("Los recursos ya se estan compartiendo");
        } catch (RemoteException ex) {
            Logger.getLogger(ServerClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(ServerClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }  
    
    }
    
}
