package fr.theflogat.gearbox.handlers;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
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
import fr.theflogat.gearbox.compat.MineFactoryReloaded;
import fr.theflogat.gearbox.compat.ThermalExpansion;
import fr.theflogat.gearbox.lib.Names;
import fr.theflogat.gearbox.lib.config.Ids;

public class Recipes {

	private static final String[] toRemove = {
		"gearWood","gearStone","gearIron","gearGold","gearTin","gearCopper","gearTitanium","gearSilver","gearLead"
	};

	private static void compat(List list, Object rec){}

	public static void init() {
		OreDictionary.registerOre("ingotIron", Item.ingotIron);
		OreDictionary.registerOre("ingotGold", Item.ingotGold);

		OreDictionary.registerOre("blockIron", Block.blockIron);
		OreDictionary.registerOre("blockGold", Block.blockGold);
		OreDictionary.registerOre("blockObsidian", Block.obsidian);
		OreDictionary.registerOre("blockDiamond", Block.blockDiamond);
		OreDictionary.registerOre("blockRedstone", Block.blockRedstone);
		OreDictionary.registerOre("blockGlowstone", Block.glowStone);

		OreDictionary.registerOre("gemDiamond", Item.diamond);

		OreDictionary.registerOre("dustRedstone", Item.redstone);
		OreDictionary.registerOre("dustGlowstone", Item.glowstone);

		OreDictionary.registerOre("stickWood", Item.stick);
		OreDictionary.registerOre("slabStone", Block.stoneSingleSlab);

		for(int i = 0; i<Names.resources.length; i++){
			OreDictionary.registerOre(Names.resources[i], new ItemStack(Gears.Resource, 1, i));
		}

		Burn.setBurnable(new ItemStack(Item.coal), 20*20);
		Burn.setBurnable(new ItemStack(Item.coal,0,1), 20*20);
		Burn.setBurnable(new ItemStack(Item.blazeRod), 60*20);

		Reactant.setReactants(new ItemStack(Item.blazePowder), 30*20);
		Reactant.setReactants(new ItemStack(Item.eyeOfEnder), 60*20);

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
			Character.valueOf('G'), "ingotGold", Character.valueOf('I'), "blockIron", Character.valueOf('B'), Block.workbench,
			Character.valueOf('P'), Block.pistonBase
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 0), true, new Object[]{
			"BFB",
			"BDB",
			"III",
			Character.valueOf('I'), "ingotIron", Character.valueOf('F'), Block.furnaceIdle,Character.valueOf('B'), Block.fenceIron,
			Character.valueOf('D'), "blockDiamond"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 1), true, new Object[]{
			"BFB",
			"BDB",
			"ICI",
			Character.valueOf('I'), "ingotIron", Character.valueOf('F'), Block.furnaceIdle,Character.valueOf('B'), Block.fenceIron,
			Character.valueOf('C'), Item.magmaCream, Character.valueOf('D'), "blockDiamond"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 2), true, new Object[]{
			"BFB",
			"BDB",
			"ISI",
			Character.valueOf('I'), "ingotIron", Character.valueOf('F'), Block.furnaceIdle,Character.valueOf('B'), Block.fenceIron,
			Character.valueOf('S'), Item.snowball, Character.valueOf('D'), "blockDiamond"
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
			Item.book, "ingotIron","dustRedstone"
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
			Character.valueOf('D'), new ItemStack(Gears.GearDynamo), Character.valueOf('T'), Block.torchRedstoneActive
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
				Character.valueOf('I'), "shaftInputCombustion", Character.valueOf('F'), Block.furnaceIdle,Character.valueOf('B'), Block.fenceIron,
				Character.valueOf('S'), "shaftInputLiquidCool", Character.valueOf('D'), "blockDiamond"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 5), true, new Object[]{
				"ROR",
				"OBO",
				"ROR",
				Character.valueOf('R'), Item.blazeRod, Character.valueOf('O'), "shaftOutputBasic",Character.valueOf('B'), "blockObsidian",
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 6), true, new Object[]{
				"ROR",
				"OBO",
				"ROR",
				Character.valueOf('R'), Item.enderPearl, Character.valueOf('O'), "shaftOutputStrong",Character.valueOf('B'), "blockDiamond",
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
				Character.valueOf('I'), new ItemStack(Gears.Resource, 1, 0), Character.valueOf('F'), Block.furnaceIdle,Character.valueOf('B'), Block.fenceIron,
				Character.valueOf('S'), new ItemStack(Gears.Resource, 1, 2), Character.valueOf('D'), "blockDiamond"
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 5), true, new Object[]{
				"ROR",
				"OBO",
				"ROR",
				Character.valueOf('R'), Item.blazeRod, Character.valueOf('O'), new ItemStack(Gears.Resource, 1, 4),Character.valueOf('B'), "blockObsidian",
			}));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Gears.Resource, 1, 6), true, new Object[]{
				"ROR",
				"OBO",
				"ROR",
				Character.valueOf('R'), Item.enderPearl, Character.valueOf('O'), new ItemStack(Gears.Resource, 1, 5),Character.valueOf('B'), "blockDiamond",
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
		}
	}
}