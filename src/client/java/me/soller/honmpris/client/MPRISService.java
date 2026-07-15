package me.soller.honmpris.client;

import me.soller.honmpris.HonMPRIS;

import net.minecraft.resources.Identifier;



public class MPRISService {

    private volatile Song currentSong;

    public MPRISService() {

        new Thread(() -> {

            while (true) {

                updateSong();

                try {

                    Thread.sleep(250);

                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }

    private void updateSong() {
        String coverPath = Playerctl.run(
                "playerctl",
                "metadata",
                "mpris:artUrl"
        );
        String track = Playerctl.run(
                "playerctl",
                "metadata",
                "xesam:title"
        );
        String artist = Playerctl.run(
                "playerctl",
                "metadata",
                "xesam:artist"
        );
        String durationString = Playerctl.run(
                "playerctl",
                "metadata",
                "mpris:length"
        );
        String positionString = Playerctl.run(
                "playerctl",
                "position"
        );

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
        // if its playing something
        else {

            // converts the strings to int (and also converts microseconds into seconds)
            long duration = durationString != null
                    ? Long.parseLong(durationString) / 1_000_000
                    : 0;

            int position = positionString != null
                    ? (int) Double.parseDouble(positionString)
                    : 0;

            float progressBarRatio = duration > 0
                    ? (float) position / duration
                    : 0;

            // displays the song info
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

    public Song getSong() {
        return currentSong;
    }
}


