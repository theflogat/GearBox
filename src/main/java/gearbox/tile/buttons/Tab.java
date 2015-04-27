package fr.theflogat.gearbox.tile.buttons;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import fr.theflogat.gearbox.api.Stats;
import fr.theflogat.gearbox.api.util.Template;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.items.ItemGearbox.Input;
import fr.theflogat.gearbox.tile.gui.GuiR;

public class Tab {
	
	public int x;
	public int y;
	public int u;
	public int v;
	public ResourceLocation texture;
	public int width;
	public int height;
	public int id;
	
	public Tab(int x, int y, int u, int v, int width, int height, ResourceLocation texture, int id) {
		this.x = x;
		this.y = y;
		this.u = u;
		this.v = v;
		this.texture = texture;
		this.width = width;
		this.height = height;
		this.id = id;
	}
	
	public void drawSmall(GuiR gui){
		gui.getMc().renderEngine.bindTexture(texture);
		gui.drawTexturedModalRect(gui.getLeft()+x, gui.getTop()+y, u, v, width, height);
	}
	
	public void drawLarge(GuiR gui, int x, int y, int u, int v, int w, int h) {
		gui.getMc().renderEngine.bindTexture(texture);
		gui.drawTexturedModalRect(gui.getLeft()+x, gui.getTop()+y, u, v, w, h);
	}
	
	public boolean isInRegion(GuiR gui, int mouseX, int mouseY, boolean isOffset){
		int posX=mouseX-gui.getLeft();
		int posY=mouseY-gui.getTop();
		if(!isOffset){
			if(x<=posX && posX<=x+width && y<=posY && posY<=y+height){
			return true;
			}
		} else {
			if(x<=posX && posX<=x+width && y+112<=posY && posY<=y+112+height)
				return true;
		}
		return false;
	}

	public void drawText(GuiR gui, String[] text, int x, int y) {
		for(int i = 0; i<text.length; i++){
			String txt = text[i];
			gui.getFont().drawString(txt, gui.getLeft() + x, gui.getTop()+ y + i*8, Color.WHITE.getRGB());
		}
	}
	
	public void drawStatText(GuiR gui, Template temp, int x, int y) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Color> color = new ArrayList<Color>();
		ItemStack items = Stats.process(temp.template, temp.inv);
		if(items==null || items.stackTagCompound == null)
			return;
		try{
			list.add("      Gearbox Stats");color.add(Color.WHITE);
			color.add(items.stackTagCompound.getFloat(ItemGearbox.instab)<=20? Color.WHITE : Color.RED);
			color.add(Color.WHITE);color.add(Color.WHITE);color.add(Color.WHITE);color.add(Color.WHITE);color.add(Color.WHITE);
			color.add(Color.WHITE);color.add(Color.WHITE);color.add(Color.WHITE);color.add(Color.WHITE);color.add(Color.WHITE);
			list.add("Instability: " + items.stackTagCompound.getFloat(ItemGearbox.instab));
			list.add("Efficiency: " + items.stackTagCompound.getFloat(ItemGearbox.efficency));
			float k = 0;
			int n = 0;
			for(Input in : Input.valid){
				int i = items.stackTagCompound.getInteger(in.ident);
				if(i>=0){
					k += (i==1 ? 1.5 : (i==2 ? 0.5 : 1));
					n++;
				}
			}
			list.add("Output: " + ((int)items.stackTagCompound.getInteger(ItemGearbox.output) * k / n) + " RF/t");
			list.add("Input(s): ");
			for(Input in : Input.valid){
				int i = items.stackTagCompound.getInteger(in.ident);
				if(i>=0)
					list.add(in.toString() + (i==0 ? "" : (i==1 ? " Power" : " Efficency")));
			}
			list.add("Shaft: " + items.stackTagCompound.getByte(ItemGearbox.shaft) + "/3");
		}catch(Exception e){
			return;
		}
		
		for(int i = 0; i<list.size(); i++){
			String txt = list.get(i);
			gui.getFont().drawString(txt, gui.getLeft() + x, gui.getTop()+ y + i*8, color.get(i).getRGB() /*& 0x25BCDE*/);
		}
	}
}
