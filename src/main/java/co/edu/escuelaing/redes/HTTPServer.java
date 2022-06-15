/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.redes;

import java.net.*;
import java.io.*;
import java.util.Scanner;
/**
 *
 * @author cristian.forero-m
 */
public class HTTPServer {
    
    private static HTTPServer _instance = new HTTPServer();

    public static HTTPServer getInstance() {
        return _instance;
    }
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        
        boolean run = true;
        while(run){
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(35000);
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
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            
            String path = "";
            boolean firstL = true;
            
            while ((inputLine = in.readLine()) != null) {
                if(firstL)
                {
                    path = inputLine.split(" ")[1];
                    URI resource = new URI(path);
                    System.out.println("path= " + resource);
                    firstL = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            
            
            /*outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<title>Title of the document</title>\n" + "</head>"
                    + "<body>"
                    + "My Web Site"
                    + "<br></br>"
                    + "<img src=\"https://m.media-amazon.com/images/I/61Y8VHcrPXL._AC_SX425_.jpg\" alt=\"Turtwig\">"
                    + "</body>"
                    + "</html>" + inputLine;*/
            
            outputLine = _instance.readFile(path);

            out.println(outputLine);

            out.close();

            in.close();

            clientSocket.close();

            serverSocket.close();
        }
    }
    
    private String readFile(String path){
        String s = "";
        String p = "..\\src\\main\\resources\\";
        try{    
            if(path.equals("/")){
                p += "\\index.html";
            }
            else {
                p += path;
            }
            File outputFile = new File(p); //"..\\src\\main\\resources\\index.html"
            Scanner scan = new Scanner(outputFile);
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
}
