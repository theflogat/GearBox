package fr.theflogat.gearbox.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.theflogat.gearbox.api.Fuels;
import fr.theflogat.gearbox.api.Fuels.Burn;
import fr.theflogat.gearbox.tile.TEDynamo;
import fr.theflogat.gearbox.tile.slot.SlotFuel;
import fr.theflogat.gearbox.tile.slot.SlotReactant;

public class ContainerDynamo extends Container{
	
	private TEDynamo tile;
	InventoryPlayer inv;
	
	public ContainerDynamo(InventoryPlayer inv, TEDynamo tile2) {
		tile = tile2;
		this.inv = inv;
		int size = 18;
		
		for(int x = 0; x < 9; x++) {
			  this.addSlotToContainer(new Slot(inv, x, 8 + x * size, 142));
		}
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(inv, 9 + x + y * 9, 8 + x * size, 84 + y * size));
			}
		}
		
		
		addSlotToContainer(new SlotFuel(tile2, 0,  44,  35));
		addSlotToContainer(new SlotReactant(tile2, 1,  115,  35));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = getSlot(i);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack = slot.getStack();
			ItemStack result = itemstack.copy();

	        if(i >= 36) {
	        	if(!mergeItemStack(itemstack, 0, 36, false)) {
	        		return null;
	        	}
	       	} else {
	       		if(canItemFit(itemstack, i-36)){
	       			if(!mergeItemStack(itemstack, 36, 36 + tile.getSizeInventory(), false)) {
	       				return null;
	       			}
	       		} else{
	       			return null;
	       		}
	       	}

	        if(itemstack.stackSize == 0) {
	        	slot.putStack(null);
	        } else {
	        	slot.onSlotChanged();
	        }
	        slot.onPickupFromSlot(player, itemstack); 
	        return result;
		}
		return null;
	}

	private boolean canItemFit(ItemStack items, int i) {
		if((i==0 && Burn.getValue(items)>0) || (i==1 && Fuels.Reactant.getValue(items)>0))
			return true;
		
		return false;
	}
}
