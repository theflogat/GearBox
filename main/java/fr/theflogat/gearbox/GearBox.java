package fr.theflogat.gearbox;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import fr.theflogat.gearbox.blocks.LoadBlocks;
import fr.theflogat.gearbox.compat.BloodMagic;
import fr.theflogat.gearbox.compat.BuildCraft;
import fr.theflogat.gearbox.compat.MineFactoryReloaded;
import fr.theflogat.gearbox.compat.ThermalExpansion;
import fr.theflogat.gearbox.events.EventRegister;
import fr.theflogat.gearbox.handlers.CreativeTabGear;
import fr.theflogat.gearbox.handlers.Recipes;
import fr.theflogat.gearbox.items.LoadItems;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.config.ConfigHandler;
import fr.theflogat.gearbox.proxies.CommonProxy;
import fr.theflogat.gearbox.tile.gui.GuiHandler;

@Mod(modid = ModLib.ModId, name = ModLib.ModName, version = ModLib.Version)
public class GearBox {
	
	@SidedProxy(clientSide = ModLib.ProxyLocation + ".ClientProxy", serverSide = ModLib.ProxyLocation + ".CommonProxy")
	public static CommonProxy proxy;
	
	@Instance(ModLib.ModId)
	public static GearBox instance;
	
	public static CreativeTabs gears = new CreativeTabGear("Gears");
	
	public static ToolMaterial meca = EnumHelper.addToolMaterial("Mecanic", 6, 1000, 0, 0, 20);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		LoadBlocks.init();
		LoadItems.init();
		new EventRegister();
		proxy.initRenderers();
		proxy.initSounds();
		NetworkRegistry.INSTANCE.registerGuiHandler(GearBox.instance, new GuiHandler());
	}
    
	@EventHandler
	public void init(FMLInitializationEvent event){
		
	}
	    
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		ThermalExpansion.init();
		BloodMagic.init();
		MineFactoryReloaded.init();
		BuildCraft.init();
		Recipes.init();
	}
}
