package org.lpt.betterrcon;

import net.minecraft.commands.Commands;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterRcon.MODID)
public class Config {
    public static final ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.ConfigValue<Integer> RCON_PORT;
    public static ForgeConfigSpec.ConfigValue<String> RCON_PASSWORD;
    public static ForgeConfigSpec.ConfigValue<Integer> COMMAND_PERMISSION;
    public static ForgeConfigSpec.ConfigValue<Integer> RCON_PLAYER_PERMISSION;
    public static ForgeConfigSpec.ConfigValue<String> RCON_PLAYER_NAME;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("general");
        RCON_PORT = builder
                .comment("Server port")
                .defineInRange("port", 25570, 1024, 40000);
        RCON_PASSWORD = builder
                .comment("Server password")
                .define("password", "Ch4ng3-M3");

        COMMAND_PERMISSION = builder
                .comment("Required permission for commands")
                .define("command_permission", Commands.LEVEL_OWNERS);

        RCON_PLAYER_PERMISSION = builder
                .comment("Permission level for the virtual RCON player")
                .define("rcon_player_permission", Commands.LEVEL_ADMINS);
        RCON_PLAYER_NAME = builder
                .comment("Name for the virtual RCON player")
                .define("rcon_player_name", "RCON");

        builder.pop();

        SPEC = builder.build();
    }
}
