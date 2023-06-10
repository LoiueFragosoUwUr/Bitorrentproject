/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.controller;

import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.model.Nodo;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iarog
 */
public class ClientManager {
        private final String staticDirectory = "\\clientresources";
        private final String filesDirectory;
        private final String fragmentsDirectory;
    
        public ClientManager(){
            filesDirectory = staticDirectory + "\\files";
            fragmentsDirectory = staticDirectory + "\\fragments";
        }
    
       public List<FileInformation> getFilesToShare(){
            List<FileInformation> files = new ArrayList<>();
       
           String actualDirectory = System.getProperty("user.dir");
           String absoluteDirectory = actualDirectory + filesDirectory;
           File direc = new File(absoluteDirectory);
           String [] listNames = direc.list();
           
           for(String fileName: listNames){
               
               File file = new File(absoluteDirectory + "\\"+fileName);
               FileInformation fileInfo = new FileInformation(fileName, file.length(),100.0, 10);
               
               files.add(fileInfo);
           }
           
           absoluteDirectory = actualDirectory + fragmentsDirectory;
           File direcFragments = new File(absoluteDirectory);
           listNames = direcFragments.list();
            
           for(String fileName: listNames){
               boolean encontrado = false;
               String [] fileNameSplited = fileName.split("_");
               String originalFileName = fileNameSplited[0] + "."+ ((fileNameSplited[1].split("[.]"))[1]);
               
               for(int i = 0; i < files.size(); i++){
                   if(files.get(i).getNameFile().equals(originalFileName)){
                       FileInformation fileAux = files.get(i);
                       fileAux.setPercentage(fileAux.getPercentage() + 10);
                       files.set(i, fileAux);
                        encontrado = true;
                       break;
                   }
               }
               
               if(!encontrado){
                   FileInformation fileInfoExtra = new FileInformation(originalFileName, 1, 10, 10);
                    files.add(fileInfoExtra);
               }
           }
           
           return files;
       }
       
    public Nodo createNodeToShare(List<FileInformation> files) throws UnknownHostException{
        Nodo node;
        
        InetAddress hostip = InetAddress.getLocalHost();
        String hostaddress = hostip.getHostAddress();
        
        node = new Nodo(hostaddress, 6000, files);
        
        return node;
        
    }
}
