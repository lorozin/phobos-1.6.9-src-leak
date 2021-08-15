
package me.earth.phobos.features.modules.render;

import me.earth.phobos.event.events.PacketEvent;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Animations extends Module {
    private final Setting < Mode > mode = this.register ( new Setting <> ( "OldAnimations" , Mode.OneDotEight ) );
    private final Setting < Swing > swing = this.register ( new Setting <> ( "Swing" , Swing.Mainhand ) );
    private final Setting < Boolean > slow = this.register ( new Setting <> ( "Slow" , false ) );

    public Animations ( ) {
        super ( "Animations" , "Change animations." , Module.Category.RENDER , true , false , false );
    }

    @Override
    public void onUpdate ( ) {
        if ( Animations.nullCheck ( ) ) {
            return;
        }
        if ( this.swing.getValue ( ) == Swing.Offhand ) {
            Animations.mc.player.swingingHand = EnumHand.OFF_HAND;
        }
        if ( this.mode.getValue ( ) == Mode.OneDotEight && (double) Animations.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9 ) {
            Animations.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            Animations.mc.entityRenderer.itemRenderer.itemStackMainHand = Animations.mc.player.getHeldItemMainhand ( );
        }
    }

    @Override
    public void onEnable ( ) {
        if ( this.slow.getValue ( ) ) {
            Animations.mc.player.addPotionEffect ( new PotionEffect ( MobEffects.MINING_FATIGUE , 255000 ) );
        }
    }

    @Override
    public void onDisable ( ) {
        if ( this.slow.getValue ( ) ) {
            Animations.mc.player.removePotionEffect ( MobEffects.MINING_FATIGUE );
        }
    }

    @SubscribeEvent
    public void onPacketSend ( PacketEvent.Send send ) {
        Object t = send.getPacket ( );
        if ( t instanceof CPacketAnimation ) {
            if ( this.swing.getValue ( ) == Swing.Disable ) {
                send.setCanceled ( true );
            }
        }
    }

    private enum Mode {
        Normal,
        OneDotEight

    }

    private enum Swing {
        Mainhand,
        Offhand,
        Disable

    }
}
