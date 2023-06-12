/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.rmi.main;

import com.upiita.bittorrent.node.rmi.ClientRMI;
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
public class ServerClientRMI {
    
    private final int port;
    private final String rmiName;
    private Registry registry;
    private ClientRMI clientRMI;

    public ServerClientRMI(int port, String rmiName) {
        super();
        this.port = port;
        this.rmiName = rmiName;
        try{
             this.clientRMI =  new ControllerClientRMI();
            registry = LocateRegistry.createRegistry(port);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    public void stopRMI(){
        try{
            registry.unbind(rmiName);
            System.out.println("Los recursos ya no se estan compartiendo");
        }
        catch(Exception x){
            x.printStackTrace();
        }
        
    }
    
    public void startServer(){
        try {
            
            registry.bind(rmiName,this.clientRMI);
            System.out.println("Los recursos ya se estan compartiendo");
        } catch (RemoteException ex) {
            Logger.getLogger(ServerClientRMI.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(ServerClientRMI.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    
    }
    
}
