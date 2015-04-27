package fr.theflogat.gearbox.api.util;

import net.minecraft.item.ItemStack;

public class Array {

	
	public static ItemStack[] toArray(ItemStack[] array, int start, int end) {
		ItemStack[] newA = new ItemStack[1 + end - start];
		for(int i = end; i<start; i++){
			int j = i-end;
			newA[j] = array[i];
		}
		return newA;
	}
	
	public static int[] toArray(int[] array, int start, int end) {
		int[] newA = new int[1 + end - start];
		for(int i = end; i<start; i++){
			int j = i-end;
			newA[j] = array[i];
		}
		return newA;
	}
}
