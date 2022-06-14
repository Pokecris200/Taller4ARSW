/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.redes;

import java.net.URL;

/**
 *
 * @author cristian.forero-m
 */
public class URLExplorer {
    
    public static void main( String args[]){
        try{
            URL site = new URL("https://neox.atresmedia.com:80/games/noticias/actualidad/nintendo-habla-futuro-proyectos-que-todavia-estan-anunciar_201902015c5422d50cf2be7ee48555ce.html?title=protocol#Yo_jaja");
            
            System.out.println(site.getProtocol());
            System.out.println(site.getAuthority());
            System.out.println(site.getHost());
            System.out.println(site.getPort());
            System.out.println(site.getPath());
            System.out.println(site.getQuery());
            System.out.println(site.getFile());
            System.out.println(site.getRef());
            
        }catch( Exception e){
            e.printStackTrace();
        }
    }
}
