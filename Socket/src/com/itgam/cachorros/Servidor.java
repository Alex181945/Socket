package com.itgam.cachorros;

import java.io.*;
import java.net.*;
import java.util.Date;

public class Servidor {

	public static void main(String args[]){
		/**/
		/*Obteniendo la direccion ip del equipo
        Note: La direccion sin necesidad del string*/
		try {
			System.out.println("La direccion IP = "+InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			System.out.println("Hubo un error al obtenet la direccion, "+e);
		}

		/*Abriendo un puerto*/
		ServerSocket ss = null;

		try {
			ss = new ServerSocket(1234);
		} catch (IOException e) {
			System.out.println("Error al abrir el socket de servidor: "+e);
			System.exit(-1);
		}

		/*Variables*/
		int entrada;
		long salida;
		Date hora = new Date();

		while (true) {            
			try {
				/*El programa se va a quedar en este paso hasta detectar una
                conexion*/           
				Socket sckt = ss.accept();
				/*Si alguien se conecta, se continua con la ejecucion*/
				DataInputStream dis = new DataInputStream(sckt.getInputStream());
				DataOutputStream dos = new DataOutputStream(sckt.getOutputStream());

				int puerto = sckt.getPort();

				InetAddress direccion = sckt.getInetAddress();

				entrada = dis.readInt();

				salida = (long)entrada * (long)entrada;

				dos.writeLong(salida);
				dos.writeUTF(hora.toString());
				System.out.println(hora.toString());

				dis.close();
				dos.close();
				sckt.close();

				System.out.println("Cliente = "+direccion+" puerto = "+puerto+
						" Entrada = "+entrada+" Salida ="+salida);
			} catch (Exception e) {
				System.err.println("Se ha producido un error: "+e);
			}
		}
	}
}
