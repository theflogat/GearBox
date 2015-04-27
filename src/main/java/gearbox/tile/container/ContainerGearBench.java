package fr.theflogat.gearbox.tile.container;

import fr.theflogat.gearbox.api.util.InventoryUtil;
import fr.theflogat.gearbox.tile.TEGearBench;
import fr.theflogat.gearbox.tile.slot.SlotGearboxBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

public class ContainerGearBench extends Container{
	
	private TEGearBench tile;
	InventoryPlayer inv;
	
	public ContainerGearBench(InventoryPlayer inv, TEGearBench te) {
		tile = te;
		this.inv = inv;
		int size = 18;
		
		for(int x = 0; x < 9; x++) {
			  this.addSlotToContainer(new Slot(inv, x, 50 + x * size, 232));
		}
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(inv, 9 + x + y * 9, 50 + x * size, 174 + y * size));
			}
		}
		
		for(int z = 0; z < 9; z++) {
			for(int a = 0; a < 9; a++){
				addSlotToContainer(new SlotGearboxBench(te, a + z * 9,  50 + a * size ,  3 + z * size ));
			}
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
//		Slot slot = getSlot(i);
//
//		if(slot != null && slot.getHasStack()) {
//			ItemStack itemstack = slot.getStack();
//			ItemStack result = itemstack.copy();
//
//	        if(i >= 36) {
//	        	if(!mergeItemStack(itemstack, 0, 36, false)) {
//	        		return null;
//	        	}
//	       	} else {
//	       		if(canItemFit(itemstack)){
//	       			if(!mergeItemStack(itemstack, 36, 36 + tile.getSizeInventory(), false)) {
//	       				return null;
//	       			}
//	       		} else{
//	       			return null;
//	       		}
//	       	}
//
//	        if(itemstack.stackSize == 0) {
//	        	slot.putStack(null);
//	        } else {
//	        	slot.onSlotChanged();
//	        }
//	        slot.onPickupFromSlot(player, itemstack); 
//	        return result;
//		}
		return null;
	}

	private boolean canItemFit(ItemStack items) {
		if(OreDictionary.getOreName(OreDictionary.getOreID(items)).contains("gear") || 
				OreDictionary.getOreName(OreDictionary.getOreID(items)).contains("shaft"))
			return true;
		
		
		return false;
	}
}