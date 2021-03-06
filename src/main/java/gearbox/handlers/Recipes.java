package fr.theflogat.gearbox.handlers;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.theflogat.gearbox.api.FuelLiquid.Cold;
import fr.theflogat.gearbox.api.FuelLiquid.Hot;
import fr.theflogat.gearbox.api.FuelLiquid.Reacting;
import fr.theflogat.gearbox.api.Fuels.Burn;
import fr.theflogat.gearbox.api.Fuels.Reactant;
import fr.theflogat.gearbox.api.Gears;
import fr.theflogat.gearbox.compat.BloodMagic;
import fr.theflogat.gearbox.compat.BuildCraft;
import fr.theflogat.gearbox.compat.MineFactoryReloaded;
import fr.theflogat.gearbox.compat.ThermalExpansion;
import fr.theflogat.gearbox.lib.Names;
import fr.theflogat.gearbox.lib.config.Ids;

public class Recipes {

//	private static final String[] toRemove = {
//		"gearWood","gearStone","gearIron","gearGold","gearTin","gearCopper","gearTitanium","gearSilver","gearLead"
//	};

	private static void compat(List list, Object rec){}

	public static void init() {
		OreDictionary.registerOre("ingotIron", Items.iron_ingot);
		OreDictionary.registerOre("ingotGold", Items.gold_ingot);

		OreDictionary.registerOre("blockIron", Blocks.iron_block);
		OreDictionary.registerOre("blockGold", Blocks.gold_block);
		OreDictionary.registerOre("blockObsidian", Blocks.obsidian);
		OreDictionary.registerOre("blockDiamond", Blocks.diamond_block);
		OreDictionary.registerOre("blockRedstone", Blocks.redstone_block);
		OreDictionary.registerOre("blockGlowstone", Blocks.glowstone);

		OreDictionary.registerOre("gemDiamond", Items.diamond);

		OreDictionary.registerOre("dustRedstone", Items.redstone);
		OreDictionary.registerOre("dustGlowstone", Items.glowstone_dust);

		OreDictionary.registerOre("stickWood", Items.stick);
		OreDictionary.registerOre("slabStone", Blocks.stone_slab);

		for(int i = 0; i<Names.resources.length; i++){
			OreDictionary.registerOre(Names.resources[i], new ItemStack(Gears.Resource, 1, i));
		}

		Burn.setBurnable(new ItemStack(Items.coal), 20*20);
		Burn.setBurnable(new ItemStack(Items.coal,0,1), 20*20);
		Burn.setBurnable(new ItemStack(Items.blaze_rod), 60*20);

		Reactant.setReactants(new ItemStack(Items.blaze_powder), 30*20);
		Reactant.setReactants(new ItemStack(Items.ender_eye), 60*20);

		Hot.setHotLiquid(new FluidStack(FluidRegistry.LAVA, 0), 20*20);

		Cold.setCoolant(new FluidStack(FluidRegistry.WATER, 0), 1, false);
		
		if(ThermalExpansion.te){
			Reacting.setReacting(new FluidStack(ThermalExpansion.fluidRedstone, 0), 20*60);
			Reacting.setReacting(new FluidStack(ThermalExpansion.fluidGlowstone, 0), 20*60);
			
			Hot.setHotLiquid(new FluidStack(ThermalExpansion.fluidPyrotheum, 0), 20*120);
			Hot.setHotLiquid(new FluidStack(ThermalExpansion.fluidCoal, 0), 20*60);
			
			Cold.setCoolant(new FluidStack(ThermalExpansion.fluidEnder, 0), 20*30);
			Cold.setCoolant(new FluidStack(ThermalExpansion.fluidCryotheum, 0), 20*60);
			
			Reactant.setReactants(ThermalExpansion.dustNiter, 20*30);
			Reactant.setReactants(ThermalExpansion.dustSulfur, 20*60);
		}
		if(BloodMagic.bm){
			Hot.setHotLiquid(new FluidStack(BloodMagic.lifeEssenceFluid, 0), 20*60);
		}
		if(MineFactoryReloaded.mfr){
			Reactant.setReactants(new ItemStack(MineFactoryReloaded.meatNuggetCookedItem), 20*10);
			Reactant.setReactants(new ItemStack(MineFactoryReloaded.meatIngotCookedItem), 20*90);
			
			Reacting.setReacting(new FluidStack(MineFactoryReloaded.milk, 0), 20*15);
			Reacting.setReacting(new FluidStack(MineFactoryReloaded.mobessence, 0), 20*80);
			
			Hot.setHotLiquid(new FluidStack(MineFactoryReloaded.biofuel, 0), 20*80);
		}
		if(BuildCraft.bc){
			Hot.setHotLiquid(new FluidStack(BuildCraft.fluidFuel, 0), 20*120);
			Hot.setHotLiquid(new FluidStack(BuildCraft.fluidOil, 0), 20*15);
		}
		//List list = CraftingManager.getInstance().getRecipeList();
		//
		//
		//		for(int i = 0; i<list.size(); i++){
		//			String id = "";
		//			if(list.get(i) instanceof ShapedRecipes){
		//				ShapedRecipes recipe = (ShapedRecipes) list.get(i);
		//				id = OreDictionary.getOreName(OreDictionary.getOreID(recipe.getRecipeOutput()));
		//				System.out.println(id);
		//				for(String toRem : toRemove){
		//					if(id.equalsIgnoreCase(toRem))
		//						list.remove(recipe);
		//				}
		//			}
		//
		//			if(list.get(i) instanceof ShapelessRecipes){
		//				ShapelessRecipes recipe = (ShapelessRecipes) list.get(i);
		//				id = OreDictionary.getOreName(OreDictionary.getOreID(recipe.getRecipeOutput()));
		//				System.out.println(id);
		//				for(String toRem : toRemove){
		//					if(id.equalsIgnoreCase(toRem))
		//						list.remove(recipe);
		//				}
		//			}
		//			
		//			compat(list, list.get(i));
		//		}

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.GearBench, 1), true, new Object[]{
			"GGG",
			"IBI",
			"IPI",
			Character.valueOf('G'), "ingotGold", Character.valueOf('I'), "blockIron", Character.valueOf('B'), Blocks.crafting_table,
			Character.valueOf('P'), Blocks.piston
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 0), true, new Object[]{
			"BFB",
			"BDB",
			"III",
			Character.valueOf('I'), "ingotIron", Character.valueOf('F'), Blocks.furnace,Character.valueOf('B'), Blocks.iron_bars,
			Character.valueOf('D'), "blockDiamond"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 1), true, new Object[]{
			"BFB",
			"BDB",
			"ICI",
			Character.valueOf('I'), "ingotIron", Character.valueOf('F'), Blocks.furnace,Character.valueOf('B'), Blocks.iron_bars,
			Character.valueOf('C'), Items.magma_cream, Character.valueOf('D'), "blockDiamond"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 2), true, new Object[]{
			"BFB",
			"BDB",
			"ISI",
			Character.valueOf('I'), "ingotIron", Character.valueOf('F'), Blocks.furnace,Character.valueOf('B'), Blocks.iron_bars,
			Character.valueOf('S'), Items.snowball, Character.valueOf('D'), "blockDiamond"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 4), true, new Object[]{
			"BBB",
			"BGB",
			"IDI",
			Character.valueOf('I'), "blockIron", Character.valueOf('G'), "blockGold",Character.valueOf('B'), "dustRedstone",
			Character.valueOf('D'), "gemDiamond"
		}));
		//Wooden Gears
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 7), true, new Object[]{
			"III",
			"ISI",
			"III",
			Character.valueOf('S'), "ingotIron", Character.valueOf('I'), "stickWood"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 4, 8), true, new Object[]{
			" I ",
			"ISI",
			" I ",
			Character.valueOf('S'), "gemDiamond", Character.valueOf('I'), "gearEfficientWood"
		}));
		//Stone Gears
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 10), true, new Object[]{
			"III",
			"ISI",
			"III",
			Character.valueOf('S'), "ingotIron", Character.valueOf('I'), "stone"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 10), true, new Object[]{
			"III",
			"SIS",
			"III",
			Character.valueOf('S'), "ingotIron", Character.valueOf('I'), "cobblestone"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 2, 11), true, new Object[]{
			" I ",
			"ISI",
			" I ",
			Character.valueOf('S'), "gemDiamond", Character.valueOf('I'), "gearEfficientStone"
		}));
		//Iron Gears
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 13), true, new Object[]{
			"III",
			"IDI",
			"III",
			Character.valueOf('I'), "ingotIron", Character.valueOf('S'), "blockIron", Character.valueOf('D'), "gemDiamond"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, 14), new Object[]{
			"gemDiamond", "gearEfficientIron", "gemDiamond", "gemDiamond", "gemDiamond"
		}));	
		//Gold Gears
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 16), true, new Object[]{
			"III",
			"IDI",
			"III",
			Character.valueOf('I'), "ingotGold", Character.valueOf('S'), "blockIron", Character.valueOf('D'), "gemDiamond"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, 17), new Object[]{
			"gemDiamond", "gearEfficientGold", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		//Tin Gears
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 19), true, new Object[]{
			"III",
			"IDI",
			"III",
			Character.valueOf('I'), "ingotTin", Character.valueOf('S'), "blockIron", Character.valueOf('D'), "gemDiamond"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, 20),  new Object[]{
			"gemDiamond", "gearEfficientTin", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		//Copper Gears
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 22), true, new Object[]{
			"III",
			"IDI",
			"III",
			Character.valueOf('I'), "ingotCopper", Character.valueOf('S'), "blockIron", Character.valueOf('D'), "gemDiamond"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, 23), new Object[]{
			"gemDiamond", "gearEfficientCopper", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		//Titanium Gears
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 25), true, new Object[]{
			"III",
			"IDI",
			"III",
			Character.valueOf('I'), "ingotTitanium", Character.valueOf('S'), "blockIron", Character.valueOf('D'), "gemDiamond"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, 26), new Object[]{
			"gemDiamond", "gearEfficientTitanium", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		//Silver Gears
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 28), true, new Object[]{
			"III",
			"IDI",
			"III",
			Character.valueOf('I'), "ingotSilver", Character.valueOf('S'), "blockIron", Character.valueOf('D'), "gemDiamond"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, 29), new Object[]{
			"gemDiamond", "gearEfficientSilver", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		//Lead Gears
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 31), true, new Object[]{
			"III",
			"IDI",
			"III",
			Character.valueOf('I'), "ingotLead", Character.valueOf('S'), "blockIron", Character.valueOf('D'), "gemDiamond"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, 32), new Object[]{
			"gemDiamond", "gearEfficientLead", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		
		//Tome
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Tome, 1, 0), new Object[]{
			Items.book, "ingotIron","dustRedstone"
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, 32), new Object[]{
			"gemDiamond", "gearEfficientLead", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.GearTemplate, 1, 0), true, new Object[]{
			"RIR",
			"SSS",
			Character.valueOf('S'), "slabStone", Character.valueOf('R'), "dustRedstone", Character.valueOf('I'), "ingotIron"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.MecaTool, 1, 0), true, new Object[]{
			"RIR",
			"SDS",
			"STS",
			Character.valueOf('S'), "ingotIron", Character.valueOf('R'), "blockRedstone", Character.valueOf('I'), "gemDiamond",
			Character.valueOf('D'), new ItemStack(Gears.GearDynamo), Character.valueOf('T'), Blocks.redstone_torch
		}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, Names.resources.length-4), new Object[]{
			"gemDiamond", "shaftInputCombustionEfficient", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, Names.resources.length-3), new Object[]{
			"gemDiamond", "shaftInputLiquidHotEfficient", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, Names.resources.length-2), new Object[]{
			"gemDiamond", "shaftInputLiquidCoolEfficient", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Gears.Resource, 1, Names.resources.length-1), new Object[]{
			"gemDiamond", "shaftInputLiquidReactantEfficient", "gemDiamond", "gemDiamond", "gemDiamond"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, Names.resources.length-8), true, new Object[]{
			"GIG",
			"ISI",
			"GIG",
			Character.valueOf('S'), "shaftInputCombustion", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'),
			"blockGlowstone"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, Names.resources.length-7), true, new Object[]{
			"GIG",
			"ISI",
			"GIG",
			Character.valueOf('S'), "shaftInputLiquidHot", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'),
			"blockGlowstone"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, Names.resources.length-6), true, new Object[]{
			"GIG",
			"ISI",
			"GIG",
			Character.valueOf('S'), "shaftInputLiquidCool", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'),
			"blockGlowstone"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, Names.resources.length-5), true, new Object[]{
			"GIG",
			"ISI",
			"GIG",
			Character.valueOf('S'), "shaftInputLiquidReactant", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'),
			"blockGlowstone"
		}));

		if(Ids.g()){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 15), true, new Object[]{
				"III",
				"IDI",
				"III",
				Character.valueOf('S'), "gearIron", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));	

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 18), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), "gearGold", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 21), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), "gearTin", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 24), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), "gearCopper", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 27), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), "gearTitanium", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 30), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), "gearSilver", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 33), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), "gearLead", Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.GearDynamo), true, new Object[]{
				" R ",
				"GBG",
				"RFR",
				Character.valueOf('G'), "gearIron", Character.valueOf('B'), "blockGold",Character.valueOf('R'), "dustRedstone",
				Character.valueOf('F'), "gearPolishedGold"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 3), true, new Object[]{
				"BFB",
				"B B",
				"ISI",
				Character.valueOf('I'), "shaftInputCombustion", Character.valueOf('F'), Blocks.furnace,Character.valueOf('B'), Blocks.iron_bars,
				Character.valueOf('S'), "shaftInputLiquidCool", Character.valueOf('D'), "blockDiamond"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 5), true, new Object[]{
				"ROR",
				"OBO",
				"ROR",
				Character.valueOf('R'), Items.blaze_rod, Character.valueOf('O'), "shaftOutputBasic",Character.valueOf('B'), "blockObsidian",
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 6), true, new Object[]{
				"ROR",
				"OBO",
				"ROR",
				Character.valueOf('R'), Items.ender_pearl, Character.valueOf('O'), "shaftOutputStrong",Character.valueOf('B'), "blockDiamond",
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 12), true, new Object[]{
				" I ",
				"ISI",
				" I ",
				Character.valueOf('S'), "gearStone", Character.valueOf('I'), "blockRedstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 9), true, new Object[]{
				"ISI",
				Character.valueOf('S'), "gearWood", Character.valueOf('I'), "blockRedstone"
			}));

		} else {
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 15), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 13), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));	

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 18), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 16), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 21), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 19), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 24), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 22), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 27), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 25), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 30), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 28), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 33), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 31), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'), "blockGlowstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.GearDynamo), true, new Object[]{
				" R ",
				"GBG",
				"RFR",
				Character.valueOf('G'), new ItemStack(Gears.Resource, 1, 13), Character.valueOf('B'), "blockGold",Character.valueOf('R'), "dustRedstone",
				Character.valueOf('F'), new ItemStack(Gears.Resource, 1, 16)
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 3), true, new Object[]{
				"BFB",
				"B B",
				"ISI",
				Character.valueOf('I'), new ItemStack(Gears.Resource, 1, 0), Character.valueOf('F'), Blocks.furnace,Character.valueOf('B'), Blocks.iron_bars,
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 2), Character.valueOf('D'), "blockDiamond"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 5), true, new Object[]{
				"ROR",
				"OBO",
				"ROR",
				Character.valueOf('R'), Items.blaze_rod, Character.valueOf('O'), new ItemStack(Gears.Resource, 1, 4),Character.valueOf('B'), "blockObsidian",
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 6), true, new Object[]{
				"ROR",
				"OBO",
				"ROR",
				Character.valueOf('R'), Items.ender_pearl, Character.valueOf('O'), new ItemStack(Gears.Resource, 1, 5),Character.valueOf('B'), "blockDiamond",
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 12), true, new Object[]{
				" I ",
				"ISI",
				" I ",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 10), Character.valueOf('I'), "blockRedstone"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 9), true, new Object[]{
				"ISI",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 7), Character.valueOf('I'), "blockRedstone"
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 30), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 0), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'),
				"blockGlowstone"
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 30), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 1), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'),
				"blockGlowstone"
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 30), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 2), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'),
				"blockGlowstone"
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 30), true, new Object[]{
				"GIG",
				"ISI",
				"GIG",
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 3), Character.valueOf('I'), "blockRedstone", Character.valueOf('G'),
				"blockGlowstone"
			}));
		}
	}
}