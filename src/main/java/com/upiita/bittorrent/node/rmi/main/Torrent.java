/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.rmi.main;

import com.upiita.bittorrent.dao.TorrentDAO;
import com.upiita.bittorrent.dao.file.FileTorrentDAO;
import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.node.controller.ClientManager;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author iarog
 */
public class Torrent extends TimerTask{
    
    private Properties props;
    List<DownloadClientRMI> hilosDescargas;
    
    public Torrent(){
        hilosDescargas = new ArrayList<>();
        props = new Properties();
        try(FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\bittorrent.properties")){
            props.load(fis);
        }
        catch(Exception ex){
            System.exit(1);
        }
        
        Timer timer = new Timer();
        timer.schedule(this, Integer.parseInt(props.getProperty("initTorrent")), Integer.parseInt(props.getProperty("intervalTorrent")));
        
        props = new Properties();
        try(FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\bittorrent.properties")){
            props.load(fis);
        }
        catch(Exception ex){
            System.exit(1);
        }
    }
    
    public void rebuildFiles(){
        String directory = System.getProperty("user.dir") + props.getProperty("staticDirectoryClient") + props.getProperty("directoryTorrent");
        File direc = new File(directory);
        String [] listFile = direc.list();
        
        for(String f: listFile){
            int sizeFile = 0;
            int fragmentsDownloaded = 0;
            TorrentDAO torrentDAO = new FileTorrentDAO(f);
            List<Nodo> nodos = torrentDAO.list();
            
            Nodo nodo = nodos.get(0);
            if(sizeFile == 0){
                sizeFile = (int) Math.ceil(nodo.getFiles().get(0).getSize());
            }
             byte [] buffer = new byte[(int) sizeFile];
             int chunkSize = (int)Math.ceil((double)sizeFile/Double.parseDouble(props.getProperty("sizePackage")));
             int offset = 0;
             for(int i = 0; i < Integer.parseInt(props.getProperty("sizePackage")); i++){
                 String fragmentName =  System.getProperty("user.dir") + props.getProperty("staticDirectoryClient") + props.getProperty("directoryFragments")  + "\\"+ ClientManager.accoplishFragment(nodo.getFiles().get(0).getNameFile(), i+1);

                 try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fragmentName))){
                     int bytesRead = 0;
                     int chunkSizeAux = chunkSize*(i+1) > sizeFile ? sizeFile-(chunkSize)*i : chunkSize;
                     
                     bytesRead = bis.read(buffer, offset, (int)chunkSizeAux);
                     offset += bytesRead;
                     fragmentsDownloaded++;
                 }
                 catch(Exception ex){
                     
                 }
             }
             
             if(fragmentsDownloaded >= Integer.parseInt(props.getProperty("sizePackage"))-1){
                 String fileName = System.getProperty("user.dir") + props.getProperty("staticDirectoryClient") + props.getProperty("directoryFiles")  + "\\" +nodo.getFiles().get(0).getNameFile();
                try(FileOutputStream fos = new FileOutputStream(fileName)){
                    fos.write(buffer);
                    fos.close();
                }
                catch(Exception ex){

                }
                
                torrentDAO.deleteFile();
             }
             else{
                 
                 for(Nodo n: nodos){
                     
                     String nombreNodo = n.getIp() + "_" + nodo.getFiles().get(0).getNameFile();
                     boolean e = false;
                     for(int i = 0; i< hilosDescargas.size(); i++){
                         
                         if(!hilosDescargas.get(i).isAlive()){
                             hilosDescargas.remove(i);
                             i--;
                         }else if(hilosDescargas.get(i).getName().equals(nombreNodo)){
                            e = true;
                            break;
                         }
                         
                     }
                     
                     if(!e){
                        DownloadClientRMI download = new DownloadClientRMI(n);
                        download.setName(nombreNodo);
                        hilosDescargas.add(download);
                        hilosDescargas.get(hilosDescargas.size()-1).start();
                     }
                     
                 }
             }
        }
        
        
    }
    
    @Override
    public void run(){
        rebuildFiles();
    }
    
}
