package webserver667;

import startup.configuration.MimeTypes;
import startup.configuration.ServerConfiguration;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.requests.RequestReader;
import webserver667.responses.IResource;
import webserver667.responses.Resource;
import webserver667.responses.writers.ResponseWriter;
import webserver667.responses.writers.ResponseWriterFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

public class WebServer implements I667Server {

    private int port;

    public WebServer() {
        super();
    }

    public WebServer(int port) {
        this.port = port;
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public void start(ServerConfiguration configuration, MimeTypes mimeTypes) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(configuration.getPort());
            Path documentRoot = configuration.getDocumentRoot();
            while (true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    RequestReader requestReader = new RequestReader(socket.getInputStream());
                    HttpRequest request = requestReader.getRequest();
                    IResource resource = new Resource(request.getUri(), request.getQueryString(), String.valueOf(documentRoot), mimeTypes);
                    ResponseWriter responseWriter = ResponseWriterFactory.create(
                            socket.getOutputStream(),
                            resource,
                            request
                    );
                    responseWriter.write();
                } catch (Exception e) {
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public void stop() {
    }
}
