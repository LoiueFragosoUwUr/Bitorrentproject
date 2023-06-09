/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.averiguar;
import java.net.InetAddress;  
import java.net.UnknownHostException;  
import java.net.InetAddress;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.FileWriter;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.ObjectOutputStream;
import java.io.OutputStream;  
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author biosh
 */
public class FirstStepTracker extends UnicastRemoteObject  implements FileTransfer,InformsItstheTracker{
    int port=1099;
    static List<String> receivedIPs= new ArrayList<String>();
    public String ImtheTracker() {
              return "ImtheTracker";
    }   
    @Override
    public void SharesIP(String IP, String port) throws IOException{
    
        InetAddress hostip = InetAddress.getLocalHost();
        String hostaddress= hostip.getHostAddress();
        IP=hostaddress;
        port=port;
                System.out.println("Received IP addresses");
                if (receivedIPs.contains(hostaddress)!=true) {
                    receivedIPs.add(hostaddress);
                    for (int i = 0; i < receivedIPs.size(); i++) {
                        System.out.println(receivedIPs.get(i));
                    }
                }
                printslist();
    }
    public FirstStepTracker() throws RemoteException{super();}
//    @Override
    public /*String*/void SendsIP (String IP)throws RemoteException,IOException{
    
        System.out.println(IP);
    }
    @Override
    public String transferFile(byte[] data, String filename)throws RemoteException,IOException{
        InetAddress hostip = InetAddress.getLocalHost();
        String hostaddress= hostip.getHostAddress();
        if (filename.equals(hostaddress)==true) {
            System.out.println("ドキュメントは常にアイピーアドレスを含めています。");
        }else if(filename.equals(hostaddress)!=true){
             filename.equals(0);
            
        try (FileOutputStream fos= new FileOutputStream(filename,true)){
           fos.write(data);
           
        }catch(IOException e){
            System.err.println("Error writing file: " + e.getMessage());
        }
                }
        return hostaddress;
        }
    public static void main(String[] args) throws RemoteException, Exception{
//        String srvrnm =args[0];
          String IPrecibida;
        try {
            FirstStepTracker server= new FirstStepTracker();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("FileTransfer", server);
            registry.bind("InformsItstheTracker",server);
            registry.bind("ReceiveMessageInterface",server);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
        FileWriter myWriter = new FileWriter("C:\\Users\\biosh\\Documents\\NetBeansProjects\\averiguar\\IP.txt");
        System.out.println("borra");
        myWriter.equals(0);
        myWriter.close();
         
    }

    public void printslist(){
        if (receivedIPs.isEmpty()!=true) {
        System.out.println("inside main");
        for (int i = 0; i < receivedIPs.size(); i++) {
            System.out.println(receivedIPs.get(i));
        }
            
        }
    
    }
}

