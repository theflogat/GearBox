package fr.theflogat.gearbox.tile;

import java.util.ArrayList;

import fr.theflogat.gearbox.api.Gears;
import fr.theflogat.gearbox.api.GearsValue;
import fr.theflogat.gearbox.api.util.Coordinates;
import fr.theflogat.gearbox.api.util.GridUtil;
import fr.theflogat.gearbox.api.util.Template;
import fr.theflogat.gearbox.api.util.WorldUtil;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.items.ItemGearbox.Input;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

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
	public String getInvName() {
		return "";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	public void craft() {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
			if(dir!=ForgeDirection.DOWN && dir!=ForgeDirection.UP){
				TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				if(tile != null && tile instanceof IInventory){
					if(canDropOff((IInventory) tile)){
						ItemStack result = process();
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
			TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
			if(tile != null && tile instanceof TETemplate){
				for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
					if(dir!=ForgeDirection.DOWN && dir!=ForgeDirection.UP){
						TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
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
			ItemStack result = process();
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

	public ItemStack process(){
		Stats stat = new Stats();

		for(int i = 0; i<inv.length; i++){
			ItemStack items = inv[i];
			String name = getName(items).toLowerCase();
			//output
			if(name.contains("shaftoutput")){
				stat.outputShafts++;
				stat.shaftLevel = (byte) (name.contains("basic") ? 1 : 
					(name.contains("strong") ? 2 : 3));
				if(stat.outputShafts>1)
					return null;
			}
		}

		for(int i = 0; i<inv.length; i++){
			ItemStack items = inv[i];
			String name = getName(items).toLowerCase();
			if(name.contains("shaftinput")){
				for(Input in : Input.valid){
					if(in.isPart(name)){
						System.out.println(in.id + " true");
						stat.input[in.id] = true;
					}
				}
			}
		}

		for(int i = 0; i<inv.length; i++){
			ItemStack items = inv[i];
			String name = getName(items).toLowerCase();
			if(name.contains("gear")){
				GearsValue val = GearsValue.getValue(name);
				stat.gears++;
				if(GridUtil.has2More(this,i)){
					stat.efficiency *= val.eff;
					stat.output += val.out/2;
				} else {
					stat.output += val.out;
				}
				stat.efficiency *= val.eff;
				stat.inst += val.inst;
			}
		}

		return stat.writeToItemStack(this);
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

	public String getName(ItemStack items) {
		return OreDictionary.getOreName(OreDictionary.getOreID(items));
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

	public class Stats{

		public int outputShafts = 0;
		public byte shaftLevel = 0;
		public boolean[] input = {false,false,false,false};
		public float efficiency = 1;
		public int output = 0;
		public int gears = 0;
		public float inst = 0;

		public ItemStack writeToItemStack(IInventory inv) {
			ItemStack items = new ItemStack(Gears.GearBox,1);
			items.stackTagCompound = new NBTTagCompound();

			items.stackTagCompound.setByte(ItemGearbox.shaft, shaftLevel);
			for(int i = 0; i<input.length; i++)
				items.stackTagCompound.setBoolean(Input.valid[i].ident, input[i]);



			items.stackTagCompound.setFloat(ItemGearbox.instab, inst);
			if(gears>0){
				if(shaftLevel==3){
					items.stackTagCompound.setInteger(ItemGearbox.output, output - gears*8);
					items.stackTagCompound.setFloat(ItemGearbox.efficency, (float) (Math.sqrt(efficiency)));
				}else if(shaftLevel==2){
					items.stackTagCompound.setInteger(ItemGearbox.output, Math.min(200, output - gears*8));
					items.stackTagCompound.setFloat(ItemGearbox.efficency, Math.min(20, (float) Math.sqrt(efficiency)));
				}else{
					items.stackTagCompound.setInteger(ItemGearbox.output, Math.min(100, output - gears*8));
					items.stackTagCompound.setFloat(ItemGearbox.efficency, Math.min(5, (float) Math.sqrt(efficiency)));
				}
			}else{
				return null;
			}
			
			Template temp = new Template(inv);
			temp.writeToNBT(items.stackTagCompound);

			return items;
		}

		public Stats() {}
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
