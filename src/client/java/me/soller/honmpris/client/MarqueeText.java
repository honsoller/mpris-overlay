package me.soller.honmpris.client;

import net.minecraft.client.Minecraft;

public class MarqueeText {

    public static String fitText(
            Minecraft minecraft,
            String text,
            int maxWidth
    ) {

        if (minecraft.font.width(text) <= maxWidth) {
            return text;
        }

        String result = "";
        String dots = "...";

        int i = 0;

        while (i < text.length()) {

            String next = result + text.charAt(i) + dots;

            if (minecraft.font.width(next) > maxWidth) {
                break;
            }

            result += text.charAt(i);
            i++;
        }

        return result + dots;
    }

}