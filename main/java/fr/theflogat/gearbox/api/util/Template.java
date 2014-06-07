package fr.theflogat.gearbox.api.util;

import fr.theflogat.gearbox.api.Gears;
import fr.theflogat.gearbox.api.GearsValue;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.items.ItemGearbox.Input;
import fr.theflogat.gearbox.tile.TETemplate.Stats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;

public class Template {
	
	public ItemStack[] template;
	public IInventory inv = new IInventory() {
		
		@Override
		public void setInventorySlotContents(int i, ItemStack itemstack) {
			template[i] = itemstack;
		}
		
		@Override
		public void openChest() {}
		
		@Override
		public void onInventoryChanged() {}
		
		@Override
		public boolean isUseableByPlayer(EntityPlayer entityplayer) {
			return true;
		}
		
		@Override
		public boolean isItemValidForSlot(int i, ItemStack itemstack) {
			return true;
		}
		
		@Override
		public boolean isInvNameLocalized() {
			return false;
		}
		
		@Override
		public ItemStack getStackInSlotOnClosing(int i) {
			return null;
		}
		
		@Override
		public ItemStack getStackInSlot(int i) {
			return template[i];
		}
		
		@Override
		public int getSizeInventory() {
			return template.length;
		}
		
		@Override
		public int getInventoryStackLimit() {
			return 1;
		}
		
		@Override
		public String getInvName() {
			return null;
		}
		
		@Override
		public ItemStack decrStackSize(int i, int j) {
			return null;
		}
		
		@Override
		public void closeChest() {}
	};
	
	public Template() {
		template = new ItemStack[81];
	}
	
	public Template(ItemStack[] inv) {
		template = inv;
	}
	
	public boolean corresponds(IInventory inv) {
		if(template!=null){
			for(int i=0; i<template.length; i++){
				if(!ItemStack.areItemStacksEqual(template[i], inv.getStackInSlot(i)))
					return false;
			}
		}
		
		return true;
	}
	
	public Template(IInventory inv) {
		template = new ItemStack[inv.getSizeInventory()];
		
		for(int i=0; i<template.length; i++){
			template[i] = inv.getStackInSlot(i);
		}
	}
	
	public void applyTemplate(IInventory inv){
		for(int i=0; i<template.length; i++){
			inv.setInventorySlotContents(i, template[i]);
		}
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < template.length; i++) {
			ItemStack itemstack = template[i];

			if(itemstack != null) {
				NBTTagCompound item = new NBTTagCompound();

				item.setByte("SlotTemplate", (byte) i);
				itemstack.writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		compound.setTag("ItemsTemplate", list);
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		
		template = new ItemStack[81];
		
		NBTTagList list = compound.getTagList("ItemsTemplate");

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) list.tagAt(i);
			int slot = item.getByte("SlotTemplate");

			if(slot >= 0) {
				template[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	public static Template readFromNBTS(NBTTagCompound compound) {
		
		ItemStack[] template = new ItemStack[81];
		
		NBTTagList list = compound.getTagList("ItemsTemplate");

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) list.tagAt(i);
			int slot = item.getByte("SlotTemplate");

			if(slot >= 0) {
				template[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
		
		return new Template(template);
	}
	
	public ItemStack process(){
		Stats stat = new Stats();

		for(int i = 0; i<template.length; i++){
			ItemStack items = template[i];
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

		for(int i = 0; i<template.length; i++){
			ItemStack items = template[i];
			String name = getName(items).toLowerCase();
			if(name.contains("shaftinput")){
				for(Input in : Input.valid){
					if(in.isPart(name)){
						stat.input[in.id] = true;
					}
				}
			}
		}

		for(int i = 0; i<template.length; i++){
			ItemStack items = template[i];
			String name = getName(items).toLowerCase();
			if(name.contains("gear")){
				GearsValue val = GearsValue.getValue(name);
				stat.gears++;
				if(GridUtil.has2More(inv,i)){
					stat.efficiency *= val.eff;
					stat.output += val.out/2;
				} else {
					stat.output += val.out;
				}
				stat.efficiency *= val.eff;
				stat.inst += val.inst;
			}
		}

		return stat.writeToItemStack();
	}
	
	public String getName(ItemStack items) {
		return OreDictionary.getOreName(OreDictionary.getOreID(items));
	}
	
	public class Stats{

		public int outputShafts = 0;
		public byte shaftLevel = 0;
		public boolean[] input = {false,false,false,false};
		public float efficiency = 1;
		public int output = 0;
		public int gears = 0;
		public float inst = 0;

		public ItemStack writeToItemStack() {
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
			
			Template temp = new Template(template);
			temp.writeToNBT(items.stackTagCompound);

			return items;
		}

		public Stats() {}
	}
}
