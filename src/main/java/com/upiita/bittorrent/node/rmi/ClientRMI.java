/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.rmi;

import java.rmi.Remote;

/**
 *
 * @author iarog
 */
public interface ClientRMI extends Remote{
    
    public boolean transferFile(byte [] data, String fileName, int id, int chunkSize);
    public boolean ackConnection();
    
}
