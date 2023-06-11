/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upiita.bittorrent.dao.file;

import com.upiita.bittorrent.dao.TorrentDAO;
import com.upiita.bittorrent.model.Nodo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author iarog
 */
public class FileTorrentDAO implements TorrentDAO{
    
    private String staticDirectory;
    private String nameFile;
    
    public FileTorrentDAO(String nameFile){
        
        Properties props = new Properties();
        try(FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\bittorrent.properties")){
            props.load(fis);
            staticDirectory = props.getProperty("staticDirectoryClient") + props.getProperty("directoryTorrent");
            nameFile = staticDirectory + "\\" + nameFile;
        }
        catch(Exception ex){
            staticDirectory = "";
            nameFile= "";
            System.exit(1);
        }

    }
    
    @Override
    public void create(Nodo object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Nodo object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Nodo> list() {
        List<Nodo> nuevaLista = null;
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + nameFile)) {
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                nuevaLista = new ArrayList<>();
                nuevaLista = (List<Nodo>) ois.readObject();
                ois.close();
            } catch (Exception e) {

            } finally {
                fis.close();
            }
        } catch (Exception e) {

        }
        return nuevaLista;
    }

    @Override
    public void saveFile(List<Nodo> listaObject) {
        try (FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + nameFile)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(listaObject);
                oos.close();
            } catch (Exception e) {

            } finally {
                fos.close();
            }
        } catch (Exception e) {

        }
    }
    
}
