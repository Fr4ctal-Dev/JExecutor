package com.company.client_server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeZoneCalculator implements Runnable{

    Socket client;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public TimeZoneCalculator(Socket client) throws IOException {
        this.client = client;
        bufferedReader = new BufferedReader(new
                InputStreamReader(client.getInputStream()));

        printWriter = new PrintWriter(client.getOutputStream(), true);

    }

    public TimeZoneCalculator() {

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
        Calendar calendar = getCalendar(TZId);
        printWriter.println("The time in " + TZId + " is " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));

    }

    Calendar getCalendar(String timeZoneId){
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(timeZone);
        return calendar;
    }

}
