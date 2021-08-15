
package me.earth.phobos.features.modules.misc;

import me.earth.phobos.features.modules.Module;
import net.minecraft.util.EnumHand;

public class OffhandSwing extends Module {
	
    public OffhandSwing() {
        super("OffhandSwing", "sexc", Module.Category.MISC, true, false, false);
    }
	
	    public void update() {

        mc.player.swingingHand = EnumHand.OFF_HAND;

       }

}