package fr.theflogat.gearbox.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FuelLiquid {
	public static class Hot{
		public static FluidStack[] heat = new FluidStack[64];
		public static int pos = 0;
		public static int[] val = new int[64];

		public static void setHotLiquid(FluidStack items, int value) {
			FluidStack toAdd = new FluidStack(items.getFluid(),0);
			heat[pos] = toAdd;
			val[pos] = value;
			pos++;
		}

		public static int getDurationValue(FluidStack items) {
			FluidStack toCheck = new FluidStack(items.getFluid(),0);
			for(int i = 0; i<heat.length; i++){
				if(toCheck.isFluidEqual(heat[i]))
					return val[i];
			}
			return -1;
		}
	}

	public static class Cold{
		public static FluidStack[] coolant = new FluidStack[64];
		public static int pos = 0;
		public static int[] val = new int[64];
		public static boolean[] eff = new boolean[64];

		public static void setCoolant(FluidStack items, int value) {
			FluidStack toAdd = new FluidStack(items.getFluid(),0);
			coolant[pos] = toAdd;
			val[pos] = value;
			eff[pos] = true;
			pos++;
		}

		public static void setCoolant(FluidStack items, int value, boolean canBeEfficient) {
			FluidStack toAdd = new FluidStack(items.getFluid(),0);
			coolant[pos] = toAdd;
			val[pos] = value;
			eff[pos] = canBeEfficient;
			pos++;
		}
		
		public static int getDurationValue(FluidStack items) {
			FluidStack toCheck = new FluidStack(items.getFluid(),0);
			for(int i = 0; i<coolant.length; i++){
				if(toCheck.isFluidEqual(coolant[i]))
					return val[i];
			}
			return -1;
		}
		
		public static boolean canBeEfficient(FluidStack items) {
			FluidStack toCheck = new FluidStack(items.getFluid(),0);
			for(int i = 0; i<coolant.length; i++){
				if(toCheck.isFluidEqual(coolant[i]))
					return eff[i];
			}
			return true;
		}
	}

	public static class Reacting{
		public static FluidStack[] reacting = new FluidStack[64];
		public static int pos = 0;
		public static int[] val = new int[64];

		public static void setReacting(FluidStack items, int value) {
			FluidStack toAdd = new FluidStack(items.getFluid(),0);
			reacting[pos] = toAdd;
			val[pos] = value;
			pos++;
		}

		public static int getDurationValue(FluidStack items) {
			FluidStack toCheck = new FluidStack(items.getFluid(),0);
			for(int i = 0; i<reacting.length; i++){
				if(toCheck.isFluidEqual(reacting[i]))
					return val[i];
			}
			return -1;
		}
	}
}
