package org.lpt.betterrcon;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.lpt.util.rcon.MessageHandler;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements MessageHandler {
    private final MinecraftServer server;

    public CommandHandler(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public String[] handleMessage(String s) {
        String playerName = "Rcon";
        CommandSourceStack virtualPlayerSource = new CommandSourceStack(
                CommandSource.NULL,
                Vec3.atCenterOf(BlockPos.ZERO),
                Vec2.ZERO,
                server.getLevel(Level.OVERWORLD),
                4,
                playerName,
                Component.literal(playerName),
                server,
                null
        );

        List<String> output = new ArrayList<>();
        virtualPlayerSource = virtualPlayerSource.withSource(new CommandSource() {
            @Override
            public void sendSystemMessage(Component component) {
                output.add(component.getString());
            }

            @Override
            public boolean acceptsSuccess() {
                return true;
            }

            @Override
            public boolean acceptsFailure() {
                return true;
            }

            @Override
            public boolean shouldInformAdmins() {
                return false;
            }
        });

        server.getCommands().performPrefixedCommand(virtualPlayerSource, s);

        return output.toArray(new String[0]);
    }
}
