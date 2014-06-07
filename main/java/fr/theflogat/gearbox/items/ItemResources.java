package fr.theflogat.gearbox.items;

import java.util.List;

import javax.swing.Icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.theflogat.gearbox.GearBox;
import fr.theflogat.gearbox.api.GearsValue;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;

public class ItemResources extends Item{
	
	public ItemResources() {
		super();
		setUnlocalizedName(ModLib.getId(Names.resource));
		setCreativeTab(GearBox.gears);
		setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List list, boolean moreInfo) {
		for(int i = 0; i<Names.desc.length-2; i++){
			if(items.getItemDamage()==i)
				list.add(Names.desc[i]);
		}
		
		for(int i = Names.desc.length-2; i<34; i++){
			if(items.getItemDamage()==i){
				GearsValue v = GearsValue.getValue(Names.resources[i]);
				list.add("Instability : " + v.inst);
				list.add("Efficiency : " + v.eff);
				list.add("Output : " + v.out + " RF/t");
			}
		}
		
		for(int i = 34; i<34+8; i++){
			if(items.getItemDamage()==i){
				if(i<38){
					list.add(Names.desc[i-34]);
					list.add(Names.desc[8]);
				}else{
					list.add(Names.desc[i-38]);
					list.add(Names.desc[7]);
				}
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack items) {
		return ModLib.getId(Names.resource) + Names.resources[items.getItemDamage()];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i<Names.resources.length; i++){
			list.add(new ItemStack(this, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons = new IIcon[Names.resources.length];
	
	@Override
	public void registerIcons(IIconRegister reg) {
		for(int i = 0; i<Names.resources.length; i++){
			icons[i] = reg.registerIcon(ModLib.getAsset(Names.resources[i]));
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int i) {
		return icons[i];
	}
}