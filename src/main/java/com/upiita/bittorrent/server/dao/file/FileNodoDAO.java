/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upiita.bittorrent.server.dao.file;

import com.upiita.bittorrent.model.Nodo;
import com.upiita.bittorrent.server.dao.DAO;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author biosh
 */
public class FileNodoDAO implements DAO<Nodo>{
private final String staticDirectory="\\serverresources";
private final String nameFile;
public FileNodoDAO(){
     nameFile = staticDirectory+"\\bitTorrent.deDAO";

}      
    
    private void saveFile(List<Nodo> listaNodos){
    try(FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+ nameFile )) {
            try(ObjectOutputStream oos=new ObjectOutputStream(fos )) {
                oos.writeObject(listaNodos);
                oos.close();
            } catch (Exception e) {
            
            }
            finally {
                fos.close();
            }
        } catch (Exception e) {
            
        }
    }



    @Override
    public void create(Nodo object) {
        List <Nodo> nuevaLista=list();
        if (nuevaLista==null) {
            nuevaLista= new ArrayList<>();
        }
    
        nuevaLista.add(object);
        saveFile(nuevaLista);

        
    }

    @Override
    public void update(Nodo object) {
        List<Nodo> listaNodos= list();
        
        
        for(int i=0;i<listaNodos.size();i++){
            if (listaNodos.get(i).getIp().equals(object.getIp()) ) {
                Nodo aux=listaNodos.get(i);
                aux.setIp(object.getIp());
                aux.setPort(object.getPort());
                aux.setFiles(object.getFiles());
                listaNodos.set(i, aux);
                break;
            }
        }
        saveFile(listaNodos);
        
    }

    @Override
    public void delete(String s) {
        List<Nodo> listaNodos= list();
        
        for(int i=0;i<listaNodos.size();i++){
            if (listaNodos.get(i).getIp().equals(s) ) {
                listaNodos.remove(i);
                
                break;
            }
        }
            saveFile(listaNodos);
    }

    @Override
    public List<Nodo> list() {
        List <Nodo> nuevaLista=null;
        try(FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ nameFile )) {
            try(ObjectInputStream ois=new ObjectInputStream(fis )) {
                nuevaLista=new ArrayList<>();
               nuevaLista  = (List<Nodo>) ois.readObject();    
               ois.close();
            } catch (Exception e) {
            
            }
            finally {
                fis.close();
            }
        } catch (Exception e) {
            
        }
        return nuevaLista;
    }
    
}
