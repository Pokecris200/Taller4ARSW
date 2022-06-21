package co.edu.escuelaing.redes;

import java.util.concurrent.*;

public class HTTPClient {
	public static void main(String[] args) {
    	int nHilos = Integer.parseInt(args[0]);
    	ExecutorService threadPool = Executors.newFixedThreadPool(8);
        HTTPServer server = HTTPServer.getInstance();
        while(nHilos > 0) {
        	nHilos--;
        	ThreadProcesor tProcessor = new ThreadProcesor(getPort());
        	threadPool.execute(tProcessor);
        }
        threadPool.shutdown();
    }
    
    /**
     * Retorna el puerto activo en la variable de sistema
     * @return Puerto Activo para el cliente
     */
    private static int getPort(){
        if (System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000;
        
    }
}
