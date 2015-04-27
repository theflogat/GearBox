package fr.theflogat.gearbox.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import fr.theflogat.gearbox.GearBox;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.lib.GuiIds;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;
import fr.theflogat.gearbox.tile.TEDynamo;
import fr.theflogat.gearbox.tile.TER;

public class BlockDynamo extends BlockContainer{

	public BlockDynamo() {
		super(Material.iron);
		setBlockName(ModLib.getId(Names.dynamo));
		setCreativeTab(GearBox.gears);
		setHardness(10);
		setResistance(50);
		setBlockBounds(2*0.0625F, 2*0.0625F, 2*0.0625F, 14*0.0625F, 14*0.0625F, 14*0.0625F);
	}
	
//	@Override
//	public int getRenderType() {
//		return -1;
//	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		super.onBlockAdded(w, x, y, z);
//		 if (!w.isRemote)
//	        {
//	            int l = w.getBlockId(x, y, z - 1);
//	            int i1 = w.getBlockId(x, y, z + 1);
//	            int j1 = w.getBlockId(x - 1, y, z);
//	            int k1 = w.getBlockId(x + 1, y, z);
//	            byte b0 = 3;
//
//	            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
//	            {
//	                b0 = 3;
//	            }
//
//	            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
//	            {
//	                b0 = 2;
//	            }
//
//	            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
//	            {
//	                b0 = 5;
//	            }
//
//	            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
//	            {
//	                b0 = 4;
//	            }
//
//	            w.setBlockMetadataWithNotify(x, y, z, b0, 2);
//	        }
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase player, ItemStack items) {
		 int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	        if (l == 0)
	        {
	            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
	        }

	        if (l == 1)
	        {
	            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
	        }

	        if (l == 2)
	        {
	            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
	        }

	        if (l == 3)
	        {
	            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
	        }
	}
	
	@Override
	public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
		TER tile =  (TER) w.getTileEntity(x, y, z);
		tile.dropContents();
		super.breakBlock(w, x, y, z, block, meta);
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.inventory.mainInventory[player.inventory.currentItem];
		if(heldItem!=null && heldItem.getItem() instanceof ItemGearbox){
			TileEntity dynamo = w.getTileEntity(x, y, z);
			if(dynamo!=null && dynamo instanceof TEDynamo){
				player.inventory.mainInventory[player.inventory.currentItem] = ((TEDynamo)dynamo).setGearbox(heldItem);
			}
		/*}else if(heldItem!=null && FluidContainerRegistry.isContainer(heldItem) && heldItem.getUnlocalizedName().toLowerCase().contains("bucket")){
			if(w.getBlockTileEntity(x, y, z)!=null && w.getBlockTileEntity(x, y, z) instanceof TEDynamo){

				TEDynamo dynamo = (TEDynamo) w.getBlockTileEntity(x, y, z);
				if(dynamo.canFill(null, FluidContainerRegistry.getFluidForFilledItem(heldItem).getFluid())){
					dynamo.fill(null, FluidContainerRegistry.getFluidForFilledItem(heldItem), true);
					player.inventory.mainInventory[player.inventory.currentItem]=new ItemStack(Item.bucketEmpty,1);
				}
			}*/
		}else{
			if(!w.isRemote)
				FMLNetworkHandler.openGui(player, GearBox.instance, GuiIds.dynamo, w, x, y, z);
		}
		return true;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ModLib.getAsset(Names.dynamo));
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TEDynamo();
	}
}
