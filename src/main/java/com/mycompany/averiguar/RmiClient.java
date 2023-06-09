/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.averiguar;

/**
 *
 * @author biosh
 */
import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;
import java.io.*;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.File;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;

public class RmiClient {
    static public void main(String args[]) throws RemoteException, NotBoundException {
        ReceiveMessageInterface rmiServer;
//        Registry registry ;
    //FileTransfer stub = (FileTransfer) registry.lookup("FileTransfer");
        String serverAddress = "26.53.141.25";
        String serverPort = "1099";
        String text = "dasdwe";
        System.out.println("sending " + text + " to " + serverAddress + ":" + serverPort);
        try {
            // get the �gregistry�h
            Registry registry  = LocateRegistry.getRegistry("26.53.141.25",1099);
            System.out.println(" solaaaaaaaaa cero");
// look up the remote object
            rmiServer  = (ReceiveMessageInterface) (registry.lookup("rmiServer"));
            
            System.out.println("olaaaaaaaaa");
            // call the remote method
            rmiServer.receiveMessage(text);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
