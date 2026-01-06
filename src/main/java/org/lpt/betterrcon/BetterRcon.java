package org.lpt.betterrcon;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lpt.betterrcon.chat.TextFormatter;
import org.lpt.betterrcon.handler.CommandHandler;
import org.lpt.util.rcon.RconServer;
import org.slf4j.Logger;

import java.io.IOException;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BetterRcon.MODID)
public class BetterRcon {
    public static final String MODID = "betterrcon";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static RconServer rconServer;

    public BetterRcon(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    public static Component startRconServer(MinecraftServer server) {
        if (rconServer != null) {
            return TextFormatter.failed("Server already running");
        }

        try {
            rconServer = new RconServer(
                    Config.RCON_PORT.get(),
                    Config.RCON_PASSWORD.get(),
                    new CommandHandler(server)
            );
            rconServer.open();
            return TextFormatter.success("Server started");
        } catch (IOException e) {
            rconServer = null;
            LOGGER.error("Failed starting RconServer:", e);
            return TextFormatter.failed("Failed starting Server. See logs");
        }
    }

    public static Component stopRconServer() {
        if (rconServer == null) {
            return TextFormatter.failed("Server not running");
        }

        try {
            rconServer.close();
            rconServer = null;
            return TextFormatter.success("Server stopped");
        } catch (IOException e) {
            LOGGER.error("Failed stopping RconServer: {}", e.getMessage());
            return TextFormatter.failed("Failed stopping Server. See logs");
        }
    }
}
