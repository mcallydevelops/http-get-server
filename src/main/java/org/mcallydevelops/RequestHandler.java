package org.mcallydevelops;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private Socket socket;
    private final ObjectMapper objectMapper;

    public RequestHandler(Socket socket, ObjectMapper objectMapper) {
        this.socket = socket;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run() {
        try {
            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            String[] request = line.split(" ");
            String method = request[0];
            String path = request[1];
            String httpResponse = createResponse(method, path);
            socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private String createResponse(String method, String path) {
        if("GET".equals(method)) {
            if(!"/".equals(path)) {
                return "HTTP/1.1 404\n";
            }
            String response = "Hello World!";
            String httpResponseStatus = "HTTP/1.1 200 OK\n";
            String server = "Server: McAlly\n";
            String contentLength = String.valueOf(response.getBytes().length);
            String contentType = "text/plain\n";
            String httpResponse = new StringBuilder(httpResponseStatus)
                    .append(server)
                    .append(contentLength)
                    .append(contentType)
                    .append("\n")
                    .append("Hello World!")
                    .toString();
            return httpResponse;
        }
        String response = "HTTP/1.1 500 INTERNAL SERVER ERROR\n";
        return response;
    }
}
