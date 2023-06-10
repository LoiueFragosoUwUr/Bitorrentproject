package com.upiita.bittorrent.server;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author biosh
 */
import java.rmi.*;

import java.rmi.registry.*;

import java.rmi.server.*;

import java.net.*;

public class RmiServer extends java.rmi.server.UnicastRemoteObject implements ReceiveMessageInterface

{

    int thisPort;

    String thisAddress;

    Registry registry; // rmi registry for lookup the remote objects.

    // This method is called from the remote client by the RMI.

    // This is the implementation of the �gReceiveMessageInterface�h.

    public void receiveMessage(String x) throws RemoteException

    {

        System.out.println(x);

    }

    public RmiServer() throws RemoteException, AlreadyBoundException

    {

        try {

            // get the address of this host.

            thisAddress = (InetAddress.getLocalHost()).toString();

        }

        catch (Exception e) {

            throw new RemoteException("can't get inet address.");

        }

        thisPort = 1099; // this port(registry�fs port)

        System.out.println("this address=" + thisAddress + ",port=" + thisPort);

        try {

            // create the registry and bind the name and object.

            registry = LocateRegistry.createRegistry(thisPort);

            registry.rebind("rmiServer", this);
            //FirstStepTracker server= new FirstStepTracker();
           // Registry registry = LocateRegistry.createRegistry(1099);
//            registry.bind("FileTransfer", this);
//            registry.bind("InformsItstheTracker",this);

        }

        catch (RemoteException e) {

            throw e;

        }

    }

    static public void main(String args[])

    {

        try {

            RmiServer s = new RmiServer();

        }

        catch (Exception e) {

            e.printStackTrace();

            System.exit(1);

        }

    }

}
