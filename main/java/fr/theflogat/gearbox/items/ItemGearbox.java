package fr.theflogat.gearbox.items;

import java.util.List;

import fr.theflogat.gearbox.GearBox;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemGearbox extends Item{
	
	public static String instab = "instability";
	public static String efficency = "efficiency";
	public static String output = "output";
	public static String shaft = "shaft";
	
	public ItemGearbox(int id) {
		super(id);
		setUnlocalizedName(ModLib.getId(Names.gearbox));
		setCreativeTab(GearBox.gears);
		setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack items, World w, Entity player, int par4, boolean par5) {
		if(items.stackTagCompound == null)
			items.stackTagCompound = new NBTTagCompound();
		try{
			items.stackTagCompound.getFloat(instab);
		}catch(Exception e){
			try{
				items.stackTagCompound.setFloat(instab, items.stackTagCompound.getInteger(instab));
			}catch(Exception f){}
		}
	}
	
	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List list, boolean moreInfo) {
		if(items.stackTagCompound == null)
			items.stackTagCompound = new NBTTagCompound();
		
		try{
			list.add("Instability: " + items.stackTagCompound.getFloat(instab));
			list.add("Efficiency: " + items.stackTagCompound.getFloat(efficency));
			float k = 0;
			int n = 0;
			for(Input in : Input.valid){
				int i = items.stackTagCompound.getInteger(in.ident);
				if(i>=0){
					k += (i==1 ? 1.5 : (i==2 ? 0.5 : 1));
					n++;
				}
			}
			list.add("Output: " + ((int)items.stackTagCompound.getInteger(output) * k / n) + " RF/t");
			list.add("Input(s): ");
			for(Input in : Input.valid){
				int i = items.stackTagCompound.getInteger(in.ident);
				if(i>=0)
					list.add(in.toString() + (i==0 ? "" : (i==1 ? " Power" : " Efficency")));
			}
			list.add("Shaft: " + items.stackTagCompound.getByte(shaft) + "/3");
		}catch(Exception e){
			list.add("Instability: " + 0);
			list.add("Efficiency: " + 0);
			list.add("Output: " + 0 + " RF/t");
			list.add("Input: ");
			list.add("Shaft: " + 0 + "/3");
		}
	}
	
	public enum Input{
		COMB(0,"Combustion"),
		LIQHOT(1,"LiquidHot"),
		LIQCOLD(2, "LiquidCool"),
		REACTION(3, "LiquidReactant"),
		INVALID(-1, " ");
		
		public static final Input[] valid = {
			COMB,LIQHOT,LIQCOLD,REACTION
		};
		
		public int id;
		public String ident;
		
		public boolean isId(byte i){
			if((i | id) == i)
				return true;
			
			return false;
		}
		
		private Input(int id, String iden){
			this.id = id;
			ident = iden;
		}
		
		@Override
		public String toString() {
			switch(id){
			case 0:
				return "Combustion ";
			case 1:
				return "Liquid Combustion ";
			case 2:
				return "Coolant ";
			case 3:
				return "Reactant ";
			}
			return "";
		}

		public boolean isPart(String name) {
			return name.toLowerCase().contains(ident.toLowerCase());
		}
	}
	
	public byte writeInputs(Input[] ins) {
		byte i = 0;
		for(Input in: ins){
			i |= in.id;
		}
		return i;
	}
	
	public Input[] readInputs(byte i){
		Input[] ins = new Input[4];
		
		for(int j=0; j<Input.valid.length; j++){
			Input in = Input.valid[j];
			ins[j] = in.isId(i) ? in : null;
		}
		
		return null;
	}
	
	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ModLib.getAsset(Names.gearbox));
	}
}
