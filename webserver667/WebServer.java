package webserver667;

import startup.configuration.MimeTypes;
import startup.configuration.ServerConfiguration;
import webserver667.core.ServerListener;

import java.io.IOException;
import java.nio.file.Path;

public class WebServer implements I667Server {

    private int port;
    private ServerListener serverListener;

    public WebServer() {
        super();
    }

    public WebServer(int port) {
        this.port = port;
    }

    @Override
    public void close() throws Exception {
        // no-op
    }

    @Override
    public void start(ServerConfiguration configuration, MimeTypes mimeTypes) {
        System.out.println("Server Starting...");
        int port = configuration.getPort();
        Path documentRoot = configuration.getDocumentRoot();
        System.out.println("Using port: " + port);
        System.out.println("Using document root: " + documentRoot);

        try {
            // start the listener to listen specific port
            serverListener = new ServerListener(port, documentRoot, mimeTypes);
            serverListener.start();
        } catch (IOException e) {
            System.out.println("Fail to start server, error: " + e.getMessage());
        }
    }

  @Override
  public void stop() {
        // no-op
  }
}
