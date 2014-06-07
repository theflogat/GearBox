package fr.theflogat.gearbox.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import fr.theflogat.gearbox.api.Gears;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;
import fr.theflogat.gearbox.lib.config.Ids;

public class LoadItems {

	public static void init() {
		Gears.Resource = new ItemResources(Ids.actualResource);
		Gears.GearBox = new ItemGearbox(Ids.actualGearBox);
		Gears.Tome = new ItemTome(Ids.actualTome);
		Gears.MecaTool = new ItemMecaTool(Ids.actualMecaTool);
		
		GameRegistry.registerItem(Gears.Resource, ModLib.getId(Names.resource));
		GameRegistry.registerItem(Gears.GearBox, ModLib.getId(Names.gearbox));
		GameRegistry.registerItem(Gears.Tome, ModLib.getId(Names.tome));
		GameRegistry.registerItem(Gears.MecaTool, ModLib.getId(Names.tool));
		
		LanguageRegistry.addName(Gears.GearBox, "Gearbox");
		LanguageRegistry.addName(Gears.Tome, "Tome of Gearboxing");
		LanguageRegistry.addName(Gears.MecaTool, "Meca Tool");
		
		for(int i = 0; i<Names.namesResour.length; i++){
			LanguageRegistry.addName(new ItemStack(Gears.Resource, 1, i), Names.namesResour[i]);
		}
		
		MinecraftForge.setToolClass(Gears.MecaTool, "pickaxe", 3);
	}

}