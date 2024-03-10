package webserver667.core;

import startup.configuration.MimeTypes;
import webserver667.constant.Constants;
import webserver667.exceptions.responses.BadRequestException;
import webserver667.exceptions.responses.MethodNotAllowedException;
import webserver667.exceptions.responses.ServerErrorException;
import webserver667.logging.Logger;
import webserver667.requests.HttpRequest;
import webserver667.requests.RequestReader;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;
import webserver667.responses.Resource;
import webserver667.responses.writers.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * @author 7991uxug@gmail.com
 * @date 3/3/24 10:40 PM
 */
public class HttpConnectionWorkerThread extends Thread{

    private Socket socket;
    private Path documentRoot;
    private MimeTypes mimeTypes;

    public HttpConnectionWorkerThread(Socket socket, Path documentRoot, MimeTypes mimeTypes) {
        this.socket = socket;
        this.documentRoot = documentRoot;
        this.mimeTypes = mimeTypes;
    }

    @Override
    public synchronized void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        ResponseWriter responseWriter = null;
        HttpRequest request = null;
        IResource resource = null;
        long fileSize = 0;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            RequestReader requestReader = new RequestReader(inputStream);
            try {
                request = requestReader.getRequest();
            }
            // if something wrong with reading request, create bad request or method not allowed accordingly
            catch (BadRequestException e) {
                responseWriter = new BadRequestResponseWriter(outputStream, resource, request);
            } catch (MethodNotAllowedException e) {
                responseWriter = new MethodNotAllowedResponseWriter(outputStream, resource, request);
            }
            // normal process: create writers according to the request and resource
            if (request != null) {
                resource = new Resource(request.getUri(), request.getQueryString(), String.valueOf(documentRoot), mimeTypes);
                fileSize = resource.getFileSize();
                responseWriter = ResponseWriterFactory.create(outputStream, resource, request);
            }
        } catch (Exception e) {
            // if something unexpected happens, create InternalServerErrorResponseWriter to return a 500 response
            responseWriter = new InternalServerErrorResponseWriter(outputStream, resource, request);
        } finally {
            // call write method
            try {
                responseWriter.write();
            } catch (Exception e) {
            }
            // print log
            System.out.println(Logger.getLogString(socket.getInetAddress().getHostAddress(), request, resource, responseWriter.getResponseCode(), (int)fileSize));
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
            System.out.println("Processing Finished.");
        }
    }
}
