package com.upiita.bittorrent.server;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


/**
 *
 * @author biosh
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface SharestheIP extends Remote{
    String SharestheIPwithTracker() throws RemoteException;
    
    
}
