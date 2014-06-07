package fr.theflogat.gearbox.tile.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import fr.theflogat.gearbox.api.util.Template;
import fr.theflogat.gearbox.lib.ModLib;

public class GuiTemplate extends GuiR{
	
	protected Template temp;
	protected ResourceLocation texture = new ResourceLocation(ModLib.ModId.toLowerCase(), "textures/gui/template.png");
	
	public GuiTemplate(Container cont) {
		super(cont);
		xSize = 256;
		ySize = 256;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
