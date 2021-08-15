
package me.earth.phobos.features.modules.player;

import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import me.earth.phobos.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public
class MCX
        extends Module {
    private final Setting < Boolean > antiFriend = this.register ( new Setting <> ( "AntiFriend" , true ) );

    public
    MCX ( ) {
        super ( "MCX" , "Middle Click XP" , Module.Category.PLAYER , false , false , false );
    }

    @Override
    public
    void onUpdate ( ) {
        {
            if ( Mouse.isButtonDown ( 2 ) ) {
                {
                    this.throwXP ( );
                }
            }
        }
    }

    private
    void throwXP ( ) {
        boolean offhand;
        RayTraceResult result;
        if ( this.antiFriend.getValue ( ) && ( result = MCX.mc.objectMouseOver ) != null && result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit instanceof EntityPlayer )
            return;
        int xpSlot = InventoryUtil.findHotbarBlock ( ItemExpBottle.class );
        offhand = MCX.mc.player.getHeldItemOffhand ( ).getItem ( ) == Items.EXPERIENCE_BOTTLE;
        if ( xpSlot != - 1 || offhand ) {
            int oldslot = MCX.mc.player.inventory.currentItem;
            if ( ! offhand ) {
                InventoryUtil.switchToHotbarSlot ( xpSlot , false );
            }
            MCX.mc.playerController.processRightClick ( MCX.mc.player , MCX.mc.world , offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND );
            if ( ! offhand ) {
                InventoryUtil.switchToHotbarSlot ( oldslot , false );
            }
        }
    }
}