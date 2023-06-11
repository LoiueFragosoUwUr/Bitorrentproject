/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author iarog
 */
public interface ClientRMI extends Remote{
    
    public byte[] transferFile(String fileName, int fragment) throws RemoteException;
    public boolean ackConnection() throws RemoteException;
    
}
