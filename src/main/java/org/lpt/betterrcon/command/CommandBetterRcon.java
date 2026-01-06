package org.lpt.betterrcon.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.lpt.betterrcon.BetterRcon;
import org.lpt.betterrcon.Config;

public class CommandBetterRcon {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("betterrcon")
                .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                .then(StartRconCommand.register())
                .then(StopRconCommand.register())
                .then(InfoCommand.register());
    }

    private static class StartRconCommand {
        static ArgumentBuilder<CommandSourceStack, ?> register() {
            return Commands.literal("start")
                    .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
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
                    .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                    .executes(ctx -> {
                        CommandSourceStack source = ctx.getSource();

                        Component text = BetterRcon.stopRconServer();
                        source.sendSuccess(() -> text, true);
                        return Command.SINGLE_SUCCESS;
                    });
        }
    }

    private static class InfoCommand {
        static ArgumentBuilder<CommandSourceStack, ?> register() {
            return Commands.literal("info")
                    .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                    .executes(ctx -> {
                        CommandSourceStack source = ctx.getSource();

                        boolean serverRunning = BetterRcon.rconServer != null;
                        int port = Config.RCON_PORT.get();
                        String name = Config.RCON_PLAYER_NAME.get();

                        Component text = Component.empty()
                                .append("Status: " + (serverRunning ? "running" : "stopped"))
                                .append("\nPort: " + port)
                                .append("\nRcon-Player Name: " + name);

                        source.sendSuccess(() -> text, true);
                        return Command.SINGLE_SUCCESS;
                    });
        }
    }
}
