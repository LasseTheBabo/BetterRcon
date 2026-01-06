package org.lpt.betterrcon;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = BetterRcon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.ConfigValue<Integer> RCON_PORT;
    public static ForgeConfigSpec.ConfigValue<String> RCON_PASSWORD;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("general");
        RCON_PORT = builder
                .comment("Server Port")
                .defineInRange("port", 25570, 1024, 40000);
        RCON_PASSWORD = builder
                .comment("Server Password")
                .define("password", "changeme");

        builder.pop();

        SPEC = builder.build();
    }
}
