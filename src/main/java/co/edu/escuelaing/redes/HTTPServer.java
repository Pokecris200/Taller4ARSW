
package co.edu.escuelaing.redes;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cristian.forero-m
 */
public class HTTPServer {
    
	//Instancia de la clase
    private static HTTPServer _instance = new HTTPServer();
    
    //Condicion de carrera
    private static boolean run = true;

    public static HTTPServer getInstance() {
        return _instance;
    }
    
    /**
     * Inicia el servidor web
     */
    public static void start() {
    	while(run)
    	{
	    	ExecutorService threadPool = Executors.newFixedThreadPool(8);
	        ServerSocket serverSocket = null;
	        try {
	        	serverSocket = new ServerSocket(_instance.getPort());
		        Socket clientSocket = null;
	            System.out.println("Listo para recibir ...");
	            clientSocket = serverSocket.accept();
		        try {
		            HTTPServerProcesor.process(clientSocket);
		            serverSocket.close();
		        } catch (URISyntaxException ex) {
		            Logger.getLogger(HTTPServer.class.getName()).log(Level.SEVERE, null, ex);
		        } 
	        }
	        catch (IOException ex) {
	            Logger.getLogger(HTTPServer.class.getName()).log(Level.SEVERE, null, ex);
	        } 
    	}
        
    }
    
    public String readFile(String path, Socket cs){
        String s = "";
        String p = "target\\classes\\";
        try{    
            if(path.equals("/") || path.equals("")){
                p += "index.html";
            }
            else {
                p += path.replace("/", "");
            }
            File outputFile = new File(p); 
            Scanner scan = new Scanner(outputFile);
            if (p.contains(".htm") || p.contains(".js") || p.contains(".css")) {
            	s = files(outputFile, cs, scan, p);
            }
            else if (p.contains(".jpg") || p.contains(".png") || p.contains(".jpeg") || p.contains(".gif")) {
            	s = media(outputFile, cs, scan, p);
            }
            while( scan.hasNext()){
                s += " " + scan.next();
            }
        }
        catch (FileNotFoundException e) {
			s = fail(404);
		}
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(s); 
        return s;
        
    }
    
    private String files(File f, Socket skt, Scanner scan, String p){
        String s = "";
        String ext = (p.substring(p.indexOf(".") +1).equals("js")) ? "javascript": p.substring(p.indexOf(".") +1);
        s = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/"+ ext +"\r\n"
                    + "\r\n";
        while( scan.hasNext()){
                s += " " + scan.next();
            }
        return s;
    }
    
    private String media(File f, Socket skt, Scanner scan, String p){
        String s = "";
        byte[] bit = null;
        try{
            FileInputStream fis = new FileInputStream(f);
        }catch(Exception e){
            bit = new byte[0];
        }
        s = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: image/" + p.substring(p.indexOf(".")+1) + "\r\n"
                + "Content-Length: " + bit.length + "\r\n"
                + "\r\n";
        return s;
    }
    
    private int getPort(){
        if (System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000;
        
    }
    
    private String fail(int n) {
    	String s = "";
        if (n == 404) {
            s = "HTTP/1.1 404 Not Found\r\n\r\n"
                    + "<!DOCTYPE html>"
                    + "<html>"
                    + "<h1>404 El Recurso no esta disponible</h1>"
                    + "<br></br>"
                    + "<img src=https://c.tenor.com/sjnrOgJ_uagAAAAC/cute-cat-crying.gif>"
                    + "</html>";
        }
    	return s;
    }
}
