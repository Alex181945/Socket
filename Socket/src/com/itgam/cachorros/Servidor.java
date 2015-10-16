package com.itgam.cachorros;

import java.io.*;
import java.net.*;

public class Servidor {

	public static void main(String args[]){
		try {
			System.out.println("Ip Local = "+InetAddress.getLocalHost().toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
