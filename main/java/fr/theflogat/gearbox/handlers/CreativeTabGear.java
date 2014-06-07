package fr.theflogat.gearbox.handlers;

import fr.theflogat.gearbox.api.Gears;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabGear extends CreativeTabs{

	public CreativeTabGear(String par2Str) {
		super(par2Str);
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Gears.GearBench);
	}
}
