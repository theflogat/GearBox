package fr.theflogat.gearbox.blocks;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.IFluidContainerItem;
import fr.theflogat.gearbox.GearBox;
import fr.theflogat.gearbox.api.util.WorldUtil;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.lib.GuiIds;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;
import fr.theflogat.gearbox.tile.TEDynamo;
import fr.theflogat.gearbox.tile.TER;

public class BlockDynamo extends BlockContainer{

	public BlockDynamo(int id) {
		super(id, Material.iron);
		setUnlocalizedName(ModLib.getId(Names.dynamo));
		setCreativeTab(GearBox.gears);
		setHardness(10);
		setResistance(50);
		setBlockBounds(2*0.0625F, 2*0.0625F, 2*0.0625F, 14*0.0625F, 14*0.0625F, 14*0.0625F);
	}
	
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x+2*0.0625, y+2*0.0625, z+2*0.0625, x+14*0.0625, y+14*0.0625, z+14*0.0625);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x+2*0.0625, y+2*0.0625, z+2*0.0625, x+14*0.0625, y+14*0.0625, z+14*0.0625);
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
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
		 if (!w.isRemote)
	        {
	            int l = w.getBlockId(x, y, z - 1);
	            int i1 = w.getBlockId(x, y, z + 1);
	            int j1 = w.getBlockId(x - 1, y, z);
	            int k1 = w.getBlockId(x + 1, y, z);
	            byte b0 = 3;

	            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
	            {
	                b0 = 3;
	            }

	            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
	            {
	                b0 = 2;
	            }

	            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
	            {
	                b0 = 5;
	            }

	            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
	            {
	                b0 = 4;
	            }

	            w.setBlockMetadataWithNotify(x, y, z, b0, 2);
	        }
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
	public void breakBlock(World par1World, int par2,int par3,int par4,int par5,int par6){
		TER tile =  (TER) par1World.getBlockTileEntity(par2, par3, par4);
		tile.dropContents();
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.inventory.mainInventory[player.inventory.currentItem];
		if(heldItem!=null && heldItem.getItem() instanceof ItemGearbox){
			TileEntity dynamo = w.getBlockTileEntity(x, y, z);
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
	public TileEntity createNewTileEntity(World world) {
		return new TEDynamo();
	}
	
	@Override
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon(ModLib.getAsset(Names.dynamo));
	}
}
