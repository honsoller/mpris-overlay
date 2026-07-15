package me.soller.honmpris.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.NativeImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URL;

public class AlbumCoverManager {

    public Identifier getAlbumCover(String cover) {

        BufferedImage image = loadImage(cover);

        if (image == null) {
            return null;
        }

        return createTexture(image);
    }


    private BufferedImage loadImage(String cover) {

        try {

            if (cover.startsWith("file://")) {

                URI uri = URI.create(cover);
                File file = new File(uri);

                if (!file.exists()) {
                    return null;
                }

                return ImageIO.read(file);
            } else if (cover.startsWith("http://") || cover.startsWith("https://")) {

                URL url = URI.create(cover).toURL();
                return ImageIO.read(url);

            } else {

                return ImageIO.read(
                        getClass().getResourceAsStream(
                                "/assets/honmpris/" + cover
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Identifier createTexture(BufferedImage image) {

        try {

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            ImageIO.write(image, "png", output);

            NativeImage nativeImage = NativeImage.read(
                    new ByteArrayInputStream(output.toByteArray())
            );

            DynamicTexture texture = new DynamicTexture(
                    () -> "honmpris_cover",
                    nativeImage
            );

            Identifier id = Identifier.fromNamespaceAndPath(
                    "honmpris",
                    "cover"
            );

            Minecraft.getInstance()
                    .getTextureManager()
                    .register(id, texture);

            return id;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}