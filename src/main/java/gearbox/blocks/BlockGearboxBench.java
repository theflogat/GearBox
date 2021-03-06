package fr.theflogat.gearbox.blocks;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.theflogat.gearbox.GearBox;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.lib.GuiIds;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;
import fr.theflogat.gearbox.lib.SIDE;
import fr.theflogat.gearbox.tile.TEGearBench;

public class BlockGearboxBench extends BlockContainer{

	public BlockGearboxBench() {
		super(Material.iron);
		setBlockName(ModLib.getId(Names.bench));
		setStepSound(soundTypeMetal);
		setHardness(5);
		setResistance(5);
		setCreativeTab(GearBox.gears);
	}
	
	@Override
	public void breakBlock(World par1World, int x,int y,int z,Block block,int meta){
		TEGearBench tile =  (TEGearBench) par1World.getTileEntity(x, y, z);
		tile.dropContents();
		super.breakBlock(par1World, x, y, z, block, meta);
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
		ItemStack heldItem = player.inventory.mainInventory[player.inventory.currentItem];
		if(heldItem!=null && heldItem.getItem() instanceof ItemGearbox){
			TileEntity bench = w.getTileEntity(x, y, z);
			if(bench!=null && bench instanceof TEGearBench){
				player.inventory.mainInventory[player.inventory.currentItem] = ((TEGearBench)bench).tryToInsetBox(heldItem);
				return true;
			}
		}
		
		if(!w.isRemote){
			FMLNetworkHandler.openGui(player, GearBox.instance, GuiIds.gearBench, w, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TEGearBench();
	}
	
	@SideOnly(Side.CLIENT)
	IIcon[] text = new IIcon[3];
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		text[0] = reg.registerIcon(ModLib.getAsset(Names.bench)+"Top");
		text[1] = reg.registerIcon(ModLib.getAsset(Names.bench)+"Bottom");
		text[2] = reg.registerIcon(ModLib.getAsset(Names.bench)+"Side");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return side == SIDE.TOP ? text[0] : (side == SIDE.BOTTOM ? text[1] : text[2]);
	}

}
