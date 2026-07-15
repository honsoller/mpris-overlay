package me.soller.honmpris.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class HudRenderer {

    // hud size
    private static final int HUD_WIDTH = 250;
    private static final int HUD_HEIGHT = 64;
    private static final int PADDING = 6;

    private static final int COVER_SIZE = HUD_HEIGHT - PADDING * 2;

    private static final int TEXT_X =
            COVER_SIZE + PADDING * 2;

    private static final int BAR_Y = 42;

    private static final int BAR_WIDTH =
            HUD_WIDTH - COVER_SIZE - PADDING * 3;

    private static final int BAR_HEIGHT = 2;

    // hud colors
    private static final int BACKGROUND_COLOR = 0x80000000;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int SECONDARY_TEXT_COLOR = 0x99FFFFFF;
    private static final int BAR_BACKGROUND = 0x55FFFFFF;

    // title
    private static final float TITLE_SCALE = 1.4f;
    // algo

    // album cover cache
    private static String currentCoverPath = null;
    private static Identifier currentCoverTexture = null;

    private static final AlbumCoverManager coverManager =
            new AlbumCoverManager();

    // 1. BACKGROUND
    private static void drawBackground(GuiGraphicsExtractor context) {
        context.fill(
                0,
                0,
                HUD_WIDTH,
                HUD_HEIGHT,
                BACKGROUND_COLOR
        );
    }

    // 2. ALBUM COVER
    private static void drawAlbumCover(GuiGraphicsExtractor context) {
        context.blit(
                RenderPipelines.GUI_TEXTURED,
                currentCoverTexture,
                PADDING,
                PADDING,
                0,
                0,
                COVER_SIZE,
                COVER_SIZE,
                COVER_SIZE,
                COVER_SIZE
        );
    }

    // 3. SONG INFO
    private static void drawSongInfo(
            GuiGraphicsExtractor context,
            Minecraft minecraft,
            Song song
    ) {

        // increases a little bit the size
        context.pose().pushMatrix();
        context.pose().scale(TITLE_SCALE, TITLE_SCALE);

        // track
        context.text(
                minecraft.font,
                Component.literal(song.getTrack()),
                (int) (TEXT_X / TITLE_SCALE),
                (int) (10 / TITLE_SCALE),
                TEXT_COLOR,
                false
        );

        context.pose().popMatrix();

        // title
        context.text(
                minecraft.font,
                Component.literal("by " + song.getArtist()),
                TEXT_X,
                24,
                SECONDARY_TEXT_COLOR,
                false
        );
    }

    // 4. PROGRESS BAR
    private static void drawProgressBar(
            GuiGraphicsExtractor context,
            Song song
    ) {

        context.fill(
                TEXT_X,
                BAR_Y,
                TEXT_X + BAR_WIDTH,
                BAR_Y + BAR_HEIGHT,
                BAR_BACKGROUND
        );

        int progressWidth =
                (int) (BAR_WIDTH * song.getProgressBarRatio());

        context.fill(
                TEXT_X,
                BAR_Y,
                TEXT_X + progressWidth,
                BAR_Y + BAR_HEIGHT,
                Config.ACCENT_COLOR
        );
    }

    // 5. TIME
    private static void drawTime(
            GuiGraphicsExtractor context,
            Minecraft minecraft,
            Song song
    ) {

        String current = song.getFormattedPosition();

        context.text(
                minecraft.font,
                Component.literal(current),
                TEXT_X,
                50,
                SECONDARY_TEXT_COLOR,
                false
        );

        String duration = song.getFormattedDuration();

        int durationWidth = minecraft.font.width(duration);
        int durationX = TEXT_X + BAR_WIDTH - durationWidth;

        context.text(
                minecraft.font,
                Component.literal(duration),
                durationX,
                50,
                SECONDARY_TEXT_COLOR,
                false
        );
    }

    public static void render(
            GuiGraphicsExtractor context,
            Song song
    ) {

        String coverPath = song.getCoverPath();

        if (coverPath != null && !coverPath.equals(currentCoverPath)) {

            currentCoverTexture =
                    coverManager.getAlbumCover(coverPath);

            currentCoverPath = coverPath;
        }

        Minecraft minecraft = Minecraft.getInstance();

        context.pose().pushMatrix();

        context.pose().translate(
                Config.HUD_X,
                Config.HUD_Y
        );

        context.pose().scale(
                Config.HUD_SCALE,
                Config.HUD_SCALE
        );

        drawBackground(context);

        if (currentCoverTexture != null) {
            drawAlbumCover(context);
        }

        drawSongInfo(context, minecraft, song);
        drawProgressBar(context, song);
        drawTime(context, minecraft, song);

        context.pose().popMatrix();
    }
}