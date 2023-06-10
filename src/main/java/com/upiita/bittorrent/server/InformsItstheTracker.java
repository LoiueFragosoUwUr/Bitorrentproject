package com.upiita.bittorrent.server;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


/**
 *
 * @author biosh
 */
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.*;
public interface InformsItstheTracker extends Remote{
        
        String ImtheTracker() throws RemoteException;
        void SharesIP(String ip, String port) throws RemoteException,IOException;
        /*String*/void SendsIP(String x)throws RemoteException,IOException;
               


    }


