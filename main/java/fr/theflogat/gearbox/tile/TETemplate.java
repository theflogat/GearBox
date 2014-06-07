package fr.theflogat.gearbox.tile;

import fr.theflogat.gearbox.api.Gears;
import fr.theflogat.gearbox.api.GearsValue;
import fr.theflogat.gearbox.api.util.GridUtil;
import fr.theflogat.gearbox.api.util.Template;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.items.ItemGearbox.Input;
import fr.theflogat.gearbox.tile.TEGearBench.Stats;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.oredict.OreDictionary;


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
			return process();
		}

		return null;
	}
	
	public ItemStack process(){
		Stats stat = new Stats();

		for(int i = 0; i<temp.template.length; i++){
			ItemStack items = temp.template[i];
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

		for(int i = 0; i<temp.template.length; i++){
			ItemStack items = temp.template[i];
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
	
	public String getName(ItemStack items) {
		return OreDictionary.getOreName(OreDictionary.getOreID(items));
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
