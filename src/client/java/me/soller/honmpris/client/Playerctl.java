package me.soller.honmpris.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Playerctl {

    public static String run(String... command) {

        try {

            Process process = new ProcessBuilder(command).start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

            return reader.readLine();

        } catch (Exception e) {
            return null;
        }
    }

}