package fr.theflogat.gearbox.compat;

import net.minecraftforge.fluids.Fluid;

public class BuildCraft {
	
	public static Fluid fluidOil;
	public static Fluid fluidFuel;
	
	public static boolean bc = true;
	
	public static void init() {
		try{
			Class BCE = Class.forName("buildcraft.BuildCraftEnergy");
			fluidOil = (Fluid) BCE.getField("fluidOil").get(BCE);
			fluidFuel = (Fluid) BCE.getField("fluidFuel").get(BCE);
			System.out.println("Gearbox: Buildcraft Module Activated");
		}catch(Exception e){bc = false;}
	}
}
