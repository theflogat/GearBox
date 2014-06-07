package fr.theflogat.gearbox.items;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.IEnergyContainerItem;

import com.google.common.collect.Multimap;

import fr.theflogat.gearbox.GearBox;
import fr.theflogat.gearbox.api.util.Coordinates;
import fr.theflogat.gearbox.api.util.WorldUtil;
import fr.theflogat.gearbox.items.ItemGearbox.Input;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemMecaTool extends ItemTool implements IEnergyContainerItem{

	public ItemMecaTool(int id) {
		super(id, 0, GearBox.meca, null);
		setUnlocalizedName(ModLib.getId(Names.tool));
		setCreativeTab(GearBox.gears);
		setMaxStackSize(1);
		setMaxDamage(1000);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack items, World w,	EntityPlayer player) {
		if(player.isSneaking()){
			ItemStack heldItem = player.inventory.mainInventory[Math.max(player.inventory.currentItem-1, 0)];
			if(heldItem!=null && heldItem.getItem() instanceof ItemGearbox){
				NBTTagCompound comp = items.stackTagCompound;
				items.stackTagCompound = heldItem.stackTagCompound;
				boolean ex = false;
				try{
					items.stackTagCompound.getInteger(ItemGearbox.output);
				}catch(Exception e){ex = true;}
				if(ex){
					player.inventory.mainInventory[player.inventory.currentItem-1] = null;
				}else{
					player.inventory.mainInventory[player.inventory.currentItem-1].stackTagCompound = comp;
				}
				items.stackTagCompound.setInteger("power", comp.getInteger("power"));
			}
			return items;
		}
		try{
			items.stackTagCompound.getInteger(ItemGearbox.output);
		}catch(Exception e){return items;}
		MovingObjectPosition mov = getMovingObjectPositionFromPlayer(w, player, true);
		{
			int x = (int) player.posX;
			int y = (int) player.posY;
			int z = (int) player.posZ;
			if(mov!=null && mov.typeOfHit==EnumMovingObjectType.TILE){
				for(int i = (int) Math.min(-5, -items.stackTagCompound.getInteger(ItemGearbox.output)/800*getPowerMod(items)); 
						i<Math.max(5, items.stackTagCompound.getInteger(ItemGearbox.output)/800*getPowerMod(items)); i++){
					for(int j = (int) Math.min(-5, -items.stackTagCompound.getInteger(ItemGearbox.output)/800*getPowerMod(items)); 
							j<Math.max(5, items.stackTagCompound.getInteger(ItemGearbox.output)/800*getPowerMod(items)); j++){
						for(int k = (int) Math.min(-5, -items.stackTagCompound.getInteger(ItemGearbox.output)/800*getPowerMod(items)); 
								k<Math.max(5, items.stackTagCompound.getInteger(ItemGearbox.output)/800*getPowerMod(items)); k++){
							if(canOperate(items)){
								if(w.getBlockId(x + i, y + j, z + k)!=0 && 
										Block.blocksList[w.getBlockId(x + i, y + j, z + k)].getBlockHardness(w, x + i, y + j, z + k)>=0){
									ArrayList<ItemStack> list = Block.blocksList[w.getBlockId(x + i, y + j, z + k)]
											.getBlockDropped(w, x + i, y + j, z + k, w.getBlockMetadata(x + i, y + j, z + k), 0);
									Coordinates coords = new Coordinates(x, y, z, w);
									for(ItemStack item : list){
										ItemStack toCheck = item.copy();
										toCheck.stackSize=1;
										toCheck.stackTagCompound=null;
										if(!toCheck.areItemStacksEqual(toCheck, new ItemStack(Block.cobblestone,1)))
											if(!toCheck.areItemStacksEqual(toCheck, new ItemStack(Block.dirt,1)))
												if(!w.isRemote)
													WorldUtil.spawnItemStack(coords, item);
									}
									w.setBlockToAir(x + i, y + j, z + k);
									onBlockDestroyed(items, null, 0, 0, 0, 0, null);
								}
							}
						}
					}
				}
				return items;
			}

			List ents = w.getEntitiesWithinAABBExcludingEntity(player, 
					AxisAlignedBB.getBoundingBox(x + Math.min(-5, -items.stackTagCompound.getInteger(ItemGearbox.output)/800)*getPowerMod(items), 
							y + Math.min(-5, -items.stackTagCompound.getInteger(ItemGearbox.output)/800)*getPowerMod(items), 
							z + Math.min(-5, -items.stackTagCompound.getInteger(ItemGearbox.output)/800)*getPowerMod(items), 
							x + Math.max(5, items.stackTagCompound.getInteger(ItemGearbox.output)/800)*getPowerMod(items), 
							y + Math.max(5, items.stackTagCompound.getInteger(ItemGearbox.output)/800)*getPowerMod(items), 
							z + Math.max(5, items.stackTagCompound.getInteger(ItemGearbox.output)/800)*getPowerMod(items)));
			for(int i=0; i<ents.size(); i++){
				if(ents.get(i) instanceof EntityLivingBase){
					hitEntity(items, (EntityLivingBase) ents.get(i), player);
				}
			}
			return items;
		}
	}
	@Override
	public void onUpdate(ItemStack items, World w, Entity player, int par4, boolean par5) {
		items.setItemDamage(1000 - (int)((float)items.stackTagCompound.getInteger("power")/(float)getMaxEnergyStored(items)*1000));
	}

	@Override
	public float getStrVsBlock(ItemStack items, Block block, int meta) {
		try{
			return items.stackTagCompound.getInteger("power") > 1000/items.stackTagCompound.getFloat(ItemGearbox.efficency)?
					items.stackTagCompound.getInteger(ItemGearbox.output)/10 : 1;
		}catch(Exception e){
			return 1;
		}
	}

	public boolean canOperate(ItemStack items) {
		return items.stackTagCompound.getInteger("power") > 1000/items.stackTagCompound.getFloat(ItemGearbox.efficency);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack items, World w, int x, int y, int z, int meta, EntityLivingBase player) {
		usePower(items);
		if(items.stackTagCompound.getFloat(ItemGearbox.instab)>20){
			if(player.getHealth()<=8){
				((EntityPlayer)player).onDeath(DamageSource.generic);
				player.heal(-8);
			}else{
				player.heal(-8);
			}
		}
		return true;
	}

	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List list, boolean moreInfo) {
		if(items.stackTagCompound == null){
			items.stackTagCompound = new NBTTagCompound();
			items.stackTagCompound.setInteger("power", 0);
		}

		try{
			list.add("Instability: " + items.stackTagCompound.getFloat(ItemGearbox.instab));
			list.add("Power Consumption: " + 1000/items.stackTagCompound.getFloat(ItemGearbox.efficency) + " RF/use");
			list.add("Speed/Range: " + items.stackTagCompound.getInteger(ItemGearbox.output)/10);
			list.add("Attack Damage: " + items.stackTagCompound.getInteger(ItemGearbox.output)/100);
			list.add("Power: " + items.stackTagCompound.getInteger("power") + "/" + getMaxEnergyStored(items));
		}catch(Exception e){
			list.add("Instability: " + 0);
			list.add("Power Consumption: " + 0 + " RF/use");
			list.add("Speed/Range: " + 0 );
			list.add("Attack Damage: " + 0);
			list.add("Power: " + 0 + "/" + getMaxEnergyStored(items));
		}
	}

	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), 
				new AttributeModifier(field_111210_e, "Tool modifier", (double)0, 0));
		return multimap;
	}

	@Override
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ModLib.getAsset(Names.tool));
	}

	@Override
	public boolean hitEntity(ItemStack items, EntityLivingBase entityHitted, EntityLivingBase entityHit) {
		if(items.stackTagCompound.getInteger("power") >= 1000/items.stackTagCompound.getFloat(ItemGearbox.efficency)){
			int dmg = items.stackTagCompound.getInteger(ItemGearbox.output)/100;
			if(entityHitted.getHealth()>dmg){
				entityHitted.heal(-dmg);
			}else{
				entityHitted.onDeath(DamageSource.generic);
				entityHitted.heal(-dmg);
			}

			usePower(items);

			if(items.stackTagCompound.getFloat(ItemGearbox.instab)>20){
				if(entityHit.getHealth()<=8){
					((EntityPlayer)entityHit).onDeath(DamageSource.generic);
					entityHit.heal(-8);
				}else{
					entityHit.heal(-8);
				}
			}
		}
		return true;
	}
	
	private float getPowerMod(ItemStack items) {
		float k = 0;
		int n = 0;
		for(Input in : Input.valid){
			int i = items.stackTagCompound.getInteger(in.ident);
			if(i>=0){
				k += (i==1 ? 1.5 : (i==2 ? 0.5 : 1));
				n++;
			}
		}
		return k/n;
	}
	
	private void usePower(ItemStack items) {
		float k = 0;
		int n = 0;
		for(Input in : Input.valid){
			int i = items.stackTagCompound.getInteger(in.ident);
			if(i>=0){
				k += (i==2 ? 1.5 : (i==1 ? 0.5 : 1));
				n++;
			}
		}
		items.stackTagCompound.setInteger("power", (int) (items.stackTagCompound.getInteger("power")-
				(1000/items.stackTagCompound.getInteger(ItemGearbox.efficency)*k/n)));
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int received = Math.min(maxReceive, getMaxEnergyStored(container) - container.stackTagCompound.getInteger("power"));
		if(!simulate){
			container.stackTagCompound.setInteger("power", (int) (container.stackTagCompound.getInteger("power") + received));
		}
		onUpdate(container, null, null, 0, false);
		return received;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return container.stackTagCompound.getInteger("power");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return 100000000;
	}
}
