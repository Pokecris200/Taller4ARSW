
package co.edu.escuelaing.redes;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cristian.forero-m
 */
public class HTTPServer {
    
    private static HTTPServer _instance = new HTTPServer();

    public static HTTPServer getInstance() {
        return _instance;
    }
    
    public static void main(String[] args) {
        
        boolean run = true;
        while(run){
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(_instance.getPort());
            } catch (IOException e) {
                System.err.println("Could not listen on port: 35000.");
                System.exit(1);
            }
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            try {
                HTTPServerProcesor.process(clientSocket);
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(HTTPServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(HTTPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public String readFile(String path){
        String s = "";
        String p = "src\\main\\resources\\";
        try{    
            if(path.equals("/")){
                p += "\\index.html";
            }
            else {
                p += path;
            }
            File outputFile = new File(p); //"..\\src\\main\\resources\\index.html"
            Scanner scan = new Scanner(outputFile);
            if (p.contains(".html")){
                s = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n";
            }
            while( scan.hasNext()){
                s += " " + scan.next();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(s); 
        return s;
        
    }
    
    private int getPort(){
        if (System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000;
        
    }
}
