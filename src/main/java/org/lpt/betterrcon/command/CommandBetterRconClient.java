package org.lpt.betterrcon.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.lpt.betterrcon.Config;
import org.lpt.betterrcon.screen.ConnectScreen;

public class CommandBetterRconClient {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("betterrcon")
                .requires(source -> source.hasPermission(Config.COMMAND_PERMISSION.get()))
                .then(CommandBetterRconClient.ConnectCommand.register());
    }

    private static class ConnectCommand {
        static ArgumentBuilder<CommandSourceStack, ?> register() {
            return Commands.literal("connect")
                    .requires(source -> source.hasPermission(Config.COMMAND_PERMISSION.get()))
                    .executes(ctx -> {
                        Minecraft.getInstance().setScreen(new ConnectScreen());

                        return Command.SINGLE_SUCCESS;
                    });
        }
    }
}
