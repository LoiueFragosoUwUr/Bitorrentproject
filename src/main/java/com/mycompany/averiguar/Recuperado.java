/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.averiguar;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Recuperado {
    public static void main(String[] args){

        List<Nodo> nodos = new ArrayList<Nodo>();

        try(FileInputStream fis = new FileInputStream("SistemasDistribuidos.opus")){
             try(ObjectInputStream ois = new ObjectInputStream(fis)){
                nodos = (ArrayList<Nodo>) ois.readObject();


                for(int i = 0; i < nodos.size(); i++){
                    System.out.println("Nodo " + i);
                    System.out.println(nodos.get(i).toString());
                    System.out.print("Lista de Enteros");
                    for(int j = 0; j < nodos.get(i).getListaInt().size(); j++){
                        System.out.println(nodos.get(i).getListaInt().get(j));
                    }

                }
            }
            catch(Exception exception){
                exception.printStackTrace();
            }
        }
        catch(Exception exception){
            exception.printStackTrace();
        }

    }
}
