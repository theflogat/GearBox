package fr.theflogat.gearbox.compat;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class BloodMagic {

	public static boolean bm = true;
	public static Fluid lifeEssenceFluid;


	public static void init() {
		try{
			Class BMM = Class.forName("WayofTime.alchemicalWizardry.AlchemicalWizardry");
			lifeEssenceFluid = (Fluid) BMM.getField("lifeEssenceFluid").get(BMM);
			System.out.println("Gearbox: Blood Magic Module Activated");
		}catch(Exception e){bm = false;}
	}
}