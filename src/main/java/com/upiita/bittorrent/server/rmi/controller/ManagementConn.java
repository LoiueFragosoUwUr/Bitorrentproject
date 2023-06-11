/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upiita.bittorrent.server.rmi.controller;

import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.node.rmi.ClientRMI;
import com.upiita.bittorrent.dao.DAO;
import com.upiita.bittorrent.dao.file.FileNodoDAO;
import java.io.FileInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author biosh
 */
public class ManagementConn extends TimerTask {
    Properties props;
    public ManagementConn() {
        props = new Properties();
        try(FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\bittorrent.properties")){
            props.load(fis);
        }
        catch(Exception ex){
            System.exit(1);
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this, Integer.parseInt(props.getProperty("initCheckup")), Integer.parseInt(props.getProperty("checkupInterval")));
    }

    @Override
    public void run() {
        manageSeeders();

    }

    private void manageSeeders() {
        DAO fileNodoDAO = new FileNodoDAO();
        List<Nodo> nodeList = fileNodoDAO.list();

        for (int i = 0; i < nodeList.size(); i++) {
            try {
                Registry registry = LocateRegistry.getRegistry(nodeList.get(i).getIp(), nodeList.get(i).getPort());
                ClientRMI clienteRMI = (ClientRMI) registry.lookup("FileTransfers");
                if (clienteRMI.ackConnection()) {
                    System.out.println("Continua conectado||still connected");

                }
            } catch (Exception e) {
                e.printStackTrace();
                fileNodoDAO.delete(nodeList.get(i).getIp());
            }

        }
    }
}
