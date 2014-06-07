package fr.theflogat.gearbox.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import fr.theflogat.gearbox.tile.TEGearBench;
import fr.theflogat.gearbox.tile.TETemplate;
import fr.theflogat.gearbox.tile.slot.SlotFixed;
import fr.theflogat.gearbox.tile.slot.SlotGearboxBench;

public class ContainerTemplate extends Container{
	
	private TETemplate tile;
	InventoryPlayer inv;

	public ContainerTemplate(InventoryPlayer inv, TETemplate te) {
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
				addSlotToContainer(new SlotFixed(te, a + z * 9,  50 + a * size ,  3 + z * size ));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(tile.xCoord, tile.yCoord, tile.zCoord) <= 64;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return null;
	}

}
