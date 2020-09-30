package com.company.client_server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Calculator implements Runnable{
    private static final String pos = "+";
    private static final String neg = "-";
    private static final String mult = "*";
    private static final String div = "/";



    private enum operation {
        pos, neg, mult, div
    }

    static double solution;
    private double x;
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double y;

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

    public static double addition(double x, double y) {
        return x + y;
    }

    public static double subtraction(double x, double y) {
        return x - y;
    }

    public static double multiplication(double x, double y) {
        return x * y;
    }

    public static double division(double x, double y) {
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
        double output = 0 ;
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
