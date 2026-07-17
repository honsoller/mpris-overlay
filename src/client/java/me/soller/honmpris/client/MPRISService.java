package me.soller.honmpris.client;

import java.util.List;


public class MPRISService {

    // stores the current song
    private volatile Song currentSong;

    // starts a thread that updates the song information constantly
    public MPRISService() {

        Thread thread = new Thread(() -> {

            while (true) {

                updateSong();

                try {
                    Thread.sleep(250);
                } catch (InterruptedException ignored) {
                }
            }

        }, "honmpris-updater");


        thread.start();
    }

    // finds which player should be used
    private String getActivePlayer() {

        // gets all available mpris players
        List<String> players = Playerctl.runLines(
                "playerctl",
                "-l"
        );

        // checks every player
        for (String player : players) {

            // ignores firefox for now (i'm lazy to actually implement a blacklist feature and i don't want firefox
            if (player.toLowerCase().startsWith("firefox")) {
                continue;
            }

            // checks if this specific player is playing something
            String status = Playerctl.run(
                    "playerctl",
                    "--player=" + player,
                    "status"
            );


            // returns the first player that is actually playing
            if ("Playing".equalsIgnoreCase(status)) {

                return player;

            }
        }


        // no valid player found
        return null;
    }


    // gets the current song information
    private void updateSong() {

        // chooses which player will be used
        String player = getActivePlayer();

        // if there is no player playing anything
        if (player == null) {

            currentSong = new Song(
                    "textures/boymoder.png",
                    "nenhuma musica tocando..",
                    "",
                    0,
                    0,
                    0.0f
            );

            return;
        }


        // gets all the song info
        String coverPath = Playerctl.run( // cover art
                "playerctl",
                "--player=" + player,
                "metadata",
                "mpris:artUrl"
        );
        String track = Playerctl.run( // track
                "playerctl",
                "--player=" + player,
                "metadata",
                "xesam:title"
        );
        String artist = Playerctl.run( // artist
                "playerctl",
                "--player=" + player,
                "metadata",
                "xesam:artist"
        );
        String durationString = Playerctl.run( // duration
                "playerctl",
                "--player=" + player,
                "metadata",
                "mpris:length"
        );
        String positionString = Playerctl.run( // position
                "playerctl",
                "--player=" + player,
                "position"
        );



        // checks if the player has valid song information
        if (track == null || track.isBlank()) {


            // displays that nothing is playing
            currentSong = new Song(
                    "textures/boymoder.png",
                    "nenhuma musica tocando..",
                    "",
                    0,
                    0,
                    0.0f
            );

        }


        // if there is a song playing
        else {


            // converts the duration from microseconds to seconds
            long duration = durationString != null
                    ? Long.parseLong(durationString) / 1_000_000
                    : 0;


            // converts the current position to seconds
            int position = positionString != null
                    ? (int) Double.parseDouble(positionString)
                    : 0;


            // calculates how full the progress bar should be
            float progressBarRatio = duration > 0
                    ? (float) position / duration
                    : 0;



            // creates the song object with all information
            currentSong = new Song(
                    coverPath,
                    track,
                    artist,
                    position,
                    duration,
                    progressBarRatio
            );
        }
    }



    // returns the current song to the hud
    public Song getSong() {
        return currentSong;
    }
}