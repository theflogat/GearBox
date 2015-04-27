package fr.theflogat.gearbox.api.util;

import fr.theflogat.gearbox.tile.TEGearBench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class GridUtil {

	static final int off0 = -10;
	static final int off1 = - 9;
	static final int off2 = - 8;
	static final int off3 = - 1;
	static final int min = 0;

	static final int off4 = + 1;
	static final int off5 = + 8;
	static final int off6 = + 9;
	static final int off7 = +10;
	static final int max = 80;

	//0 1 2
	//3 X 4
	//5 6 7

	public static boolean connects(IInventory inv, int index){
		boolean hasCon = false;
		try{
			hasCon = inv.getStackInSlot(index + ISI(0,index))!=null||hasCon;
		}catch(Exception e){}
		try{
			hasCon = inv.getStackInSlot(index + ISI(1,index))!=null||hasCon;
		}catch(Exception e){}
		try{
			hasCon = inv.getStackInSlot(index + ISI(2,index))!=null||hasCon;
		}catch(Exception e){}
		try{	
			hasCon = inv.getStackInSlot(index + ISI(3,index))!=null||hasCon;
		}catch(Exception e){}
		try{
			hasCon = inv.getStackInSlot(index + ISI(4,index))!=null||hasCon;	
		}catch(Exception e){}
		try{
			hasCon = inv.getStackInSlot(index + ISI(5,index))!=null||hasCon;
		}catch(Exception e){}
		try{
			hasCon = inv.getStackInSlot(index + ISI(6,index))!=null||hasCon;
		}catch(Exception e){}
		try{
			hasCon = inv.getStackInSlot(index + ISI(7,index))!=null||hasCon;
		}catch(Exception e){}
		return hasCon;
	}
	
	public static boolean connectsNoSh(IInventory inv, int index){
		boolean hasCon = false;
		try{
			hasCon = (inv.getStackInSlot(index + ISI(0,index))!=null && !getName(inv.getStackInSlot(index + ISI(0,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(1,index))!=null && !getName(inv.getStackInSlot(index + ISI(1,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(2,index))!=null && !getName(inv.getStackInSlot(index + ISI(2,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{	
			hasCon = (inv.getStackInSlot(index + ISI(3,index))!=null && !getName(inv.getStackInSlot(index + ISI(3,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(4,index))!=null && !getName(inv.getStackInSlot(index + ISI(4,index))).contains("shaft"))||hasCon;	
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(5,index))!=null && !getName(inv.getStackInSlot(index + ISI(5,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(6,index))!=null && !getName(inv.getStackInSlot(index + ISI(6,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(7,index))!=null && !getName(inv.getStackInSlot(index + ISI(7,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		return hasCon;
	}
	
	public static boolean connectsSh(IInventory inv, int index){
		boolean hasCon = false;
		try{
			hasCon = (inv.getStackInSlot(index + ISI(0,index))!=null && getName(inv.getStackInSlot(index + ISI(0,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(1,index))!=null && getName(inv.getStackInSlot(index + ISI(1,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(2,index))!=null && getName(inv.getStackInSlot(index + ISI(2,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{	
			hasCon = (inv.getStackInSlot(index + ISI(3,index))!=null && getName(inv.getStackInSlot(index + ISI(3,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(4,index))!=null && getName(inv.getStackInSlot(index + ISI(4,index))).contains("shaft"))||hasCon;	
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(5,index))!=null && getName(inv.getStackInSlot(index + ISI(5,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(6,index))!=null && getName(inv.getStackInSlot(index + ISI(6,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		try{
			hasCon = (inv.getStackInSlot(index + ISI(7,index))!=null && getName(inv.getStackInSlot(index + ISI(7,index))).contains("shaft"))||hasCon;
		}catch(Exception e){}
		return hasCon;
	}
	
	public static String getName(ItemStack items) {
		return OreDictionary.getOreName(OreDictionary.getOreID(items)).toLowerCase();
	}
	
	public static boolean has2(IInventory inv, int index) {
		int con = 0;
		try{
			con += inv.getStackInSlot(index + ISI(0,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(1,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(2,index))!=null?1:0;
		}catch(Exception e){}
		try{	
			con += inv.getStackInSlot(index + ISI(3,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(4,index))!=null?1:0;	
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(5,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(6,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(7,index))!=null?1:0;
		}catch(Exception e){}
		return con>1;
	}
	
	public static boolean has2More(IInventory inv, int index) {
		int con = 0;
		try{
			con += inv.getStackInSlot(index + ISI(0,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(1,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(2,index))!=null?1:0;
		}catch(Exception e){}
		try{	
			con += inv.getStackInSlot(index + ISI(3,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(4,index))!=null?1:0;	
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(5,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(6,index))!=null?1:0;
		}catch(Exception e){}
		try{
			con += inv.getStackInSlot(index + ISI(7,index))!=null?1:0;
		}catch(Exception e){}
		return con>2;
	}

	public static boolean hasGear(IInventory inv, int index) {
		int con = 0;
		try{
			con += (inv.getStackInSlot(index + ISI(0,index))!=null && getName(inv.getStackInSlot(index + ISI(0,index))).contains("gear"))?1:0;
		}catch(Exception e){}
		try{
			con += (inv.getStackInSlot(index + ISI(1,index))!=null && getName(inv.getStackInSlot(index + ISI(1,index))).contains("gear"))?1:0;
		}catch(Exception e){}
		try{
			con += (inv.getStackInSlot(index + ISI(2,index))!=null && getName(inv.getStackInSlot(index + ISI(2,index))).contains("gear"))?1:0;
		}catch(Exception e){}
		try{	
			con += (inv.getStackInSlot(index + ISI(3,index))!=null && getName(inv.getStackInSlot(index + ISI(3,index))).contains("gear"))?1:0;
		}catch(Exception e){}
		try{
			con += (inv.getStackInSlot(index + ISI(4,index))!=null && getName(inv.getStackInSlot(index + ISI(4,index))).contains("gear"))?1:0;
		}catch(Exception e){}
		try{
			con += (inv.getStackInSlot(index + ISI(5,index))!=null && getName(inv.getStackInSlot(index + ISI(5,index))).contains("gear"))?1:0;
		}catch(Exception e){}
		try{
			con += (inv.getStackInSlot(index + ISI(6,index))!=null && getName(inv.getStackInSlot(index + ISI(6,index))).contains("gear"))?1:0;
		}catch(Exception e){}
		try{
			con += (inv.getStackInSlot(index + ISI(7,index))!=null && getName(inv.getStackInSlot(index + ISI(7,index))).contains("gear"))?1:0;
		}catch(Exception e){}
		return con>0;
	}
	
	public static boolean canRetireGear(IInventory inv, int index) {
		boolean hasSh = connectsSh(inv, index);
		if(hasSh){
			return !hasGear(inv, index);
		}
		boolean hasGear = true;
		try{
			if(inv.getStackInSlot(index + ISI(0,index))!=null && getName(inv.getStackInSlot(index + ISI(0,index))).contains("gear"))
					hasGear = has2(inv, index + ISI(0,index))&&hasGear;
		}catch(Exception e){}
		try{
			if(inv.getStackInSlot(index + ISI(1,index))!=null && getName(inv.getStackInSlot(index + ISI(1,index))).contains("gear"))
					hasGear = has2(inv, index+ISI(1,index))&&hasGear;
		}catch(Exception e){}
		try{
			if(inv.getStackInSlot(index + ISI(2,index))!=null && getName(inv.getStackInSlot(index + ISI(2,index))).contains("gear"))
					hasGear = has2(inv, index+ISI(2,index))&&hasGear;
		}catch(Exception e){}
		try{	
			if(inv.getStackInSlot(index + ISI(3,index))!=null && getName(inv.getStackInSlot(index + ISI(3,index))).contains("gear"))
					hasGear = has2(inv, index+ISI(3,index))&&hasGear;
		}catch(Exception e){}
		try{
			if(inv.getStackInSlot(index + ISI(4,index))!=null && getName(inv.getStackInSlot(index + ISI(4,index))).contains("gear"))
					hasGear = has2(inv, index+ISI(4,index))&&hasGear;	
		}catch(Exception e){}
		try{
			if(inv.getStackInSlot(index + ISI(5,index))!=null && getName(inv.getStackInSlot(index + ISI(5,index))).contains("gear"))
					hasGear = has2(inv, index+ISI(5,index))&&hasGear;
		}catch(Exception e){}
		try{
			if(inv.getStackInSlot(index + ISI(6,index))!=null && getName(inv.getStackInSlot(index + ISI(6,index))).contains("gear"))
					hasGear = has2(inv, index+ISI(6,index))&&hasGear;
		}catch(Exception e){}
		try{
			if(inv.getStackInSlot(index + ISI(7,index))!=null && getName(inv.getStackInSlot(index + ISI(7,index))).contains("gear"))
					hasGear = has2(inv, index+ISI(7,index))&&hasGear;
		}catch(Exception e){}
		return hasGear;
	}
	
	//0 1 2
	//3 X 4
	//5 6 7
	
	public static int ISI(int seek, int index) {
		if(index%9 == 0)
			if(seek==0 || seek==3 || seek==5)
				return -1;
		
		if(index%9 == 8)
			if(seek==2 || seek==4 || seek==7)
				return -1;
		
		switch(seek){
		case 0:	return off0;
		case 1:	return off1;
		case 2: return off2;
		case 3: return off3;
		case 4: return off4;
		case 5: return off5;
		case 6: return off6;
		case 7: return off7;
		}
		
		
		return -1;
	}
}
