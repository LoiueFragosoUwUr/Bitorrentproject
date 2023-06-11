package com.upiita.bittorrent.server;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.server.rmi.InformsItstheTracker;
import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.dao.DAO;
import com.upiita.bittorrent.dao.file.FileNodoDAO;
import com.upiita.bittorrent.server.rmi.controller.ManagementConn;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author biosh
 */
public class FirstStepTracker extends UnicastRemoteObject implements  InformsItstheTracker {

    int port = 1099;
    static List<String> receivedIPs = new ArrayList<String>();

    public String ImtheTracker() {
        return "ImtheTracker";
    }

    @Override
    public void SharesIP(Nodo node) throws IOException {

        DAO fileNodoDAO = new FileNodoDAO();
        fileNodoDAO.create(node);

    }

    public FirstStepTracker() throws RemoteException {
        super();
    }
//    @Override

    public ArrayList<Nodo> SendsIP(String fileName) throws RemoteException, IOException {
        DAO fileNodoDAO = new FileNodoDAO();
        ArrayList<Nodo> nodes = (ArrayList<Nodo>) fileNodoDAO.list();
        for (int i = 0; i < nodes.size(); i++) {
            List<FileInformation> fileInformation = new ArrayList<>();
            
            List<FileInformation> files = nodes.get(i).getFiles();
            for(FileInformation file: files){
                if (file.getNameFile().equals(fileName)) {
                    fileInformation.add(file);
                    break;
                    
                }
 
            }
            
            if (fileInformation.isEmpty()) {
                nodes.remove(i);
                i--;
            }else{
                
            Nodo nuevoNodo =nodes.get(i);
            nuevoNodo.setFiles(fileInformation);
            nodes.set(i, nuevoNodo);
            
            
            }
        }
        
        return nodes;
    }

    
    public static void main(String[] args) throws RemoteException, Exception {
//        String srvrnm =args[0];
        String IPrecibida;
        try {
            FirstStepTracker server = new FirstStepTracker();
            ManagementConn nuevo = new ManagementConn();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("InformsItstheTracker", server);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
        

    }

    @Override
    public ArrayList<FileInformation> ListFiles() {
        DAO nodoDAO = new FileNodoDAO();
        List<Nodo> nodos = nodoDAO.list();
        ArrayList<FileInformation> files = new ArrayList<>();
        for (Nodo nodo : nodos) {

            for (FileInformation file : nodo.getFiles()) {
                boolean existe = false;
                for (FileInformation fileaux : files) {
                    if (fileaux.getNameFile().equals(file.getNameFile())) {
                        existe = true;
                        break;

                    }

                }

                if (!existe) {
                    files.add(file);
                }
            }

        }

        return files;
    }
}
