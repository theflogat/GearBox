package fr.theflogat.gearbox.tile;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import fr.theflogat.gearbox.api.Stats;
import fr.theflogat.gearbox.api.util.Template;


public class TETemplate extends TER {

	Template temp;
	private ItemStack[] inv = new ItemStack[81];
	
	public TETemplate() {
		temp = new Template();
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void applyTemplate(ItemStack gearbox) {
		if(gearbox.stackTagCompound==null)
			return;
		temp.readFromNBT(gearbox.stackTagCompound);
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {inv[i] = itemstack;}

	@Override
	public String getInvName() {return null;}

	@Override
	public boolean isInvNameLocalized() {return false;}

	@Override
	public int getInventoryStackLimit() {return 1;}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return temp!=null && 
				itemstack!=null && 
				temp.template!=null &&
				temp.template[i]!=null && 
				temp.template[i].areItemStacksEqual(temp.template[i], itemstack);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		temp.readFromNBT(compound);
		ItemStack gearbox = new ItemStack(Block.grass);
		gearbox.stackTagCompound = compound;
		applyTemplate(gearbox);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		temp.writeToNBT(compound);
	}
	
	public NBTTagCompound getNBT() {
		if(temp==null)
			return null;
		NBTTagCompound comp = new NBTTagCompound();
		temp.writeToNBT(comp);
		return comp;
	}

	public ItemStack getGearbox() {
		if(temp==null || temp.template==null){
			return null;
		}
		if(temp.corresponds(this)){
			return Stats.process(inv, this);
		}

		return null;
	}
	
	public boolean doesSlotCorrespond(int i) {
		if(temp==null || temp.template==null || isTempEmpty())
			return false;
		
		if(temp.template[i]==null && inv[i]==null)
			return true;
		
		if(temp.template[i]!=null && inv[i]!=null)
			return true;
		
		return false;
	}
	
	public boolean isTempEmpty() {
		for(ItemStack item: temp.template){
			if(item!=null)
				return false;
		}
		return true;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		if(!worldObj.isRemote){
			NBTTagCompound comp = new NBTTagCompound();
			writeToNBT(comp);
			return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, comp);
		}
		return null;
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		super.onDataPacket(net, pkt);
		readFromNBT(pkt.data);
	}
}
