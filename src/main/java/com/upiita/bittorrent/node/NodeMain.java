/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node;

import com.upiita.bittorrent.dao.TorrentDAO;
import com.upiita.bittorrent.dao.file.FileTorrentDAO;
import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.node.controller.ClientManager;
import com.upiita.bittorrent.node.rmi.main.DownloadClientRMI;
import com.upiita.bittorrent.node.rmi.main.ServerClientRMI;
import com.upiita.bittorrent.node.rmi.main.Torrent;
import com.upiita.bittorrent.server.rmi.InformsItstheTracker;
import java.io.FileInputStream;
import java.net.InetAddress;
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
     * 
     * 
     */
    
    private static ServerClientRMI server = null;
    private static Torrent torrent;
    private static String estado = "Leecher";
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
            
            
            torrent = new Torrent();
            System.out.println("Bienvenido a BitTorrentUPIITA");
            
            do{
                System.out.println("Estado: " + estado);
                System.out.println("¿Que deseas?");
                System.out.println("a. Ver descargas");
                System.out.println("b. Descargar un archivo");
                System.out.println("c. Compartir recursos");
                System.out.println("d. Salir");
                opt = scanner.nextLine().charAt(0);

                if(opt == 'a'){
                        
                    printDownloadedFiles();
                    
                }
                else if(opt == 'b'){
                    try{
                        Registry registry = LocateRegistry.getRegistry(props.getProperty("ipTracker"), Integer.parseInt(props.getProperty("portTracker")));
                        InformsItstheTracker informsItsTracker = (InformsItstheTracker) registry.lookup(props.getProperty("lookupTracker"));
                        InetAddress hostip = InetAddress.getLocalHost();
                        String hostaddress = hostip.getHostAddress();
                        List<FileInformation> listFiles  = informsItsTracker.ListFiles(hostaddress);
                        printList(listFiles);
                        
                        System.out.println("Escribe el nombre de un archivo");
                        System.out.println("Escribe \"Cancelar\" para cancelar");
                        String nombre = scanner.nextLine();
                        
                        if(!nombre.equalsIgnoreCase("Cancelar")){
                            List<Nodo> nodos = informsItsTracker.SendsIP(nombre, hostaddress);
                        
                            if(nodos.isEmpty()){
                                System.out.println("No existe el archivo solicitado, revisa el nombre");
                            }
                            else{
                                TorrentDAO torrentDao = new FileTorrentDAO(nodos.get(0).getFiles().get(0).getNameFile() + props.getProperty("extensionTorrent"));
                                torrentDao.saveFile(nodos);
                            }
                        
                        }
                        
                    }
                    catch(Exception ex){
                        
                    }
                    
                    
                }else if(opt == 'c'){
                    System.out.println("¿Quieres compartir tus recursos?");
                    System.out.println("Si \t s");
                    System.out.println("No \t ingrese cualquier tecla");

                    opt = scanner.nextLine().charAt(0);

                    if(opt == 's'){
                        estado = "Seeder";
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
                        if(server == null){
                            server = new ServerClientRMI(Integer.parseInt(props.getProperty("portClient")), props.getProperty("lookupClient"));
                            
                        }
                        server.startServer();
                        

                    }
                    else{
                        estado = "Leecher";
                        if(server != null){
                            server.stopRMI();
                            
                        }
                    }
                }
            }
            while(opt != 'd');
           
        }
        while(opt != 'd');
        
        try{
            torrent.cancel();
            if(server != null){
                server.stopRMI();
            }
            
            System.exit(0);
            
        }
        catch(Exception ex){
            
        }
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
