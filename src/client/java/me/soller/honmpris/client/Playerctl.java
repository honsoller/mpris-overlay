package me.soller.honmpris.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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


    public static List<String> runLines(String... command) {

        List<String> lines = new ArrayList<>();

        try {

            Process process = new ProcessBuilder(command).start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {

                lines.add(line);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lines;
    }

}