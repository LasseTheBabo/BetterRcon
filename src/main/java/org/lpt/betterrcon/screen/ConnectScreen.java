package org.lpt.betterrcon.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.lpt.util.rcon.RconClient;

public class ConnectScreen extends Screen {
    private EditBox addressBox;
    private EditBox passwordBox;
    private EditBox portBox;
    private Button connectButton;
    private boolean failedConnection = false;
    private String lastAddress = "";
    private String lastPassword = "";

    private static final int PADDING = 10;
    private static final int LEFT_SIDE = 150;
    private static final int RIGHT_SIDE = 80;
    private static final int COMPONENT_HEIGHT = 20;
    private static final int WIDTH = LEFT_SIDE + PADDING + RIGHT_SIDE;
    private static final int HEIGHT = COMPONENT_HEIGHT * 2 + PADDING;

    public ConnectScreen() {
        super(Component.literal("BetterRcon Connect"));
    }

    @Override
    protected void init() {
        int startX = (width - WIDTH) / 2;
        int startY = (height - HEIGHT) / 2;

        addressBox = new EditBox(
                this.font,
                startX, startY,
                LEFT_SIDE, COMPONENT_HEIGHT,
                Component.literal("Address")
        );

        passwordBox = new EditBox(
                this.font,
                startX, startY + COMPONENT_HEIGHT + PADDING,
                LEFT_SIDE, COMPONENT_HEIGHT,
                Component.literal("Password")
        );

        portBox = new EditBox(
                this.font,
                startX + LEFT_SIDE + PADDING, startY,
                RIGHT_SIDE, COMPONENT_HEIGHT,
                Component.literal("Port")
        );

        portBox.setFilter(text -> {
            if (text.isEmpty()) return true;

            if (!text.matches("\\d+")) return false;

            try {
                int value = Integer.parseInt(text);
                return value >= 1 && value <= 65535;
            } catch (NumberFormatException e) {
                return false;
            }
        });

        connectButton = Button.builder(
                Component.literal("Connect"),
                button -> tryConnect()
        )
                .pos(startX + LEFT_SIDE + PADDING, startY + COMPONENT_HEIGHT + PADDING)
                .size(RIGHT_SIDE, COMPONENT_HEIGHT)
                .build();

        portBox.setMaxLength(5);
        portBox.setValue("25570");

        this.addRenderableWidget(addressBox);
        this.addRenderableWidget(passwordBox);
        this.addRenderableWidget(portBox);
        this.addRenderableWidget(connectButton);

        System.out.println(width);
        System.out.println(height);
    }

    private void tryConnect() {
        String address = addressBox.getValue();
        String password = passwordBox.getValue();
        int port = Integer.parseInt(portBox.getValue());

        if (address.isEmpty() || password.isEmpty()) {
            return;
        }

        RconClient rconClient;
        try {
            rconClient = RconClient.connect(address, port);

            if (rconClient.authenticate(password)) {
                failedConnection = true;
            }
        } catch (Exception e) {
            rconClient = null;
        }

        if (rconClient == null) {
            failedConnection = true;
            return;
        }

        Minecraft.getInstance().setScreen(new CommandScreen(rconClient));
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int startX = (width - WIDTH) / 2;
        int startY = (height - HEIGHT) / 2;

        this.renderBackground(graphics);

        String address = addressBox.getValue();
        String password = passwordBox.getValue();

        if (address.isEmpty()) {
            graphics.drawString(this.font, "Address", startX + 4, startY + 6, 0xA0A0A0);
        }

        if (password.isEmpty()) {
            graphics.drawString(this.font, "Password", startX + 4, startY + 6 + COMPONENT_HEIGHT + PADDING, 0xA0A0A0);
        }

        if (!address.equals(lastAddress) || !password.equals(lastPassword)) {
            failedConnection = false;
        }

        if (failedConnection) {
            graphics.drawString(this.font, "Connection failed", startX, startY + COMPONENT_HEIGHT + PADDING + COMPONENT_HEIGHT + PADDING, 0xFF0000);
        }

        lastAddress = address;
        lastPassword = password;
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
