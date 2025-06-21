import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new MyHandler());
        server.setExecutor(null); // default executor
        System.out.println("Java HTTP server is running on port " + port);
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String html = """
                    <div style="display:flex;height:100vh;align-items:center;">
                        <h1 style="color:royalblue;text-align:center;justify-content:center;font-size:40px;">
                            Welcome to Java Socket Server
                        </h1>
                    </div>
                    """;

            byte[] response = html.getBytes();
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();

            System.out.println("\n---Server Received---\n" + exchange.getRequestMethod() + " " + exchange.getRequestURI());
        }
    }
}
