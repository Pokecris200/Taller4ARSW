/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.redes;

import java.util.concurrent.*;

/**
 *
 * @author cristian.forero-m
 */
public class HTTPServerController {
	public static void main(String[] args) {
        HTTPServer server = HTTPServer.getInstance();
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
