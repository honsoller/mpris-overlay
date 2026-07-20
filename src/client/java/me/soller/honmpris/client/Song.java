package me.soller.honmpris.client;

public class Song {

    private final String coverPath;
    private final String track;
    private final String artist;
    private final int positionSeconds;
    private final long durationSeconds;
    private final float progressBarRatio;

    public Song(
            String coverPath,
            String track,
            String artist,
            int positionSeconds,
            long durationSeconds,
            float progressBarRatio
    ) {
        this.coverPath = coverPath;
        this.track = track;
        this.artist = artist;
        this.positionSeconds = positionSeconds;
        this.durationSeconds = durationSeconds;
        this.progressBarRatio = progressBarRatio;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public String getTrack() {
        return track;
    }

    public String getArtist() {
        return artist;
    }

    public float getProgressBarRatio() {
        return progressBarRatio;
    }

    public String getFormattedPosition() {
        return formatTime(positionSeconds);
    }

    public String getFormattedDuration() {
        return formatTime(durationSeconds);
    }

    private static String formatTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, secs);
        }

        return String.format("%d:%02d", minutes, secs);
    }
}