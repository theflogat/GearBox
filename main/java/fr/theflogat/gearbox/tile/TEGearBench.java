package fr.theflogat.gearbox.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import fr.theflogat.gearbox.api.Gears;
import fr.theflogat.gearbox.api.GearsValue;
import fr.theflogat.gearbox.api.Stats;
import fr.theflogat.gearbox.api.util.GridUtil;
import fr.theflogat.gearbox.api.util.Template;
import fr.theflogat.gearbox.api.util.WorldUtil;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.items.ItemGearbox.Input;

public class TEGearBench extends TER{

	private ItemStack[] inv;
	private int cooldown = 0;

	public TEGearBench() {
		inv = new ItemStack[81];
	}

	@Override
	public void updateEntity() {
		if(cooldown == 0){
			boolean shouldCraft = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) || 
					worldObj.getBlockPowerInput(xCoord, yCoord, zCoord)>0;
			if(shouldCraft){
				craft();
				cooldown = 200;
			}
		}else{
			cooldown--;
		}
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
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;		
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	public void craft() {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
			if(dir!=ForgeDirection.DOWN && dir!=ForgeDirection.UP){
				TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				if(tile != null && tile instanceof IInventory){
					if(canDropOff((IInventory) tile)){
						ItemStack result = Stats.process(inv, this);
						if(result != null){
							dropInInv((IInventory) tile, result);
							clearInv();
							return;
						}
					}
				}
			}
		}
		
		{
			TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if(tile != null && tile instanceof TETemplate){
				for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
					if(dir!=ForgeDirection.DOWN && dir!=ForgeDirection.UP){
						TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
						if(te != null && te instanceof IInventory){
							if(canDropOff((IInventory) te)){
								ItemStack result = ((TETemplate)tile).getGearbox();
								if(result!=null){
									dropInInv((IInventory) te, result);
									clearInvAbove((IInventory)tile);
									return;
								}
							}
						}
					}
				}
				return;
			}
		}
		
		
		if(!worldObj.isRemote){
			ItemStack result = Stats.process(inv, this);
			if(result!=null){
				WorldUtil.spawnItemStack(getCoords(), result);
				clearInv();
			}
		}
	}

	private void clearInv() {
		for(int i=0; i<inv.length; i++){
			ItemStack items = inv[i];
			if(items!=null){
				items.stackSize--;
				if(items.stackSize==0)
					items=null;
				setInventorySlotContents(i, items);
			}
		}
	}
	
	private void clearInvAbove(IInventory inv) {
		for(int i=0; i<inv.getSizeInventory(); i++){
			ItemStack items = inv.getStackInSlot(i);
			if(items!=null){
				inv.setInventorySlotContents(i, null);
			}
		}
	}

	private void dropInInv(IInventory inv, ItemStack result) {
		for(int i = 0; i<inv.getSizeInventory(); i++){
			ItemStack item = inv.getStackInSlot(i);
			if(item==null){
				inv.setInventorySlotContents(i, result);
				return;
			}
		}
	}

	public boolean canDropOff(IInventory inv){

		for(int i = 0; i<inv.getSizeInventory(); i++){
			ItemStack item = inv.getStackInSlot(i);
			if(item==null){
				return true;
			}
		}

		return false;
	}
	
	public ItemStack tryToInsetBox(ItemStack gearbox) {
		if(!isEmpty()){
			return gearbox;
		}else{
			Template temp = new Template();
			temp.readFromNBT(gearbox.stackTagCompound);
			temp.applyTemplate(this);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return null;
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		if(!worldObj.isRemote){
			NBTTagCompound comp = new NBTTagCompound();
			writeToNBT(comp);
			return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, comp);
		}
		return null;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}
}
