package com.upiita.bittorrent.server.rmi;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


/**
 *
 * @author biosh
 */
import java.rmi.*;

public interface ReceiveMessageInterface extends Remote

{

    void receiveMessage(String x) throws RemoteException;

}
