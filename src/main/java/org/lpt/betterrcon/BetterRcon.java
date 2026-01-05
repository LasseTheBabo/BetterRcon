package org.lpt.betterrcon;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lpt.util.rcon.RconServer;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BetterRcon.MODID)
public class BetterRcon {
    public static final String MODID = "betterrcon";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static RconServer rconServer;

    public BetterRcon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
}
