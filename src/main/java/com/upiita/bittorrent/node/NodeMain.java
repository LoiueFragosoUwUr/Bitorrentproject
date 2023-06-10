/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node;

import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.node.controller.ClientManager;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author iarog
 */
public class NodeMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {
        char opt;
        Scanner scanner = new Scanner(System.in);
        ClientManager clientManager;
        do{
            System.out.println("Bienvenido a BitTorrentUPIITA");
            System.out.println("¿Quieres compartir tus recursos?");
            System.out.println("Si \t s");
            System.out.println("No \t ingrese cualquier tecla");
            
            opt = scanner.next().charAt(0);
            
            if(opt == 's'){
                clientManager = new ClientManager();
                List<FileInformation> files = clientManager.getFilesToShare();
                Nodo node = clientManager.createNodeToShare(files);
                
            }
            else{
                
            }
        }
        while(opt != 'd');
    }
    
}
