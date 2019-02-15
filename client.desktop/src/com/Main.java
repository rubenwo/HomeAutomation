package com;

import com.net.Connector;
import com.net.Request;
import com.net.RequestMethod;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //   new UI();

        Connector connector = new Connector();
        connector.add(new Request(
                "http://localhost/devices",
                RequestMethod.GET,
                null,
                response -> {
                    System.out.println(response.toString());
                },
                error -> {
                    System.out.println(error.toString());
                }
        ));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
