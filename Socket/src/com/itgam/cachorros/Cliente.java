package com.itgam.cachorros;

import java.io.*;
import java.net.*;
import javax.swing.JFileChooser;

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
				/*Vamos abrir un browser*/
				/*Con jfile vamos abrir un cuadro de dialogo que nos va a permitir 
				 * la eleccion de nuestros archivos*/
				JFileChooser selectorArchivos = new JFileChooser();
				/*files and direcroties nos permite selccionar carpetas y archivos
				 * si ponemos unicamente only directories solo podemos ver carpetas*/
				selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				selectorArchivos.setAcceptAllFileFilterUsed(true);
				File archivo = null;
				/*Este if controla el que el usuario seleccione un archivo o no
				 * approve option nos devuelve por valor numerico*/
				if (selectorArchivos.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					/*En este momento estamos indicando la ruta del archivo*/
					System.out.println("Directorio: " + selectorArchivos.getSelectedFile());
					/*Aqui pasamos la ruta del archivo que seleccionamos
					 * es importante recordar que archivo recibe como parametro un
					 * string esto se logra con el metodo getAbsolutePath*/
					archivo = new File(selectorArchivos.getSelectedFile().getAbsolutePath());
					//Si no seleccionamos nada retornaremos No seleccion
					}
				else{
					System.out.println("No selecionaste nada");
				}
				/*Termina apertura de browser*/
				//File archivo = new File("E:/Descargas/descarga.jpg");
				int tamañoArchivo = (int)archivo.length();
				System.out.println( "Enviando Archivo: "+archivo.getName());
				dos.writeUTF(archivo.getName());
				dos.writeInt(tamañoArchivo);
				FileInputStream fis = new FileInputStream("E:/Descargas/descarga.jpg");
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            BufferedOutputStream bos = new BufferedOutputStream(sckt.getOutputStream());
	            byte[] buffer = new byte[tamañoArchivo];
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
