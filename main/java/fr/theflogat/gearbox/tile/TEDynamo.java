package fr.theflogat.gearbox.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.IEnergyHandler;
import fr.theflogat.gearbox.api.FuelLiquid;
import fr.theflogat.gearbox.api.FuelLiquid.Cold;
import fr.theflogat.gearbox.api.Fuels.Burn;
import fr.theflogat.gearbox.api.Fuels.Reactant;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.items.ItemGearbox.Input;

public class TEDynamo extends TER implements IEnergyHandler, IFluidHandler{

	private ItemStack[] inv;
	private EnergyStorageBis ener = new EnergyStorageBis(1000000, 0, 1000000);

	public FluidTank[] tanks = {
			new FluidTank(FluidContainerRegistry.BUCKET_VOLUME*4),
			new FluidTank(FluidContainerRegistry.BUCKET_VOLUME*4),
			new FluidTank(FluidContainerRegistry.BUCKET_VOLUME*4)
	};
	private boolean areInputsValid = false;
	public int ticksRemain = 0;
	private int cooldown = 0;

	public TEDynamo() {
		inv = new ItemStack[3];
	}

	@SuppressWarnings("unused")
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote){
			if(inv[2]!=null && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
				int ticks = 0;
				if(ticksRemain==0){
					for(Input in : Input.valid){
						try{
							if(inv[2].stackTagCompound.getBoolean(in.ident)){
								if(in.id==0){
									if(inv[0]!=null){
										ticks += Burn.getValue(inv[0]) * (true ?
												inv[2].stackTagCompound.getFloat(ItemGearbox.efficency) : 1);
										inv[0].stackSize--;
										if(inv[0].stackSize==0)
											inv[0]=null;
									}
								}else{
									FluidTank tank = tanks[in.id-1];
									if(in.id==1){
										if(tank.getFluid()!=null && tank.getFluidAmount()>=1000){
											ticks += FuelLiquid.Hot.getDurationValue(tank.getFluid()) * (true ?
													inv[2].stackTagCompound.getFloat(ItemGearbox.efficency) : 1);
											tank.drain(1000, true);
										}
									}else if(in.id==2){
										if(tank.getFluid()!=null && tank.getFluidAmount()>=1000){
											ticks += FuelLiquid.Cold.getDurationValue(tank.getFluid()) * (Cold.canBeEfficient(tank.getFluid()) ?
													inv[2].stackTagCompound.getFloat(ItemGearbox.efficency) : 1);
											tank.drain(1000, true);
										}
									}else if(in.id==3){
										if(tank.getFluid()!=null && tank.getFluidAmount()>=1000 && inv[1]!=null){
											ticks += Math.max(FuelLiquid.Reacting.getDurationValue(tank.getFluid()), Reactant.getValue(inv[1])) *
													(true ? inv[2].stackTagCompound.getFloat(ItemGearbox.efficency) : 1);
											tank.drain(1000, true);
											inv[1].stackSize--;
										}
									}
								}
							}
						}catch(Exception e){}
					}
					ticksRemain = ticks;
				}
				if(ticksRemain>0 && ener.energy<getMaxEnergyStored(null)){
					ticksRemain--;
					ener.energy += inv[2].stackTagCompound.getInteger(ItemGearbox.output);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}

				transmit();
			}
		}
		if(cooldown==0){
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			cooldown = 200;
		}else{
			cooldown--;
		}
	}

	public void transmit() {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
			if(worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) instanceof IEnergyHandler){
				IEnergyHandler conduit = (IEnergyHandler)worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				int receive = conduit.receiveEnergy(dir.getOpposite(), ener.energy, false);
				ener.energy-=receive;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound comp) {
		super.readFromNBT(comp);

		NBTTagList list = comp.getTagList("Fluids");

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tank = (NBTTagCompound) list.tagAt(i);
			int slot = tank.getByte("Tank");
			tanks[slot].readFromNBT(tank);
		}

		ener.energy = comp.getInteger("energy");
		ticksRemain = comp.getInteger("ticksRemain");
		blockMetadata = comp.getShort("meta");
	}

	@Override
	public void writeToNBT(NBTTagCompound comp) {
		super.writeToNBT(comp);

		NBTTagList list = new NBTTagList();

		for(int i = 0; i < tanks.length; i++) {
			FluidTank tankTo = tanks[i];
			NBTTagCompound tank = new NBTTagCompound();

			tank.setByte("Tank", (byte) i);
			tankTo.writeToNBT(tank);
			list.appendTag(tank);
		}

		comp.setTag("Fluids", list);
		comp.setInteger("energy", ener.energy);
		comp.setInteger("ticksRemain", ticksRemain);
		comp.setShort("meta", (short) blockMetadata);
	}

	public ItemStack setGearbox(ItemStack gearbox) {
		if(gearbox.stackTagCompound!=null && gearbox.stackTagCompound.getFloat(ItemGearbox.instab)<=20){
			ItemStack boxIn = inv[2];
			inv[2] = gearbox.copy();
			return boxIn;
		}
		return gearbox;
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
		if(i==0){
			return Burn.getValue(itemstack)>0;
		}else if(i==1){
			return Reactant.getValue(itemstack)>0;
		}
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		ener.energy -= simulate ? 0 : Math.min(maxExtract, ener.energy);
		System.out.println("Check: " + Math.min(maxExtract, ener.energy));
		return Math.min(maxExtract, ener.energy);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {return true;}

	@Override
	public int getEnergyStored(ForgeDirection from) {return ener.energy;}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {return 1000000;}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int out = 0;

		if(FuelLiquid.Hot.getDurationValue(resource)>0){
			out = tanks[0].fill(resource, doFill);
		}else if(FuelLiquid.Cold.getDurationValue(resource)>0){
			out = tanks[1].fill(resource, doFill);
		}else if(FuelLiquid.Reacting.getDurationValue(resource)>0){
			out = tanks[2].fill(resource, doFill);
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return out;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		FluidStack test = new FluidStack(fluid, 0);
		return  (FuelLiquid.Cold.getDurationValue(test)>0 && (tanks[1].getFluid().isFluidEqual(test)||tanks[1].getFluid()==null)) ||
				(FuelLiquid.Hot.getDurationValue(test)>0 && (tanks[0].getFluid().isFluidEqual(test)||tanks[0].getFluid()==null)) ||
				(FuelLiquid.Reacting.getDurationValue(test)>0 && (tanks[2].getFluid().isFluidEqual(test)||tanks[2].getFluid()==null));
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {return false;}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{
				new FluidTankInfo(tanks[0]),
				new FluidTankInfo(tanks[1]),
				new FluidTankInfo(tanks[2]),
		};
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {return null;}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {return null;}

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
