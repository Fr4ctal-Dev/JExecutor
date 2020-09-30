package com.company.client_server.main.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket s = new Socket("localhost", 12345);

        ExecutorService executorService = Executors.newCachedThreadPool();
        Runnable socketReader = new Runnable() {
            @Override
            public void run() {

                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new
                            InputStreamReader(s.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String message = "";
                while (true) {
                    try {
                        assert bufferedReader != null;
                        message = bufferedReader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(message);
                }
            }
        };
        Runnable socketWriter = new Runnable() {
            @Override
            public void run() {

                PrintWriter printWriter = null;
                try {
                    printWriter = new PrintWriter(s.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert printWriter != null;
                while (true) {
                    String message;
                    message = new Scanner(System.in).next();

                    printWriter.println(message);
                    if (message.equals("exit")){
                        System.exit(1);
                    }
                }
            }
        };

        executorService.submit(socketReader);
        executorService.submit(socketWriter);


    }


}
