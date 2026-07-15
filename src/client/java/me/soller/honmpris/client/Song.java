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

    public int getPositionSeconds() {
        return positionSeconds;
    }

    public long getDurationSeconds() {
        return durationSeconds;
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
        long minutes = seconds / 60;
        long secs = seconds % 60;

        return String.format("%d:%02d", minutes, secs);
    }
}