package me.earth.phobos.mixin.mixins;

import java.awt.Color;
import me.earth.phobos.event.events.RenderEntityModelEvent;
import me.earth.phobos.features.modules.client.Colors;
import me.earth.phobos.features.modules.render.Chams;
import me.earth.phobos.features.modules.render.ESP;
import me.earth.phobos.features.modules.render.Skeleton;
import me.earth.phobos.util.EntityUtil;
import me.earth.phobos.util.RenderUtil;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderLivingBase.class})
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T> {
  public MixinRenderLivingBase(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
    super(renderManagerIn);
  }
  
  @Redirect(method = {"renderModel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
  private void renderModelHook(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    boolean cancel = false;
    if (Skeleton.getInstance().isEnabled() || ESP.getInstance().isEnabled()) {
      RenderEntityModelEvent event = new RenderEntityModelEvent(0, modelBase, entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (Skeleton.getInstance().isEnabled())
        Skeleton.getInstance().onRenderModel(event); 
      if (ESP.getInstance().isEnabled()) {
        ESP.getInstance().onRenderModel(event);
        if (event.isCanceled())
          cancel = true; 
      } 
    } 
    if (Chams.getInstance().isEnabled() && entityIn instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)(Chams.getInstance()).colored.getValue()).booleanValue()) {
      GL11.glPushAttrib(1048575);
      GL11.glDisable(3008);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(1.5F);
      GL11.glEnable(2960);
      if (((Boolean)(Chams.getInstance()).rainbow.getValue()).booleanValue()) {
        Color rainbowColor1 = ((Boolean)(Chams.getInstance()).colorSync.getValue()).booleanValue() ? Colors.INSTANCE.getCurrentColor() : new Color(RenderUtil.getRainbow(((Integer)(Chams.getInstance()).speed.getValue()).intValue() * 100, 0, ((Integer)(Chams.getInstance()).saturation.getValue()).intValue() / 100.0F, ((Integer)(Chams.getInstance()).brightness.getValue()).intValue() / 100.0F));
        Color rainbowColor = EntityUtil.getColor(entityIn, rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue(), ((Integer)(Chams.getInstance()).alpha.getValue()).intValue(), true);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(10754);
        GL11.glColor4f(rainbowColor.getRed() / 255.0F, rainbowColor.getGreen() / 255.0F, rainbowColor.getBlue() / 255.0F, ((Integer)(Chams.getInstance()).alpha.getValue()).intValue() / 255.0F);
        modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
      } else if (((Boolean)(Chams.getInstance()).xqz.getValue()).booleanValue()) {
        Color hiddenColor = ((Boolean)(Chams.getInstance()).colorSync.getValue()).booleanValue() ? Colors.INSTANCE.getCurrentColor() : EntityUtil.getColor(entityIn, ((Integer)(Chams.getInstance()).red.getValue()).intValue(), ((Integer)(Chams.getInstance()).green.getValue()).intValue(), ((Integer)(Chams.getInstance()).blue.getValue()).intValue(), ((Integer)(Chams.getInstance()).alpha.getValue()).intValue(), true);
        Color visibleColor = ((Boolean)(Chams.getInstance()).colorSync.getValue()).booleanValue() ? Colors.INSTANCE.getCurrentColor() : EntityUtil.getColor(entityIn, ((Integer)(Chams.getInstance()).red.getValue()).intValue(), ((Integer)(Chams.getInstance()).green.getValue()).intValue(), ((Integer)(Chams.getInstance()).blue.getValue()).intValue(), ((Integer)(Chams.getInstance()).alpha.getValue()).intValue(), true);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(10754);
        GL11.glColor4f(hiddenColor.getRed() / 255.0F, hiddenColor.getGreen() / 255.0F, hiddenColor.getBlue() / 255.0F, ((Integer)(Chams.getInstance()).alpha.getValue()).intValue() / 255.0F);
        modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glColor4f(visibleColor.getRed() / 255.0F, visibleColor.getGreen() / 255.0F, visibleColor.getBlue() / 255.0F, ((Integer)(Chams.getInstance()).alpha.getValue()).intValue() / 255.0F);
        modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      } else {
        Color visibleColor = ((Boolean)(Chams.getInstance()).colorSync.getValue()).booleanValue() ? Colors.INSTANCE.getCurrentColor() : EntityUtil.getColor(entityIn, ((Integer)(Chams.getInstance()).red.getValue()).intValue(), ((Integer)(Chams.getInstance()).green.getValue()).intValue(), ((Integer)(Chams.getInstance()).blue.getValue()).intValue(), ((Integer)(Chams.getInstance()).alpha.getValue()).intValue(), true);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(10754);
        GL11.glColor4f(visibleColor.getRed() / 255.0F, visibleColor.getGreen() / 255.0F, visibleColor.getBlue() / 255.0F, ((Integer)(Chams.getInstance()).alpha.getValue()).intValue() / 255.0F);
        modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
      } 
      GL11.glEnable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glEnable(3008);
      GL11.glPopAttrib();
    } else if (!cancel) {
      modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    } 
  }
  
  @Inject(method = {"doRender"}, at = {@At("HEAD")})
  public void doRenderPre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
    if (Chams.getInstance().isEnabled() && !((Boolean)(Chams.getInstance()).colored.getValue()).booleanValue() && entity != null) {
      GL11.glEnable(32823);
      GL11.glPolygonOffset(1.0F, -1100000.0F);
    } 
  }
  
  @Inject(method = {"doRender"}, at = {@At("RETURN")})
  public void doRenderPost(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
    if (Chams.getInstance().isEnabled() && !((Boolean)(Chams.getInstance()).colored.getValue()).booleanValue() && entity != null) {
      GL11.glPolygonOffset(1.0F, 1000000.0F);
      GL11.glDisable(32823);
    } 
  }
}
