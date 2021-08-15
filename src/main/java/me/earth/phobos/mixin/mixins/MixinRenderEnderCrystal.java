package me.earth.phobos.mixin.mixins;

import me.earth.phobos.event.events.RenderEntityModelEvent;
import me.earth.phobos.features.modules.client.Colors;
import me.earth.phobos.features.modules.render.CrystalScale;
import me.earth.phobos.util.EntityUtil;
import me.earth.phobos.util.RenderUtil;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(value={RenderEnderCrystal.class})
public class MixinRenderEnderCrystal {
    @Shadow
    @Final
    private static ResourceLocation ENDER_CRYSTAL_TEXTURES;
    private static final ResourceLocation glint;

    @Redirect(method={"doRender"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderModelBaseHook(ModelBase model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (CrystalScale.INSTANCE.isEnabled()) {
            if (CrystalScale.INSTANCE.animateScale.getValue().booleanValue() && CrystalScale.INSTANCE.scaleMap.containsKey((Object)((EntityEnderCrystal)entity))) {
                GlStateManager.scale((float)CrystalScale.INSTANCE.scaleMap.get((Object)((EntityEnderCrystal)entity)).floatValue(), (float)CrystalScale.INSTANCE.scaleMap.get((Object)((EntityEnderCrystal)entity)).floatValue(), (float)CrystalScale.INSTANCE.scaleMap.get((Object)((EntityEnderCrystal)entity)).floatValue());
            } else {
                GlStateManager.scale((float)CrystalScale.INSTANCE.scale.getValue().floatValue(), (float)CrystalScale.INSTANCE.scale.getValue().floatValue(), (float)CrystalScale.INSTANCE.scale.getValue().floatValue());
            }
        }
        if (CrystalScale.INSTANCE.isEnabled() && CrystalScale.INSTANCE.wireframe.getValue().booleanValue()) {
            RenderEntityModelEvent event = new RenderEntityModelEvent(0, model, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            CrystalScale.INSTANCE.onRenderModel(event);
        }
        if (CrystalScale.INSTANCE.isEnabled() && CrystalScale.INSTANCE.chams.getValue().booleanValue()) {
            GL11.glPushAttrib(1048575);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(2960);
            if (CrystalScale.INSTANCE.rainbow.getValue().booleanValue()) {
                Color rainbowColor1 = CrystalScale.INSTANCE.colorSync.getValue() != false ? Colors.INSTANCE.getCurrentColor() : new Color(RenderUtil.getRainbow(CrystalScale.INSTANCE.speed.getValue() * 100, 0, (float)CrystalScale.INSTANCE.saturation.getValue().intValue() / 100.0f, (float)CrystalScale.INSTANCE.brightness.getValue().intValue() / 100.0f));
                Color rainbowColor = EntityUtil.getColor(entity, rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue(), CrystalScale.INSTANCE.alpha.getValue(), true);
                if (CrystalScale.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glEnable(10754);
                GL11.glColor4f((float)rainbowColor.getRed() / 255.0f, (float)rainbowColor.getGreen() / 255.0f, (float)rainbowColor.getBlue() / 255.0f, (float)CrystalScale.INSTANCE.alpha.getValue().intValue() / 255.0f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (CrystalScale.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
            } else if (CrystalScale.INSTANCE.xqz.getValue().booleanValue() && CrystalScale.INSTANCE.throughWalls.getValue().booleanValue()) {
                Color visibleColor;
                Color hiddenColor = EntityUtil.getColor(entity, CrystalScale.INSTANCE.hiddenRed.getValue(), CrystalScale.INSTANCE.hiddenGreen.getValue(), CrystalScale.INSTANCE.hiddenBlue.getValue(), CrystalScale.INSTANCE.hiddenAlpha.getValue(), true);
                Color color = visibleColor = EntityUtil.getColor(entity, CrystalScale.INSTANCE.red.getValue(), CrystalScale.INSTANCE.green.getValue(), CrystalScale.INSTANCE.blue.getValue(), CrystalScale.INSTANCE.alpha.getValue(), true);
                if (CrystalScale.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glEnable(10754);
                GL11.glColor4f((float)hiddenColor.getRed() / 255.0f, (float)hiddenColor.getGreen() / 255.0f, (float)hiddenColor.getBlue() / 255.0f, (float)CrystalScale.INSTANCE.alpha.getValue().intValue() / 255.0f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (CrystalScale.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
                GL11.glColor4f((float)visibleColor.getRed() / 255.0f, (float)visibleColor.getGreen() / 255.0f, (float)visibleColor.getBlue() / 255.0f, (float)CrystalScale.INSTANCE.alpha.getValue().intValue() / 255.0f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            } else {
                Color visibleColor;
                Color color = visibleColor = CrystalScale.INSTANCE.colorSync.getValue() != false ? Colors.INSTANCE.getCurrentColor() : EntityUtil.getColor(entity, CrystalScale.INSTANCE.red.getValue(), CrystalScale.INSTANCE.green.getValue(), CrystalScale.INSTANCE.blue.getValue(), CrystalScale.INSTANCE.alpha.getValue(), true);
                if (CrystalScale.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glEnable(10754);
                GL11.glColor4f((float)visibleColor.getRed() / 255.0f, (float)visibleColor.getGreen() / 255.0f, (float)visibleColor.getBlue() / 255.0f, (float)CrystalScale.INSTANCE.alpha.getValue().intValue() / 255.0f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (CrystalScale.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
            }
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
            if (CrystalScale.INSTANCE.glint.getValue().booleanValue()) {
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GlStateManager.enableAlpha();
                GlStateManager.color((float)1.0f, (float)0.0f, (float)0.0f, (float)0.13f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GlStateManager.disableAlpha();
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
            }
        } else {
            model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
        if (CrystalScale.INSTANCE.isEnabled()) {
            if (CrystalScale.INSTANCE.animateScale.getValue().booleanValue() && CrystalScale.INSTANCE.scaleMap.containsKey((Object)((EntityEnderCrystal)entity))) {
                GlStateManager.scale((float)(1.0f / CrystalScale.INSTANCE.scaleMap.get((Object)((EntityEnderCrystal)entity)).floatValue()), (float)(1.0f / CrystalScale.INSTANCE.scaleMap.get((Object)((EntityEnderCrystal)entity)).floatValue()), (float)(1.0f / CrystalScale.INSTANCE.scaleMap.get((Object)((EntityEnderCrystal)entity)).floatValue()));
            } else {
                GlStateManager.scale((float)(1.0f / CrystalScale.INSTANCE.scale.getValue().floatValue()), (float)(1.0f / CrystalScale.INSTANCE.scale.getValue().floatValue()), (float)(1.0f / CrystalScale.INSTANCE.scale.getValue().floatValue()));
            }
        }
    }

    static {
        glint = new ResourceLocation("textures/glint.png");
    }
}

