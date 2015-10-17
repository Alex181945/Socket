package com.itgam.cachorros;

import java.io.*;
import java.net.*;

public class Cliente {
	
public static void main(String[] args){
        
        InetAddress direccion = null;
        
        try {
            direccion = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Hubo un error al obtenet la direccion, "+e);
            System.err.println(-1);
        }
        
        int puerto = 1234;
        
        for (int i = 1; i < args.length; i++) {
            Socket sckt = null;
            DataInputStream dis = null;
            DataOutputStream dos = null;
            
            try {
                int numero = 10;
                
                sckt = new Socket(direccion,puerto);
                
                dis = new DataInputStream(sckt.getInputStream());
                dos = new DataOutputStream(sckt.getOutputStream());
                
                dos.writeInt(numero);
                long resultado = dis.readLong();
                
                System.out.println("Solicitud = "+numero+"\tResultado = "+
                        resultado);
                
                dis.close();
                dos.close();
            } catch (Exception e) {
                System.err.println("Error al abrir el socket de servidor: "+e);
            }
            
            try {
                if (sckt != null){
                    sckt.close();
                }
            } catch (Exception e) {
                System.err.println("Error al cerrar el socket: "+e);
            }
        }
    }
}
