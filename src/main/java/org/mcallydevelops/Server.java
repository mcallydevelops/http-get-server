package org.mcallydevelops;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {

    private final Context context;
    private boolean running;

    public Server(Context context) {
        this.context = context;
        this.running = true;
    }

    public void run() throws IOException {
        var serverSocket = new ServerSocket(8080);
        var threadPool = Executors.newFixedThreadPool(5);
        while(this.running) {
            var task = new RequestHandler(serverSocket.accept(), context.getObjectMapper());
            threadPool.submit(task);
        }
    }
}
