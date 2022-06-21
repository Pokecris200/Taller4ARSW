
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
    
    public static void start() {
        
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
    
    public String readFile(String path, Socket cs){
        String s = "";
        String p = "\\target\\classes\\";
        try{    
            if(path.equals("/") || path.equals("")){
                p += "index.html";
            }
            else {
                p += path.replace("/", "");
            }
            File outputFile = new File(p); //"..\\src\\main\\resources\\index.html"
            Scanner scan = new Scanner(outputFile);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + outputFile.exists());
            if (p.contains(".htm") || p.contains(".js") || p.contains(".css")) {
            	s = files(outputFile, cs, scan, p);
            }
            else if (p.contains(".jpg") || p.contains(".png") || p.contains(".jpeg") || p.contains(".gif")) {
            	s = media(outputFile, cs, scan, p);
            }
            /*if (p.contains(".html")){
                s = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n";
            }*/
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
    
    private String files(File f, Socket skt, Scanner scan, String p){
        String s = "";
        s = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/"+ p.substring(p.indexOf(".") +1)+"\r\n"
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
}
