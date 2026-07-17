package me.soller.honmpris.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Config {


    private static final Gson GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();



    private static Config config;



    public boolean renderOverlay = true;

    public float hudScale = 1.0f;

    public int hudX = 10;

    public int hudY = 10;

    public int accentColor = 0xFF00FF00;

    public List<String> blacklist = new ArrayList<>();





    public static void load() {


        File file =
                new File(
                        Minecraft.getInstance().gameDirectory,
                        "config/honmpris.json"
                );


        try {


            if (file.exists()) {


                FileReader reader =
                        new FileReader(file);


                config =
                        GSON.fromJson(
                                reader,
                                Config.class
                        );


                reader.close();


            }



            if (config == null) {

                config = new Config();

            }


            // garante que a lista nunca seja null

            if (config.blacklist == null) {

                config.blacklist = new ArrayList<>();

            }



            save();



        } catch(Exception e) {


            e.printStackTrace();


            config = new Config();


        }


    }






    public static void save() {


        if(config == null) {

            return;

        }



        File file =
                new File(
                        Minecraft.getInstance().gameDirectory,
                        "config/honmpris.json"
                );



        try {


            file.getParentFile().mkdirs();



            FileWriter writer =
                    new FileWriter(file);



            GSON.toJson(
                    config,
                    writer
            );



            writer.close();



        } catch(Exception e) {


            e.printStackTrace();


        }


    }






    public static Config get() {


        if(config == null) {

            load();

        }


        return config;


    }


}