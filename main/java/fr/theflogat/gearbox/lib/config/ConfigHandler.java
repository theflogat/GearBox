package fr.theflogat.gearbox.lib.config;

import java.io.File;

import fr.theflogat.gearbox.lib.Names;
import net.minecraftforge.common.Configuration;

public class ConfigHandler {
	
	
	public static void init(File configFile) {
		Configuration config = new Configuration(configFile);
		config.load();
		//Blocks
		Ids.actualBench = config.getBlock(config.CATEGORY_BLOCK, Names.bench, Ids.baseBench).getInt();
		Ids.actualDynamo = config.getBlock(config.CATEGORY_BLOCK, Names.dynamo, Ids.baseDynamo).getInt();
		Ids.actualTemplate = config.getBlock(config.CATEGORY_BLOCK, Names.template, Ids.baseTemplate).getInt();
		//Items
		Ids.actualResource = config.getItem(config.CATEGORY_ITEM + " No Id bump", Names.resource, Ids.baseResource).getInt() - 256;
		Ids.actualGearBox = config.getItem(config.CATEGORY_ITEM + " No Id bump", Names.gearbox, Ids.baseGearBox).getInt() - 256;
		Ids.actualTome = config.getItem(config.CATEGORY_ITEM + " No Id bump", Names.tome, Ids.baseTome).getInt() - 256;
		Ids.actualMecaTool = config.getItem(config.CATEGORY_ITEM + " No Id bump", Names.tool, Ids.baseMecaTool).getInt() - 256;
		//Random
		Ids.oreDictToolTip = config.get("Random", "OreDictToolTip", false).getBoolean(false);
		Ids.doUseOtherGears = config.get("Random", "DoUseOtherModsGears(breaksBalance)", false).getBoolean(false);
		config.save();
	}
}
