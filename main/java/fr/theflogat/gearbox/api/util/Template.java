package fr.theflogat.gearbox.api.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

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
}
