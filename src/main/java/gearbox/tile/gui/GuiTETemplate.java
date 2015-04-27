package fr.theflogat.gearbox.tile.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.tile.TETemplate;
import fr.theflogat.gearbox.tile.container.ContainerTemplate;
import fr.theflogat.gearbox.tile.slot.SlotFixed;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiTETemplate extends GuiTemplate {
	
	public static final ResourceLocation slot = new ResourceLocation(ModLib.ModId.toLowerCase(), "textures/gui/slot.png");
	TETemplate te;

	public GuiTETemplate(InventoryPlayer inv, TETemplate te) {
		super(new ContainerTemplate(inv, te));
		this.te = te;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int par1, int par2) {
		super.drawGuiContainerBackgroundLayer(f, par1, par2);
		mc.renderEngine.bindTexture(slot);
		int size = 18;
		for(int z = 0; z < 9; z++) {
			for(int a = 0; a < 9; a++){
				Color color = te.doesSlotCorrespond(a + z * 9) ? Color.GREEN : Color.RED;
				GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
				drawTexturedModalRect(guiLeft + 49 + a * size, guiTop + 2 + z * size, 0, 0, size, size);
			}
		}
		GL11.glColor3f(0, 0, 0);
	}
}
