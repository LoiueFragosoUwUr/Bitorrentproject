package com.upiita.bittorrent.server;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.upiita.bittorrent.model.Nodo;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Guardado {
    /**/
    public static void main (String[] args){

        List<Nodo> nodos = new ArrayList<Nodo>();
        List<Integer> listaInt1 = new ArrayList<Integer>();
        listaInt1.add(7);
        listaInt1.add(2);
        listaInt1.add(9);
        listaInt1.add(4);
        listaInt1.add(8);

        List<Integer> listaInt2 = new ArrayList<Integer>();
        listaInt2.add(721);
        listaInt2.add(435);
        listaInt2.add(202);
        listaInt2.add(181);
        listaInt2.add(119);
        Nodo nodo1 = new Nodo("127.0.0.1", 4000, "LosIncreibles.png", listaInt1);
        Nodo nodo2 = new Nodo("127.0.1.1", 3000, "Incection.wmv", listaInt2);

        nodos.add(nodo1);
        nodos.add(nodo2);

        try(FileOutputStream fos = new FileOutputStream("SistemasDistribuidos.opus")){

            try(ObjectOutputStream oos = new ObjectOutputStream(fos)){
                oos.writeObject(nodos);
                oos.close();
            }
            catch(Exception exception2){
                exception2.printStackTrace();
            }

            fos.close();

        }
        catch(Exception exception){
            exception.printStackTrace();
        }


//        /*for(int i = 0; i < nodos.size(); i++){
//            System.out.println(nodos.get(i).toString());
//        }*/

        

    }

}
