package fr.theflogat.gearbox.compat;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class ThermalExpansion {

	public static boolean te = true;
	public static Fluid fluidRedstone;
	public static Fluid fluidGlowstone;
	public static Fluid fluidEnder;
	public static Fluid fluidPyrotheum;
	public static Fluid fluidCryotheum;
	public static Fluid fluidCoal;

	public static ItemStack dustSulfur;
	public static ItemStack dustNiter;

	public static void init() {
		try {
			Class TEF = Class.forName("thermalexpansion.fluid.TEFluids");
			fluidRedstone = (Fluid) TEF.getField("fluidRedstone").get(TEF);
			fluidGlowstone = (Fluid) TEF.getField("fluidGlowstone").get(TEF);
			fluidEnder = (Fluid) TEF.getField("fluidEnder").get(TEF);
			fluidPyrotheum = (Fluid) TEF.getField("fluidPyrotheum").get(TEF);
			fluidCryotheum = (Fluid) TEF.getField("fluidCryotheum").get(TEF);
			fluidCoal = (Fluid) TEF.getField("fluidCoal").get(TEF);
			
			Class TEI = Class.forName("thermalexpansion.item.TEItems");
			dustSulfur = (ItemStack) TEI.getField("dustSulfur").get(TEI);
			dustNiter = (ItemStack) TEI.getField("dustNiter").get(TEI);
			System.out.println("Gearbox: Thermal Expansion 3 Module Activated");
		} catch (Exception e) {te = false;}
	}
}
