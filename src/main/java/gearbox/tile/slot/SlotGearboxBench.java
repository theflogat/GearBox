package fr.theflogat.gearbox.tile.slot;

import fr.theflogat.gearbox.api.util.GridUtil;
import fr.theflogat.gearbox.items.ItemResources;
import fr.theflogat.gearbox.lib.config.Ids;
import fr.theflogat.gearbox.tile.TEGearBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SlotGearboxBench extends Slot{

	public SlotGearboxBench(TEGearBench inv, int par2, int par3, int par4) {
		super(inv, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack items) {
		if((Ids.g() && OreDictionary.getOreName(OreDictionary.getOreID(items)).contains("shaft"))||
				(items.getItem() instanceof ItemResources && items.getUnlocalizedName().contains("shaft"))){
			return true;
		} else if((Ids.g() && OreDictionary.getOreName(OreDictionary.getOreID(items)).contains("gear"))||
				(items.getItem() instanceof ItemResources && items.getUnlocalizedName().contains("gear"))) {
			if(GridUtil.connects(inventory, getSlotIndex())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
		ItemStack items = inventory.getStackInSlot(getSlotIndex());
		if(items==null)
			return false;
		if((Ids.g() && OreDictionary.getOreName(OreDictionary.getOreID(items)).contains("shaft"))||
				(items.getItem() instanceof ItemResources && items.getUnlocalizedName().contains("shaft"))){
			if(GridUtil.connectsNoSh(inventory, getSlotIndex())){
				return false;
			}
		}else if((Ids.g() && OreDictionary.getOreName(OreDictionary.getOreID(items)).contains("gear"))||
				(items.getItem() instanceof ItemResources && items.getUnlocalizedName().contains("gear"))){
			if(!GridUtil.canRetireGear(inventory, getSlotIndex())){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
