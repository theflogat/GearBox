package fr.theflogat.gearbox.tile.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class GuiR extends GuiContainer{

	public GuiR(Container par1Container) {
		super(par1Container);
	}
	
	public int getLeft() {
		return guiLeft;
	}
	
	public int getTop() {
		return guiTop;
	}
	
	public FontRenderer getFont() {
		return fontRendererObj;
	}
	
	public Minecraft getMc() {
		return mc;
	}
}
