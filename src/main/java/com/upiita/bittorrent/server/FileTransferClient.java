package com.upiita.bittorrent.server;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author biosh
 */
import com.upiita.bittorrent.server.rmi.InformsItstheTracker;
import com.upiita.bittorrent.server.rmi.FileTransfer;
import java.io.*;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.File;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;

public class FileTransferClient {
    static Registry  RegistryMessage;
    public FileTransferClient(){}
    static Boolean itsnecessary=false,alreadysended=false;
    
    public static void main(String[] args) throws RemoteException, NotBoundException, IOException {
        InetAddress hostip = InetAddress.getLocalHost();
        String hostaddress= hostip.getHostAddress();
        InformsItstheTracker rmiServer;
        String filename = "C:\\Users\\biosh\\Desktop\\lunes_sistemas\\practicas-SD-2022\\rmi fragmentador archivos server\\archivotransferir.txt";
        File file = new File(filename);
        long fileSize = file.length();
        boolean decidestheConnection=false;
        long chunkSize = fileSize / 10;
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        FileTransfer stub = (FileTransfer) registry.lookup("FileTransfer");
        System.out.println("処理中");
        FileTransferClient object =new FileTransferClient();
        itsnecessary=object.creates_and_writes();
        String filewithIP = "C:\\Users\\biosh\\Desktop\\IP.txt";
        File IPfile= new File(filewithIP);
        long fileSizeIP = IPfile.length();
        long chunkSizeIP = fileSizeIP / 10;
        InputStream input = new FileInputStream("C:\\Users\\biosh\\Desktop\\IP.txt");  
         
         DataInputStream inst = new DataInputStream(input);  
         System.out.println("処理中その二");
        //IT IS NOT NECESARY
        
           //send the document that contains the ip client's IP address
            decidestheConnection = checkstheConnection(registry,decidestheConnection);
            if (decidestheConnection==true) {
                  System.out.println("We are connected to the tracker");
                  if (itsnecessary==false&&alreadysended==false) {
                  if(IPfile.equals(hostaddress)!=true){
                  try(BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(IPfile))) {
                  byte[] bufferIP= new byte[(int) chunkSizeIP];
                  int bytesRead2 = 0;
                  while ((bytesRead2 = bis2.read(bufferIP)) != -1) {
                  stub.transferFile(bufferIP, IPfile.getName());
                  alreadysended=true;
                }
                }
            }} 
            }else if(decidestheConnection==false){
                System.out.println("we are not connected to the tracker");
            }
        
    }
    
        public boolean creates_and_writes(){

            try {
                File myObj = new File("C:\\Users\\biosh\\Desktop\\IP.txt");
                
              if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                InetAddress hostip = InetAddress.getLocalHost();
                String hostaddress= hostip.getHostAddress();
                FileWriter myWriter = new FileWriter("C:\\Users\\biosh\\Desktop\\IP.txt");
                myWriter.write(hostaddress);
                myWriter.close();
              } else {
                InetAddress hostip = InetAddress.getLocalHost();
                String hostaddress= hostip.getHostAddress();
                System.out.println("File already exists.");
                //the program stars to write the IP address
                FileWriter myWriter = new FileWriter("C:\\Users\\biosh\\Desktop\\IP.txt");
                if(myObj.toString().equals(hostaddress)!=true){
                    System.out.println("el archivo es incorrecto");
                myWriter.equals(0);
                myWriter.write(hostaddress);
                myWriter.close();
                itsnecessary=false;
                
                }else if(myObj.toString().equals(hostaddress)==true){
                    System.out.println("el archivo es iwal");
                    myWriter.close();
                    itsnecessary=true;
                }
              }
            } catch (IOException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
            return itsnecessary;
          }
            /*function that informs if the node is connected to the tracker*/
            public static boolean checkstheConnection(Registry registryParam, boolean decision )throws RemoteException{
                try {
                    System.out.println("entra en checks the connection");
                    InformsItstheTracker stub = (InformsItstheTracker) registryParam.lookup("InformsItstheTracker");
                    String response = stub.ImtheTracker();
                    String receivedIP="",receivedport="";
                    stub.SharesIP(receivedIP,receivedport);
                    System.out.println(" response :"+response);
                    if (response.equals("ImtheTracker")==true) {
                       decision=true;    
                    }else{
                       decision=false;
                    }
                    
                } catch (Exception e) {
                    System.out.println("Client exception: "+e.toString());
                    e.printStackTrace();
                }
            return decision;
            }
         
            

}

