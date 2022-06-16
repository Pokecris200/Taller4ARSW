
package co.edu.escuelaing.redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author cristian.forero-m
 */
public class HTTPServerProcesor {
    public static void process(Socket clientSocket) throws IOException, URISyntaxException{
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
            
            outputLine = HTTPServer.getInstance().readFile(path);

            out.println(outputLine);

            out.close();

            in.close();

            clientSocket.close();

    }
    
    
}
