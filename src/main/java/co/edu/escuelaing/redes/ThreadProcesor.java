package co.edu.escuelaing.redes;

import java.io.*;
import java.net.*;

public class ThreadProcesor implements Runnable {
	
	//puerto por el que corre
	private int runPort;
	
	public ThreadProcesor(int port) {
		runPort = port;
		
    }

	@Override
	public void run() {
		Socket socket = conection();
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error at create PrintWriter");
        }
        out.close();
	}
	
	/**
	 * Metodo que conecta/reconecta cuando la cola esta llena
	 * @return El Socket creado para la conexion
	 */
	private Socket conection() {
        Socket socket = null;
        try {
        	socket = new Socket("127.0.0.1", runPort);
        } catch (UnknownHostException e) 
        {
            System.err.println("Fail on the Host");
            System.exit(1);
        } catch (IOException e) 
        {
            return conection();
        }
        return socket;
    }
	
}
