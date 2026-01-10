package org.lpt.betterrcon.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.lpt.betterrcon.BetterRcon;
import org.lpt.betterrcon.Config;

public class CommandBetterRcon {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("betterrcon")
                .requires(source -> source.hasPermission(Config.COMMAND_PERMISSION.get()))
                .then(InfoCommand.register())
                .then(StartRconCommand.register())
                .then(StopRconCommand.register());
    }

    private static class InfoCommand {
        static ArgumentBuilder<CommandSourceStack, ?> register() {
            return Commands.literal("info")
                    .requires(source -> source.hasPermission(Config.COMMAND_PERMISSION.get()))
                    .executes(ctx -> {
                        CommandSourceStack source = ctx.getSource();

                        boolean serverRunning = BetterRcon.rconServer != null;
                        int port = Config.RCON_PORT.get();
                        String name = Config.RCON_PLAYER_NAME.get();

                        ChatFormatting statusColor = serverRunning ? ChatFormatting.GREEN : ChatFormatting.RED;
                        String statusText = serverRunning ? "running" : "stopped";

                        MutableComponent info = Component.literal("Status: ")
                                .append(Component.literal(statusText).withStyle(statusColor))
                                .append("\nPort: ")
                                .append(String.valueOf(port))
                                .append("\nRCON name: ")
                                .append(name);

                        source.sendSuccess(() -> info, true);
                        return Command.SINGLE_SUCCESS;
                    });
        }
    }

    private static class StartRconCommand {
        static ArgumentBuilder<CommandSourceStack, ?> register() {
            return Commands.literal("start")
                    .requires(source -> source.hasPermission(Config.COMMAND_PERMISSION.get()))
                    .executes(ctx -> {
                        CommandSourceStack source = ctx.getSource();

                        Component text = BetterRcon.startRconServer(source.getServer());
                        source.sendSuccess(() -> text, true);
                        return Command.SINGLE_SUCCESS;
                    });
        }
    }

    private static class StopRconCommand {
        static ArgumentBuilder<CommandSourceStack, ?> register() {
            return Commands.literal("stop")
                    .requires(source -> source.hasPermission(Config.COMMAND_PERMISSION.get()))
                    .executes(ctx -> {
                        CommandSourceStack source = ctx.getSource();

                        Component text = BetterRcon.stopRconServer();
                        source.sendSuccess(() -> text, true);
                        return Command.SINGLE_SUCCESS;
                    });
        }
    }
}
