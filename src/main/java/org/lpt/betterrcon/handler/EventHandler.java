package org.lpt.betterrcon.handler;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lpt.betterrcon.BetterRcon;
import org.lpt.betterrcon.command.CommandBetterRcon;

import static org.lpt.betterrcon.BetterRcon.*;

@Mod.EventBusSubscriber(modid = BetterRcon.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        startRconServer(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        stopRconServer();
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(CommandBetterRcon.register());
    }
}
