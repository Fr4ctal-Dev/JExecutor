package com.company.client_server.main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {



    public static void main(String[] args) throws IOException {

        //ArrayList<Socket> clients = new ArrayList<>();
        Socket client;
        ServerSocket serverSocket = new ServerSocket(12345);
        ExecutorService executorService = Executors.newCachedThreadPool();

        while(true) {
            client = serverSocket.accept();
            //clients.add(client);
            WriterCallable writerCallable = new WriterCallable(client, executorService);
            executorService.submit(writerCallable);
        }

    }


}
