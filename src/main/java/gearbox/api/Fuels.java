package fr.theflogat.gearbox.api;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class Fuels{
	public static class Reactant {
		public static ItemStack[] reactants = new ItemStack[64];
		public static int pos = 0;
		public static int[] val = new int[64];
		public static boolean[] eff = new boolean[64];

		public static void setReactants(ItemStack items, int value) {
			ItemStack toAdd = new ItemStack(items.getItem(),0,items.getItemDamage());
			reactants[pos] = toAdd;
			val[pos] = value;
			eff[pos] = true;
			pos++;
		}

		public static void setReactants(ItemStack items, int value, boolean canBeEfficient) {
			ItemStack toAdd = new ItemStack(items.getItem(),0,items.getItemDamage());
			reactants[pos] = toAdd;
			val[pos] = value;
			eff[pos] = canBeEfficient;
			pos++;
		}
		
		public static int getValue(ItemStack items) {
			ItemStack toCheck = new ItemStack(items.getItem(),0,items.getItemDamage());
			for(int i = 0; i<reactants.length; i++){
				if(ItemStack.areItemStacksEqual(toCheck, reactants[i]))
					return val[i];
			}
			return -1;
		}
		
		public boolean canBeEfficient(ItemStack items) {
			ItemStack toCheck = new ItemStack(items.getItem(),0,items.getItemDamage());
			for(int i = 0; i<reactants.length; i++){
				if(ItemStack.areItemStacksEqual(toCheck, reactants[i]))
					return eff[i];
			}
			return true;
		}
	}

	public static class Burn{
		public static ItemStack[] burnables = new ItemStack[64];
		public static int pos = 0;
		public static int[] val = new int[64];

		public static void setBurnable(ItemStack items, int ticks) {
			ItemStack toAdd = new ItemStack(items.getItem(),0,items.getItemDamage());
			burnables[pos] = toAdd;
			val[pos] = ticks;
			pos++;
		}

		public static int getValue(ItemStack items) {
			ItemStack toCheck = new ItemStack(items.getItem(),0,items.getItemDamage());
			for(int i = 0; i<burnables.length; i++){
				if(ItemStack.areItemStacksEqual(toCheck, burnables[i]))
					return val[i];
			}
			return GameRegistry.getFuelValue(items)>0 ? GameRegistry.getFuelValue(items) : -1;
		}
	}
}