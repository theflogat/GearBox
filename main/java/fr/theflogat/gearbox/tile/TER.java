package fr.theflogat.gearbox.tile;

import fr.theflogat.gearbox.api.util.Coordinates;
import fr.theflogat.gearbox.api.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TER extends TileEntity implements IInventory{

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		ItemStack itemstack = getStackInSlot(slot);

		if(itemstack != null) {
			if(itemstack.stackSize <= count) {
				setInventorySlotContents(slot, null);
			} else {
		itemstack = itemstack.splitStack(count);
			}
		}
		return itemstack;
	}
	
	public boolean isEmpty() {
		for(int i=0; i<getSizeInventory(); i++){
			if(getStackInSlot(i)!=null)
				return false;
		}
		
		return true;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack itemstack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return itemstack;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}
	
	public World getWorld() {
		return worldObj;
	}
	
	public void dropContents()
	{
		Coordinates here = getCoords();
		for (int i = 0; i < this.getSizeInventory(); i++){
			WorldUtil.spawnItemStack(here, this.getStackInSlotOnClosing(i));
		}
	}
	
	protected Coordinates getCoords() {
		Coordinates coords = new Coordinates(this.xCoord, this.yCoord, this.zCoord, this.worldObj);
		return coords;
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < this.getSizeInventory(); i++) {
			ItemStack itemstack = this.getStackInSlot(i);

			if(itemstack != null) {
				NBTTagCompound item = new NBTTagCompound();

				item.setByte("SlotsTile", (byte) i);
				itemstack.writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		compound.setTag("ItemsTile", list);
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList list = compound.getTagList("ItemsTile",10);

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) list.getCompoundTagAt(i);
			int slot = item.getByte("SlotsTile");

			if(slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}
}
