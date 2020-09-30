package com.company.client_server.main.server;

import com.company.client_server.commands.Calculator;
import com.company.client_server.commands.TimeZoneCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class WriterCallable implements Runnable{
    Socket client;
    ExecutorService executorService;

    public WriterCallable(Socket client, ExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }

    @Override
    public void run() {

        try {
            System.out.println("New client connected: " + client.getInetAddress() + ":" + client.getLocalPort());
            PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true);
            printWriter.println("Welcome to the server!");
            printWriter.flush();
            while (true) {
                printWriter.println("Available functions: calculator, timezone, exit");
                printWriter.flush();
                BufferedReader bufferedReader = new BufferedReader(new
                        InputStreamReader(client.getInputStream()));
                String message = bufferedReader.readLine();
                switch (message){
                    case "calculator":
                        Calculator calculator = new Calculator(client);

                        Future<Void> calculatorReturn = (Future<Void>) executorService.submit(calculator);
                        calculatorReturn.get();
                        break;

                    case "timezone":
                        TimeZoneCalculator timeZoneCalculator = new TimeZoneCalculator(client);

                        Future<Void> timeZoneCalculatorReturn = (Future<Void>) executorService.submit(timeZoneCalculator);
                        timeZoneCalculatorReturn.get();
                        break;


                }
            }


        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }


}
