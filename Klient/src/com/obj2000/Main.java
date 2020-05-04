package com.obj2000;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Klient client = new Klient("127.0.0.1", 5000);

        System.out.println("Ready");

        Scanner sc = new Scanner(System.in);

        String line = "";
        while (!line.equals("exit")) {
            line = sc.nextLine();
            if (!line.isEmpty()) {
                client.sendMessage(line);
                System.out.println(client.receiveMessage());
            }
        }

        sc.close();
    }
}
