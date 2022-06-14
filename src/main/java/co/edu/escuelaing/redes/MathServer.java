/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author cristian.forero-m
 */
public class MathServer {
    private static String operation = "sin";
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
            }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
            }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(
            "Mensaje:" + inputLine);
            outputLine = "Respuesta: " + calc(inputLine);
            out.println(outputLine);
            if (outputLine.equals("Respuesta: Bye.")) {
                break;
            }
            }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
    
    private static Double calc(String input){
        Double res = 0.0;
        if(input.contains("fun"))
        {
            operation = input.substring(4);
        }
        else{
            Double n = Double.valueOf(input);
            if(operation.equals("sin")){
                res = Math.sin(n);
            }
            else if(operation.equals("cos")){
                res = Math.cos(n);
            }
            else if(operation.equals("tan")){
                res = Math.tan(n);
            }
        }
        return res;
    }
}
