package com.company.client_server.u1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public static class WriterCallable implements Runnable{
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


        public static class TimeZoneCalculator implements Runnable{

            Socket client;
            BufferedReader bufferedReader;
            PrintWriter printWriter;

            public TimeZoneCalculator(Socket client) throws IOException {
                this.client = client;
                bufferedReader = new BufferedReader(new
                        InputStreamReader(client.getInputStream()));

                printWriter = new PrintWriter(client.getOutputStream(), true);

            }

            @Override
            public void run() {
                String[] id = TimeZone.getAvailableIDs();
                printWriter.println("In TimeZone class available Ids are: ");
                for (int i=0; i<id.length; i++){
                    printWriter.println(id[i]);
                }

                printWriter.println("Input desired timezone: ");

                String TZId = "";
                try {
                    TZId = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TimeZone timeZone = TimeZone.getTimeZone(TZId);
                Calendar calendar = new GregorianCalendar();
                calendar.setTimeZone(timeZone);
                printWriter.println("The time in " + TZId + " is " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));





            }
        }

        public static class Calculator implements Runnable{
            private static final String pos = "+";
            private static final String neg = "-";
            private static final String mult = "*";
            private static final String div = "/";

            

            private enum operation {
                pos, neg, mult, div
            }

            private int solution;
            private int x;
            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            private int y;

            BufferedReader bufferedReader;
            PrintWriter printWriter;
            Socket client;



            

            public Calculator(Socket client) throws IOException {
                this.client = client;
                solution = 0;

                bufferedReader = new BufferedReader(new
                        InputStreamReader(client.getInputStream()));

                printWriter = new PrintWriter(client.getOutputStream(), true);

            }

            public int addition(int x, int y) {
                return x + y;
            }

            public int subtraction(int x, int y) {
                return x - y;
            }

            public int multiplication(int x, int y) {
                return x * y;
            }

            public int division(int x, int y) {
                solution = x / y;
                return solution;
            }
            

            @Override
            public void run() {
                System.out.println(client.getInetAddress() + ":" + client.getLocalPort() + " started calculator");
                printWriter.println("Insert 2 numbers");

                printWriter.println("operand 1: ");

                try {
                    setX(Integer.parseInt(bufferedReader.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                printWriter.println("operand 2: ");
                try {
                    setY(Integer.parseInt(bufferedReader.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                printWriter.println("What operation? ('pos', 'neg', 'mult', 'div')");
                operation ttt = null;
                try {
                    ttt = operation.valueOf(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int output = 0 ;
                assert ttt != null;
                switch(ttt){
                    case pos:
                        output = addition(getX(), getY());

                        break;
                    case neg:
                        output = subtraction(getX(), getY());

                        break;
                    case mult:
                        output = multiplication(getX(), getY());

                        break;
                    case div:
                        output = division(getX(), getY());

                        break;
                }
                printWriter.println("output ="+output);
            }
            
        }



    }


}
