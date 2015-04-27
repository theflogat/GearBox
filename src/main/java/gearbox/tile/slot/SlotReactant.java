package fr.theflogat.gearbox.tile.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import fr.theflogat.gearbox.api.Fuels.Reactant;

public class SlotReactant extends Slot{

	public SlotReactant(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack items) {
		return Reactant.getValue(items)>0;
	}
}
