package fr.theflogat.gearbox.items;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import fr.theflogat.gearbox.GearBox;
import fr.theflogat.gearbox.lib.GuiIds;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTome extends Item{

	public ItemTome(int par1) {
		super(par1);
		setUnlocalizedName(ModLib.getId(Names.tome));
		setMaxStackSize(1);
		setCreativeTab(GearBox.gears);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack items, World w, EntityPlayer player) {
		FMLNetworkHandler.openGui(player, GearBox.instance, GuiIds.tome, w, 0, 0, 0);
		
		return items;
	}
	
	@Override
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ModLib.getAsset(Names.tome));
	}
}
