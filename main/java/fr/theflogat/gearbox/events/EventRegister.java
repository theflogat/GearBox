package fr.theflogat.gearbox.events;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.theflogat.gearbox.lib.config.Ids;

public class EventRegister {
	public EventRegister(){
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void handleItemTooltipEvent(ItemTooltipEvent event){
		if(Ids.oreDictToolTip){
			if(!OreDictionary.getOreName(OreDictionary.getOreID(event.itemStack)).equals("Unknown")){
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
					try{
						event.toolTip.add(EnumChatFormatting.DARK_GREEN + OreDictionary.getOreName(OreDictionary.getOreID(event.itemStack)));
					}catch(Exception e){}
				} else {
					event.toolTip.add(EnumChatFormatting.RESET + "Hold " + EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + "Shift" + EnumChatFormatting.RESET + " for OreDictionnary Name");
				}
			}
		}
		/*if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			event.toolTip.add(event.itemStack.getItem().getUnlocalizedName());*/
	}
}
