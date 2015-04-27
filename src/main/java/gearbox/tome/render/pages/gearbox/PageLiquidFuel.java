package fr.theflogat.gearbox.tome.render.pages.gearbox;

import java.awt.Color;

import fr.theflogat.gearbox.api.Fuels;
import fr.theflogat.gearbox.api.Fuels.Burn;
import fr.theflogat.gearbox.api.util.Array;
import fr.theflogat.gearbox.tome.render.pages.PageRecipeFuel;
import fr.theflogat.gearbox.tome.render.pages.PageRecipeLiquidFuel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PageLiquidFuel extends PageRecipeLiquidFuel{
	
	FluidStack[] outputs;
	int[] val;
	
	public PageLiquidFuel(FluidStack[] start, int[] end) {
		outputs = start;
		val = end;
	}

	@Override
	public int[] getValues() {
		return val;
	}

	@Override
	public FluidStack[] getOutputs() {
		return outputs;
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}
}