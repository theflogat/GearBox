package fr.theflogat.gearbox.compat;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MineFactoryReloaded {

	public static boolean mfr = true; 
	public static Item meatIngotCookedItem;
	public static Item meatNuggetCookedItem;
	public static Fluid mobessence;
	public static Fluid biofuel;
	public static Fluid milk;

	public static void init(){
		try{
			Class MFRM = Class.forName("powercrystals.minefactoryreloaded.MineFactoryReloadedCore");
			meatIngotCookedItem = (Item) MFRM.getField("meatIngotCookedItem").get(MFRM);
			meatNuggetCookedItem = (Item) MFRM.getField("meatNuggetCookedItem").get(MFRM);

			biofuel = FluidRegistry.getFluid("biofuel");
			milk = FluidRegistry.getFluid("milk");
			mobessence = FluidRegistry.getFluid("mobessence");
			System.out.println("Gearbox: Mine Factory Reloaded Module Activated");
		}catch(Exception e){mfr = false;}
	}
}