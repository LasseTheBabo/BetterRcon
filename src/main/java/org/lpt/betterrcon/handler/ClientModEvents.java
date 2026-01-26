package org.lpt.betterrcon.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lpt.betterrcon.BetterRcon;
import org.lpt.betterrcon.command.CommandBetterRconClient;

@Mod.EventBusSubscriber(modid = BetterRcon.MODID, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(CommandBetterRconClient.register());
    }
}
