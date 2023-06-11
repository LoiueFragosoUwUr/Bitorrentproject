package com.upiita.bittorrent.server.rmi;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


/**
 *
 * @author biosh
 */
import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.model.Nodo;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
public interface InformsItstheTracker extends Remote{
        
        String ImtheTracker() throws RemoteException;
        void SharesIP(Nodo node) throws RemoteException,IOException;
        List <Nodo> SendsIP(String fileName)throws RemoteException,IOException;
        List <FileInformation> ListFiles() throws RemoteException;
               


    }


