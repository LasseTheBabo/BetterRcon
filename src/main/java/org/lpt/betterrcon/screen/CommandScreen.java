package org.lpt.betterrcon.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.lpt.util.rcon.RconClient;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class CommandScreen extends Screen {
    private EditBox inputField;
    private List<String> outputLines = new ArrayList<>();
    private List<String> history = new ArrayList<>();
    private int historyIndex = 0;

    private static final int maxOutputLines = 20;
    private static final int lineHeight = 12;

    private static final int INPUT_BOX_HEIGHT = 20;
    private static final int OUTPUT_BOX_HEIGHT = maxOutputLines * lineHeight;
    private static final int PADDING = 10;
    private static final int WIDTH = 500;
    private static final int HEIGHT = INPUT_BOX_HEIGHT + PADDING + OUTPUT_BOX_HEIGHT;

    private final RconClient rconClient;

    public CommandScreen(RconClient rconClient) {
        super(Component.literal("BetterRcon"));
        this.rconClient = rconClient;
    }

    @Override
    protected void init() {
        int startX = (width - WIDTH) / 2;
        int startY = (height - HEIGHT) / 2;

        inputField = new EditBox(
                this.font,
                startX, startY,
                WIDTH, INPUT_BOX_HEIGHT,
                Component.literal("Server Address")
        );

        this.addRenderableWidget(inputField);
        this.setFocused(inputField);
    }

    private void submitInput() {
        String text = inputField.getValue();
        if (text.isEmpty()) return;

        history.add(text);
        historyIndex = history.size() - 1;

        addOutput("> " + text);

        rconClient.sendCommand(text).forEach(this::addOutput);

        inputField.setValue("");
    }

    private void addOutput(String line) {
        outputLines.add(line);
        if (outputLines.size() > maxOutputLines) {
            outputLines.remove(0);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int startX = (width - WIDTH) / 2;
        int startY = (height - HEIGHT) / 2;

        this.renderBackground(graphics);

        int y = startY + INPUT_BOX_HEIGHT + PADDING;
        for (String line : outputLines) {
            graphics.drawString(this.font, line, startX, y, 0xFFFFFF);
            y += lineHeight;
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.onClose();
            return true;
        }

        if (inputField.isFocused()) {
            if (keyCode == GLFW.GLFW_KEY_ENTER) {
                submitInput();
                return true;
            }

            if (!history.isEmpty()) {
                if (keyCode == GLFW.GLFW_KEY_DOWN) {
                    historyIndex++;
                    if (historyIndex >= history.size()) historyIndex = 0;
                    inputField.setValue(history.get(historyIndex));
                }

                if (keyCode == GLFW.GLFW_KEY_UP) {
                    historyIndex--;
                    if (historyIndex < 0) historyIndex = history.size() - 1;
                    inputField.setValue(history.get(historyIndex));
                }
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
