package com.example.epokamission;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DebutFinMission {

    public DebutFinMission(String[] argv)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "12/12/1988";

        try
        {
            Date date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
