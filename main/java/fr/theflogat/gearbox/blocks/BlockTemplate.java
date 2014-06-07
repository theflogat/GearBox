package fr.theflogat.gearbox.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
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
import fr.theflogat.gearbox.tile.TER;
import fr.theflogat.gearbox.tile.TETemplate;

public class BlockTemplate extends BlockContainer{

	public BlockTemplate() {
		super(Material.iron);
		setBlockName(ModLib.getId(Names.template));
		setHardness(1F);
		setResistance(10F);
		setStepSound(soundTypeMetal);
		setCreativeTab(GearBox.gears);
		setBlockBounds(0, 0, 0, 1, 0.2F, 1);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isBlockSolid(IBlockAccess w, int x, int y, int z, int meta) {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube() {
		return false;
	}
	
	@Override
	public void breakBlock(World par1World, int par2,int par3,int par4,Block par5,int par6){
		TER tile =  (TER) par1World.getTileEntity(par2, par3, par4);
		tile.dropContents();
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.inventory.mainInventory[player.inventory.currentItem];
		if(heldItem!=null && heldItem.getItem() instanceof ItemGearbox){
			TileEntity dynamo = w.getTileEntity(x, y, z);
			if(dynamo!=null && dynamo instanceof TETemplate){
				((TETemplate)dynamo).applyTemplate(heldItem);
				return true;
			}
		}
		
		if(!w.isRemote){
			FMLNetworkHandler.openGui(player, GearBox.instance, GuiIds.tetemp, w, x, y, z);
		}
		
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TETemplate();
	}

	@SideOnly(Side.CLIENT)
	public IIcon side;

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ModLib.getAsset(Names.template));
		side = reg.registerIcon(ModLib.getAsset(Names.template) + "_side");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if(side==SIDE.TOP || side==SIDE.BOTTOM)
			return blockIcon;

		return this.side;
	}
}
