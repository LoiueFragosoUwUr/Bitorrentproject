/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node;

import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.node.controller.ClientManager;
import com.upiita.bittorrent.node.rmi.main.ServerClientRMI;
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
            
            opt = scanner.nextLine().charAt(0);
            
            if(opt == 's'){
                clientManager = new ClientManager();
                List<FileInformation> files = clientManager.getFilesToShare();
                Nodo node = clientManager.createNodeToShare(files);
                ServerClientRMI server = new ServerClientRMI(6000, "FileTransfers");
                server.start();
                
            }
            
            do{
                System.out.println("¿Que deseas?");
                System.out.println("a. Ver descargas");
                System.out.println("b. Descargar un archivo");
                System.out.println("d. Salir");
                opt = scanner.nextLine().charAt(0);

                if(opt == 'a'){
                        
                    printDownloadedFiles();
                    
                }
                else if(opt == 'b'){
                    
                }
            }
            while(opt != 'd');
           
        }
        while(opt != 'd');
    }
    
    
    public static void printDownloadedFiles(){
        ClientManager clientManager = new ClientManager();
        List<FileInformation> files = clientManager.getFilesToShare();
        
        for(FileInformation file: files){
            System.out.println("Nombre\tTamanio\tPorcentaje");
            System.out.println(file.getNameFile() + "\t" + file.getSize() +" bytes\t" + file.getPercentage() + "%");
        }
    }
    
    
    
}
