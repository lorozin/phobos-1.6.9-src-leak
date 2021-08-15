package me.earth.phobos.features.modules.player;

import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import net.minecraft.client.settings.KeyBinding;

public class AntiDelay extends Module {
  public Setting<Mode> mode = register(new Setting("Mode", Mode.SWORDCRYSTAL));
  
  public Setting<Integer> swordSlotSet = register(new Setting("SwordSlot", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(9)));
  
  public Setting<Integer> crystaSlotSet = register(new Setting("CrystalSlot", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(9)));
  
  private static AntiDelay instance;
  
  private boolean didSwitch = false;
  
  public AntiDelay() {
    super("AntiDelay", "Removes Hotbar Delay", Module.Category.PLAYER, false, false, false);
    instance = this;
  }
  
  public static AntiDelay getInstance() {
    if (instance == null)
      instance = new AntiDelay(); 
    return instance;
  }
  
  public void onUpdate() {
    if (fullNullCheck())
      return; 
  }
  
  public boolean processPressed(KeyBinding binding) {
    int number = 0;
    try {
      number = Integer.parseInt(binding.getDisplayName());
    } catch (Exception exception) {}
    return binding.isPressed();
  }
  
  private void doSwitch(int slot1, int slot2) {}
  
  private int getSwordSlot() {
    return ((Integer)this.swordSlotSet.getValue()).intValue() - 1;
  }
  
  private int getCrystalSlot() {
    return ((Integer)this.crystaSlotSet.getValue()).intValue() - 1;
  }
  
  public enum Mode {
    SWORDCRYSTAL;
  }
}
