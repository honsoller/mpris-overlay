package me.soller.honmpris.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;


public class HonMPRISModMenu implements ModMenuApi {


    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        return ConfigScreen::new;

    }



    private static class ConfigScreen extends Screen {


        private final Screen parent;


        private EditBox scaleBox;
        private EditBox xBox;
        private EditBox yBox;
        private EditBox colorBox;
        private EditBox blacklistBox;



        protected ConfigScreen(Screen parent) {

            super(Component.literal("HonMPRIS Config"));

            this.parent = parent;

        }



        @Override
        protected void init() {


            Config cfg = Config.get();


            int left = this.width / 2 - 200;
            int right = this.width / 2 + 50;



            // Text "Render Overlay"

            this.addRenderableWidget(
                    new StringWidget(
                            left,
                            30,
                            150,
                            20,
                            Component.literal("Render Overlay"),
                            this.font
                    )
            );


            // Text "Hud Scale"

            this.addRenderableWidget(
                    new StringWidget(
                            left,
                            60,
                            150,
                            20,
                            Component.literal("Hud Scale"),
                            this.font
                    )
            );


            // Text "Pos X"

            this.addRenderableWidget(
                    new StringWidget(
                            left,
                            90,
                            150,
                            20,
                            Component.literal("Pos X"),
                            this.font
                    )
            );


            // Text "Pos Y"

            this.addRenderableWidget(
                    new StringWidget(
                            left,
                            120,
                            150,
                            20,
                            Component.literal("Pos Y"),
                            this.font
                    )
            );


            // Text "Accent Color"

            this.addRenderableWidget(
                    new StringWidget(
                            left,
                            150,
                            150,
                            20,
                            Component.literal("Accent Color"),
                            this.font
                    )
            );


            // Text "Blacklist"

            this.addRenderableWidget(
                    new StringWidget(
                            left,
                            180,
                            150,
                            20,
                            Component.literal("Blacklist"),
                            this.font
                    )
            );



            // Render Overlay Button

            this.addRenderableWidget(

                    Button.builder(

                                    Component.literal(
                                            cfg.renderOverlay
                                                    ? "Yes"
                                                    : "No"
                                    ),

                                    button -> {

                                        cfg.renderOverlay =
                                                !cfg.renderOverlay;


                                        button.setMessage(
                                                Component.literal(
                                                        cfg.renderOverlay
                                                                ? "Yes"
                                                                : "No"
                                                )
                                        );

                                    })

                            .bounds(
                                    right,
                                    30,
                                    120,
                                    20
                            )

                            .build()

            );



            // HUD Scale

            scaleBox = new EditBox(
                    this.font,
                    right,
                    60,
                    120,
                    20,
                    Component.empty()
            );

            scaleBox.setValue(
                    String.valueOf(cfg.hudScale)
            );

            this.addRenderableWidget(scaleBox);



            // Pos X

            xBox = new EditBox(
                    this.font,
                    right,
                    90,
                    120,
                    20,
                    Component.empty()
            );

            xBox.setValue(
                    String.valueOf(cfg.hudX)
            );

            this.addRenderableWidget(xBox);



            // Pos Y

            yBox = new EditBox(
                    this.font,
                    right,
                    120,
                    120,
                    20,
                    Component.empty()
            );

            yBox.setValue(
                    String.valueOf(cfg.hudY)
            );

            this.addRenderableWidget(yBox);



            // Accent Color

            colorBox = new EditBox(
                    this.font,
                    right,
                    150,
                    120,
                    20,
                    Component.empty()
            );

            colorBox.setValue(
                    Integer.toHexString(cfg.accentColor)
            );

            this.addRenderableWidget(colorBox);



            // Blacklist

            blacklistBox = new EditBox(
                    this.font,
                    right,
                    180,
                    120,
                    20,
                    Component.empty()
            );

            this.addRenderableWidget(blacklistBox);




            // Done

            this.addRenderableWidget(

                    Button.builder(

                                    CommonComponents.GUI_DONE,

                                    button -> {

                                        save();

                                        Config.save();

                                        onClose();

                                    })

                            .bounds(
                                    this.width / 2 - 100,
                                    this.height - 40,
                                    200,
                                    20
                            )

                            .build()

            );


        }



        private void save() {

            Config cfg = Config.get();


            try {

                cfg.hudScale =
                        Float.parseFloat(
                                scaleBox.getValue()
                        );


                cfg.hudX =
                        Integer.parseInt(
                                xBox.getValue()
                        );


                cfg.hudY =
                        Integer.parseInt(
                                yBox.getValue()
                        );


                cfg.accentColor =
                        (int) Long.parseLong(
                                colorBox.getValue(),
                                16
                        );


                if (!blacklistBox.getValue().isBlank()) {

                    cfg.blacklist.add(
                            blacklistBox.getValue()
                    );

                }


            } catch(Exception e) {

                e.printStackTrace();

            }

        }



        @Override
        public void onClose() {

            this.minecraft.gui.setScreen(parent);

        }


    }


}