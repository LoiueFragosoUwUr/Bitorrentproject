/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.node.controller;

import com.upiita.bittorrent.model.FileInformation;
import com.upiita.bittorrent.model.Nodo;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author iarog
 */
public class ClientManager {
        private String staticDirectory;
        private String filesDirectory;
        private String fragmentsDirectory;
        Properties props;
        public ClientManager(){
            
            props = new Properties();
            try(FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\bittorrent.properties")){
                props.load(fis);
                staticDirectory = props.getProperty("staticDirectoryClient");
                filesDirectory = staticDirectory + props.getProperty("directoryFiles");
                fragmentsDirectory = staticDirectory + props.getProperty("directoryFragments");
            }
            catch(Exception ex){
                staticDirectory = "";
                System.exit(1);
            }
            
        }
    
       public List<FileInformation> getFilesToShare(){
            List<FileInformation> files = new ArrayList<>();
            String absoluteDirectory = retrieveDirectory(filesDirectory);
           File direc = new File(absoluteDirectory);
           String [] listNames = direc.list();
           
           for(String fileName: listNames){
               
               File file = new File(absoluteDirectory + "\\"+fileName);
               List<Integer> listFragments = new ArrayList<>();
               for(int i = 0; i < 10; i++){
                   listFragments.add(i+1);
                   
                    
               }
               
               FileInformation fileInfo = new FileInformation(fileName, file.length(),100.0, listFragments);
               files.add(fileInfo);
               try(FileInputStream fis = new FileInputStream(file)){
                    fragmentFile(fis, (int)file.length(), fileName);
                }
                catch(Exception ex){

                }
           }
           
           absoluteDirectory = retrieveDirectory(fragmentsDirectory);
           File direcFragments = new File(absoluteDirectory);
           listNames = direcFragments.list();
            
           for(String fileName: listNames){
               boolean encontrado = false;
               String [] fileNameSplited = fileName.split("_");
               String [] numberExtension = fileNameSplited[1].split("[.]");
               String originalFileName = fileNameSplited[0] + "."+ numberExtension[1];
               
               for(int i = 0; i < files.size(); i++){
                   if(files.get(i).getNameFile().equals(originalFileName)){
                       FileInformation fileAux = files.get(i);
                       fileAux.setPercentage(fileAux.getPercentage() + 10);
                       
                       List<Integer> listFragments = fileAux.getFragments();
                       listFragments.add(Integer.parseInt(numberExtension[0]));
                       fileAux.setFragments(listFragments);
                       files.set(i, fileAux);
                        encontrado = true;
                       break;
                   }
               }
               
               if(!encontrado){
                   List<Integer> listFragments = new ArrayList<>();
                   listFragments.add(Integer.parseInt(numberExtension[0]));
                   File fileAuxi = new File(absoluteDirectory + "\\"+fileName);
                   FileInformation fileInfoExtra = new FileInformation(originalFileName, Integer.parseInt(props.getProperty("sizePackage"))*fileAuxi.length(), 10, listFragments);
                    files.add(fileInfoExtra);
               }
           }
           
           return files;
       }
       
    public Nodo createNodeToShare(List<FileInformation> files) throws UnknownHostException{
        Nodo node;
        
        InetAddress hostip = InetAddress.getLocalHost();
        String hostaddress = hostip.getHostAddress();
        
        node = new Nodo(hostaddress, 1100, files);
        
        return node;
        
    }
    
    public static String accoplishFragment(String nameFile, int fragment){
        String []splitName = nameFile.split("[.]");
        String completeName = splitName[0] + "_" +Integer.toString(fragment) + "." + splitName[1];
        return completeName;
    }
    
    public String retrieveDirectory(String directory){
         String actualDirectory = System.getProperty("user.dir");
        String absoluteDirectory = actualDirectory + directory;
        return absoluteDirectory;
    }
    
    private void fragmentFile(FileInputStream fis, int size, String fileName){
        
        try(BufferedInputStream bis = new BufferedInputStream(fis)){
            int newSize = size/Integer.parseInt(props.getProperty("sizePackage"));
            byte  [] buffer = new byte[newSize];
            int read = 0;
            int i = 0;
            
            while((read = bis.read(buffer)) != -1){
                
                String directory = retrieveDirectory(getFragmentsDirectory());
                String completeName = accoplishFragment(fileName, i+1);
                try(FileOutputStream fos = new FileOutputStream(directory + "\\" + completeName)){
                    fos.write(buffer);
                    fos.close();
                }
                catch(Exception ex){
                    
                }
                finally{
                    
                }
                
                i ++;
            }
            bis.close();
        }
        catch(Exception ex){
            
        }
        
    }
    
    public boolean verifyFragment(String nameFile, int fragment){
        
        boolean encontrado = false;
        
        String completeName = ClientManager.accoplishFragment(nameFile, fragment);
        String absoluteDirectory = retrieveDirectory(fragmentsDirectory);
        File direc = new File(absoluteDirectory);
        String [] listNames = direc.list();
        
        for(String name: listNames){
            if(encontrado = (name.equals(completeName))){
                break;
            }
        }
        
        return encontrado;
        
    }

    public String getFilesDirectory() {
        return filesDirectory;
    }

    public String getFragmentsDirectory() {
        return fragmentsDirectory;
    }
    
    
    
}
