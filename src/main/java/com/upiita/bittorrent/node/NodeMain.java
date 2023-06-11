/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node;

import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.node.controller.ClientManager;
import com.upiita.bittorrent.node.rmi.main.DownloadClientRMI;
import com.upiita.bittorrent.node.rmi.main.ServerClientRMI;
import com.upiita.bittorrent.server.rmi.InformsItstheTracker;
import java.io.FileInputStream;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
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
        
        Properties props = new Properties();
        try(FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\bittorrent.properties")){
            props.load(fis);
        }
        catch(Exception ex){
            System.exit(1);
        }
        
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
                try{
                    System.setProperty("sun.rmi.transport.connectionTimeout", "40000");
                    Registry registry = LocateRegistry.getRegistry(props.getProperty("ipTracker"), Integer.parseInt(props.getProperty("portTracker")));
                    
                    InformsItstheTracker informsItsTracker = (InformsItstheTracker) registry.lookup(props.getProperty("lookupTracker"));
                    informsItsTracker.SharesIP(node);
                    
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
                ServerClientRMI server = new ServerClientRMI(Integer.parseInt(props.getProperty("portClient")), props.getProperty("lookupClient"));
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
                    try{
                        Registry registry = LocateRegistry.getRegistry(props.getProperty("ipTracker"), Integer.parseInt(props.getProperty("portTracker")));
                        InformsItstheTracker informsItsTracker = (InformsItstheTracker) registry.lookup(props.getProperty("lookupTracker"));
                        List<FileInformation> listFiles  = informsItsTracker.ListFiles();
                        printList(listFiles);
                        
                        System.out.println("Escribe el nombre de un archivo");
                        String nombre = scanner.nextLine();
                        List<Nodo> nodos = informsItsTracker.SendsIP(nombre);
                        
                        for(Nodo nodo: nodos){
                            DownloadClientRMI download = new DownloadClientRMI(nodo);
                            download.start();
                        }
                        
                    }
                    catch(Exception ex){
                        
                    }
                    
                    
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
    
    public static void printList(List<FileInformation> files){
        for(FileInformation file: files){
            System.out.println("Nombre\tTamanio");
            System.out.println(file.getNameFile() + "\t" + file.getSize() +" bytes");
        }
    }
    
    
    
}
