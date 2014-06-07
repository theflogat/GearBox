package fr.theflogat.gearbox.tile.gui;

import java.util.ArrayList;

import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.tile.TEDynamo;
import fr.theflogat.gearbox.tile.container.ContainerDynamo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiDynamo extends GuiContainer{

	private static final ResourceLocation texture = new ResourceLocation(ModLib.ModId.toLowerCase(), "textures/gui/dynamo.png");
	private TEDynamo tile;

	public GuiDynamo(InventoryPlayer inv, TEDynamo te) {
		super(new ContainerDynamo(inv, te));
		xSize = 175;
		ySize = 166;
		tile = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawTexturedModalRect(guiLeft + 8, guiTop + 77, 0, ySize, 160 * tile.getEnergyStored(null)/tile.getMaxEnergyStored(null) ,2);
		mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		try{
			int perc = tile.tanks[0].getFluidAmount()/tile.tanks[0].getCapacity();
			drawTexturedModelRectFromIcon(guiLeft + 13, guiTop + 18 + 50*(1-perc), tile.tanks[0].getFluid().getFluid().getIcon(), 18, 50*perc);
		}catch(Exception e){}

		try{
			int perc1 = tile.tanks[1].getFluidAmount()/tile.tanks[1].getCapacity();
			drawTexturedModelRectFromIcon(guiLeft + 77, guiTop + 18 + 50*(1-perc1), tile.tanks[1].getFluid().getFluid().getIcon(), 18, 50*perc1);
		}catch(Exception e){}

		try{
			int perc2 = tile.tanks[2].getFluidAmount()/tile.tanks[2].getCapacity();
			drawTexturedModelRectFromIcon(guiLeft + 147, guiTop + 18 + 50*(1-perc2), tile.tanks[2].getFluid().getFluid().getIcon(), 18, 50*perc2);
		}catch(Exception e){}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		if(x>=guiLeft+8 && x<=guiLeft+8+160 && y>=guiTop+77 && y<=guiTop+77+2){
			ArrayList<String> list = new ArrayList<String>();
			list.add(Integer.toString(tile.getEnergyStored(null))+ " RF");
			list.add(Integer.toString(tile.ticksRemain) + " ticks left");
			try{
				list.add(Integer.toString(tile.getStackInSlot(2).stackTagCompound.getInteger(ItemGearbox.output)) + " RF/t");
			}catch(Exception e){}
			drawHoveringText(list, x-guiLeft, y-guiTop, fontRenderer);
		}
	}
}
