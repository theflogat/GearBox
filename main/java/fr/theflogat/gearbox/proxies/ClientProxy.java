package fr.theflogat.gearbox.proxies;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.theflogat.gearbox.api.Gears;
import fr.theflogat.gearbox.render.BlockDynamoRenderer;
import fr.theflogat.gearbox.render.RenderDynamo;
import fr.theflogat.gearbox.render.RenderGearBox;
import fr.theflogat.gearbox.render.RenderItemGearbox;
import fr.theflogat.gearbox.tile.TEDynamo;

public class ClientProxy extends CommonProxy{
	@Override
	public void initRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TEDynamo.class, new RenderDynamo());
		RenderingRegistry.registerBlockHandler(new BlockDynamoRenderer());
		//MinecraftForgeClient.registerItemRenderer(Gears.GearBox.itemID, (IItemRenderer) new RenderItemGearbox());
	}
	
	@Override
	public void initSounds() {
		
	}
}
