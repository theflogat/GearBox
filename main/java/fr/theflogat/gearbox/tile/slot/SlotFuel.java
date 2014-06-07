package fr.theflogat.gearbox.tile.slot;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.theflogat.gearbox.api.Fuels.Burn;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFuel extends Slot{

	public SlotFuel(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack items) {
		return Burn.getValue(items)>0;
	}
}
