package fr.theflogat.gearbox.api;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import fr.theflogat.gearbox.api.util.GridUtil;
import fr.theflogat.gearbox.api.util.Template;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.items.ItemGearbox.Input;

public class Stats{

	public Stats() {}

	public int outputShafts = 0;
	public byte shaftLevel = 0;
	public int[] input = {-1,-1,-1,-1};
	public float efficiency = 1;
	public int output = 0;
	public int gears = 0;
	public float inst = 0;
	
	public static ItemStack process(ItemStack[] inv, IInventory crafter){
		Stats stat = new Stats();
		for(int i = 0; i<inv.length; i++){
			ItemStack items = inv[i];
			String name = getName(items).toLowerCase();
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
						if(name.contains("efficient")){
							stat.input[in.id] = 2;
						}else if(name.contains("power")){
							stat.input[in.id] = 1;
						}else{
							stat.input[in.id] = 0;
						}
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
				if(GridUtil.has2More(crafter,i)){
					stat.efficiency *= val.eff;
					stat.output += val.out/2;
				} else {
					stat.output += val.out;
				}
				stat.efficiency *= val.eff;
				stat.inst += val.inst;
			}
		}
		return stat.writeToItemStack(crafter);
	}
	
	public static String getName(ItemStack items) {
		return OreDictionary.getOreName(OreDictionary.getOreID(items));
	}

	public ItemStack writeToItemStack(IInventory inv) {
		ItemStack items = new ItemStack(Gears.GearBox,1);
		items.stackTagCompound = new NBTTagCompound();

		items.stackTagCompound.setByte(ItemGearbox.shaft, shaftLevel);
		for(int i = 0; i<input.length; i++)
			items.stackTagCompound.setInteger(Input.valid[i].ident, input[i]);



		items.stackTagCompound.setFloat(ItemGearbox.instab, inst);
		if(gears>0){
			if(shaftLevel==3){
				items.stackTagCompound.setInteger(ItemGearbox.output, output - gears*8);
				items.stackTagCompound.setFloat(ItemGearbox.efficency, (float) (Math.sqrt(efficiency/Math.sqrt(gears))));
			}else if(shaftLevel==2){
				items.stackTagCompound.setInteger(ItemGearbox.output, Math.min(200, output - gears*8));
				items.stackTagCompound.setFloat(ItemGearbox.efficency, Math.min(20, (float) Math.sqrt(efficiency/Math.sqrt(gears))));
			}else{
				items.stackTagCompound.setInteger(ItemGearbox.output, Math.min(100, output - gears*8));
				items.stackTagCompound.setFloat(ItemGearbox.efficency, Math.min(5, (float) Math.sqrt(efficiency/Math.sqrt(gears))));
			}
		}else{
			return null;
		}
		
		Template temp = new Template(inv);
		temp.writeToNBT(items.stackTagCompound);

		return items;
	}
}