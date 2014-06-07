package fr.theflogat.gearbox.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.theflogat.gearbox.GearBox;
import fr.theflogat.gearbox.api.GearsValue;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.lib.Names;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemResources extends Item{
	
	public ItemResources(int id) {
		super(id);
		setUnlocalizedName(ModLib.getId(Names.resource));
		setCreativeTab(GearBox.gears);
		setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List list, boolean moreInfo) {
		for(int i = 0; i<Names.desc.length; i++){
			if(items.getItemDamage()==i)
				list.add(Names.desc[i]);
		}
		
		for(int i = Names.desc.length; i<Names.resources.length; i++){
			if(items.getItemDamage()==i){
				GearsValue v = GearsValue.getValue(Names.resources[i]);
				list.add("Instability : " + v.inst);
				list.add("Efficiency : " + v.eff);
				list.add("Output : " + v.out + " RF/t");
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack items) {
		return ModLib.getId(Names.resource) + Names.resources[items.getItemDamage()];
	}
	
	@Override
	public void getSubItems(int j, CreativeTabs creativeTab, List list) {
		for(int i = 0; i<Names.resources.length; i++){
			list.add(new ItemStack(this, 1, i));
		}
	}
	
	public Icon[] icons = new Icon[Names.resources.length];
	
	@Override
	public void registerIcons(IconRegister reg) {
		for(int i = 0; i<Names.resources.length; i++){
			icons[i] = reg.registerIcon(ModLib.getAsset(Names.resources[i]));
		}
	}
	
	@Override
	public Icon getIconFromDamage(int i) {
		return icons[i];
	}
}
