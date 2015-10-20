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

		/*Variables de entrada y salida
		 * En este caso tambien creamos un objeto del tipo date
		 * para obtener la hora del servidor*/
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
				/*Asignamos a la variable entrada lo que 
				 * el cliente envie en este caso un entero*/
				entrada = dis.readInt();
				
				/*Realizamos una operacion en este caso multiplicando por si 
				 * mismo el valor de entrada y casteando al tipo de dato long
				 * se podria cambiar la sentencia por la siguiente:
				 * salida = (long) Math.pow(entrada, 2);*/
				salida = (long)entrada * (long)entrada;
				
				/*Enviamos la operacion asi como la hora del servidor*/
				dos.writeLong(salida);
				dos.writeUTF(hora.toString());
				
				/*Solo para asegurarnos de que conincidan la imprmimos de este lado XD*/
				System.out.println(hora.toString());
				
				/*Recepcion del archivo*/
				
				String nombreArchivo = dis.readUTF().toString();
				int tam = dis.readInt();
				System.out.println( "Recibiendo archivo "+nombreArchivo);
				FileOutputStream fos = new FileOutputStream("G:\\Archivos del servidor\\"+nombreArchivo);
	            BufferedOutputStream out = new BufferedOutputStream(fos);
	            BufferedInputStream in = new BufferedInputStream(sckt.getInputStream());
	            byte[] buffer = new byte[tam];
	            for( int i = 0; i < buffer.length; i++ )
	               {
	                  buffer[i] = ( byte )in.read(); 
	               }
	            out.write(buffer);
	            System.out.println( "Archivo Recibido "+nombreArchivo);
				
				/*Cerramos conexiones*/
				out.flush(); 
	            in.close();
	            out.close();
				dis.close();
				dos.close();
				sckt.close();
				
				/*Por ultimo guardamos un registro de quien se conecto y por que puerto lo realizo
				 * esta informacin podria ser parte de un log*/
				System.out.println("Cliente = "+direccion+" puerto = "+puerto+
						" Entrada = "+entrada+" Salida ="+salida);
			} catch (Exception e) {
				System.err.println("Se ha producido un error: "+e);
			}
		}
	}
}
