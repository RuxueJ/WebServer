package webserver667.core;

import startup.configuration.MimeTypes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

/**
 * @author 7991uxug@gmail.com
 * @date 3/3/24 10:20 PM
 */
public class ServerListener extends Thread{

    private int port;
    private Path documentRoot;
    private MimeTypes mimeTypes;
    private ServerSocket serverSocket;

    public ServerListener(int port, Path documentRoot, MimeTypes mimeTypes) throws IOException {
        this.port = port;
        this.documentRoot = documentRoot;
        this.mimeTypes = mimeTypes;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println(" * connection accepted: " + socket.getInetAddress());
                // create and start a thread to process request
                HttpConnectionWorkerThread httpConnectionWorkerThread = new HttpConnectionWorkerThread(socket, documentRoot, mimeTypes);
                httpConnectionWorkerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                    System.out.println("Connection Closed.");
                } catch (IOException e) {}
            }
        }
    }

    public void close() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
            }
        }
    }
}
