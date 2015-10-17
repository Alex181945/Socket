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
		/*En esta seccion entramos a traves de un for para
		 * realizar la conexion con el servidor pero tambien podriamos crear 
		 * una condicion que no finalice la conexion hasta que el cliente
		 * lo indique*/
		for (int i = 1; i < 2; i++) {
			/*Declaramos e inicializamos variables para realizar la conexion*/
			Socket sckt = null;
			DataInputStream dis = null;
			DataOutputStream dos = null;

			try {
				/*Variable numero que vamos a enviar al servidor*/
				int numero = 10;
				
				/*Instancia de las variables para realizar la conexion*/
				sckt = new Socket(direccion,puerto);
				dis = new DataInputStream(sckt.getInputStream());
				dos = new DataOutputStream(sckt.getOutputStream());
				
				/*Envio de variable numero*/
				dos.writeInt(numero);
				/*Enviando el archivo al servidor*/
				File archivo = new File("E:/Descargas/descarga.jpg");
				int tama�oArchivo = (int)archivo.length();
				System.out.println( "Enviando Archivo: "+archivo.getName());
				dos.writeUTF(archivo.getName());
				dos.writeInt(tama�oArchivo);
				FileInputStream fis = new FileInputStream("E:/Descargas/descarga.jpg");
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            BufferedOutputStream bos = new BufferedOutputStream(sckt.getOutputStream());
	            byte[] buffer = new byte[tama�oArchivo];
	            bis.read( buffer );
	            for( int x = 0; x < buffer.length; x++ )
	            {
	                bos.write( buffer[ x ] ); 
	            }
	            System.out.println( "Archivo Enviado: "+archivo.getName());
				/*Valores que nos envia el servidor*/
				long resultado = dis.readLong();
				
				System.out.println("Solicitud = "+numero+"\tResultado = "+
						resultado);
				
				System.out.println("La hora del sistema es: "+
						dis.readUTF());
				/*Cerrando la conexion*/
				bis.close();
	            bos.close();
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
