package org.lpt.betterrcon.chat;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TextFormatter {
    public static MutableComponent failed(String text) {
        return Component.literal(text).withStyle(ChatFormatting.RED);
    }

    public static MutableComponent success(String text) {
        return Component.literal(text).withStyle(ChatFormatting.GREEN);
    }
}
