package org.lpt.betterrcon;

import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lpt.util.rcon.RconServer;

import java.io.IOException;

import static org.lpt.betterrcon.BetterRcon.LOGGER;
import static org.lpt.betterrcon.BetterRcon.rconServer;

@Mod.EventBusSubscriber(modid = BetterRcon.MODID)
public class ServerEvents {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        if (rconServer != null)
            return;

        try {
            rconServer = new RconServer(25570, "minceraft", new CommandHandler(event.getServer()));
            rconServer.open();
        } catch (Exception e) {
            LOGGER.error("Failed starting RconServer: {}", e.getMessage());
        }
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        if (rconServer == null)
            return;

        try {
            rconServer.close();
        } catch (IOException e) {
            LOGGER.error("Failed stopping RconServer: {}", e.getMessage());
        }
        rconServer = null;
    }
}
