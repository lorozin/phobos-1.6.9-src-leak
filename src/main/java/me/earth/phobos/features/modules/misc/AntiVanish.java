package me.earth.phobos.features.modules.misc;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import me.earth.phobos.event.events.PacketEvent;
import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.util.PlayerUtil;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiVanish extends Module {
  private final Queue<UUID> toLookUp = new ConcurrentLinkedQueue<>();
  
  public AntiVanish() {
    super("AntiVanish", "Notifies you when players vanish", Module.Category.MISC, true, false, false);
  }
  
  @SubscribeEvent
  public void onPacketReceive(PacketEvent.Receive event) {
    if (event.getPacket() instanceof SPacketPlayerListItem) {
      SPacketPlayerListItem sPacketPlayerListItem = (SPacketPlayerListItem)event.getPacket();
      if (sPacketPlayerListItem.getAction() == SPacketPlayerListItem.Action.UPDATE_LATENCY)
        for (SPacketPlayerListItem.AddPlayerData addPlayerData : sPacketPlayerListItem.getEntries()) {
          try {
            if (mc.getConnection().getPlayerInfo(addPlayerData.getProfile().getId()) == null)
              this.toLookUp.add(addPlayerData.getProfile().getId()); 
          } catch (Exception e) {
            e.printStackTrace();
            return;
          } 
        }  
    } 
  }
  
  public void onUpdate() {
    if (PlayerUtil.timer.passedS(5.0D)) {
      UUID lookUp = this.toLookUp.poll();
      if (lookUp != null) {
        try {
          String name = PlayerUtil.getNameFromUUID(lookUp);
          if (name != null)
            Command.sendMessage("§c" + name + " has gone into vanish."); 
        } catch (Exception exception) {}
        PlayerUtil.timer.reset();
      } 
    } 
  }
  
  public void onLogout() {
    this.toLookUp.clear();
  }
}
