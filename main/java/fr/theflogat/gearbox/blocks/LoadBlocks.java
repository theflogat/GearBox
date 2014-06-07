package fr.theflogat.gearbox.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import fr.theflogat.gearbox.api.Gears;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;
import fr.theflogat.gearbox.lib.config.Ids;
import fr.theflogat.gearbox.tile.TEDynamo;
import fr.theflogat.gearbox.tile.TEGearBench;
import fr.theflogat.gearbox.tile.TETemplate;

public class LoadBlocks {

	public static void init() {
		
		Gears.GearBench = new BlockGearboxBench(Ids.actualBench);
		Gears.GearDynamo = new BlockDynamo(Ids.actualDynamo);
		Gears.GearTemplate = new BlockTemplate(Ids.actualTemplate);
		
		GameRegistry.registerBlock(Gears.GearBench, ModLib.getId(Names.bench));
		GameRegistry.registerBlock(Gears.GearDynamo, ModLib.getId(Names.dynamo));
		GameRegistry.registerBlock(Gears.GearTemplate, ModLib.getId(Names.template));
		
		GameRegistry.registerTileEntity(TEGearBench.class, ModLib.getTileId(Names.bench));
		GameRegistry.registerTileEntity(TEDynamo.class, ModLib.getTileId(Names.dynamo));
		GameRegistry.registerTileEntity(TETemplate.class, ModLib.getTileId(Names.template));
		
		LanguageRegistry.addName(Gears.GearBench, "Gearbox Crafting Table");
		LanguageRegistry.addName(Gears.GearDynamo, "Dynamo");
		LanguageRegistry.addName(Gears.GearTemplate, "Template Provider");
	}

}
