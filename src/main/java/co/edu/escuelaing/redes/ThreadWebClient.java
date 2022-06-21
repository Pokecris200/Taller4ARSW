package co.edu.escuelaing.redes;

import java.io.*;
import java.net.*;

public class ThreadWebClient implements Runnable{
	
	//Socket del cliente
	private Socket cliSocket;
	
	//condición de carrera
	private boolean running;
	
	public ThreadWebClient(Socket clientSocket, boolean run) {
        this.cliSocket = clientSocket;
        this.running = run;
    }

	@Override
	public void run() {
		try {
		    if (running) {
		        HTTPServerProcesor.process(cliSocket);
		    }
		    cliSocket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
        

    }
	
	
}
