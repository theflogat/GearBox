package fr.theflogat.gearbox.tome.render.pages.gearbox;

import java.awt.Color;

import fr.theflogat.gearbox.api.Fuels;
import fr.theflogat.gearbox.api.Fuels.Burn;
import fr.theflogat.gearbox.api.util.Array;
import fr.theflogat.gearbox.tome.render.pages.PageRecipeFuel;
import net.minecraft.item.ItemStack;

public class PageFuel extends PageRecipeFuel{
	
	ItemStack[] outputs;
	int[] val;
	
	public PageFuel(ItemStack[] start, int[] end) {
		outputs = start;
		val = end;
	}

	@Override
	public int[] getValues() {
		return val;
	}

	@Override
	public ItemStack[] getOutputs() {
		return outputs;
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}
}