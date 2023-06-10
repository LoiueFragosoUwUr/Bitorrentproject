/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.rmi.controller;

import com.upiita.bittorrent.node.rmi.ClientRMI;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author iarog
 */
public class ControllerClientRMI extends UnicastRemoteObject implements ClientRMI {

    @Override
    public boolean transferFile(byte[] data, String fileName, int id, int chunkSize) {
        //Luego se programa jijijij
        return false;
    }

    @Override
    public boolean ackConnection() {
        return true;
    }
    
}
