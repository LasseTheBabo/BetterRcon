package org.lpt.betterrcon.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.lpt.betterrcon.BetterRcon;

public class CommandBetterRcon {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("betterrcon")
                .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                .then(StartRconCommand.register())
                .then(StopRconCommand.register());
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
}
