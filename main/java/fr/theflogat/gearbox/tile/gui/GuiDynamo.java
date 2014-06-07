package fr.theflogat.gearbox.tile.gui;

import java.util.ArrayList;

import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.items.ItemGearbox.Input;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.tile.TEDynamo;
import fr.theflogat.gearbox.tile.container.ContainerDynamo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidContainerRegistry;

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
			float filled = tile.tanks[0].getFluidAmount()/4000;
			float height = 50F * filled;
			if(height>0){
				IIcon icon = tile.tanks[0].getFluid().getFluid().getIcon();
				drawTexturedModelRectFromIcon(guiLeft + 13, guiTop + 18 + 50 - ((int)Math.floor(height)), icon, 18, (int) Math.floor(height));
			}
		}catch(Exception e){}

		try{
			float filled2 = tile.tanks[1].getFluidAmount()/4000;
			float height2 = 50F * filled2;
			if(height2>0){
				drawTexturedModelRectFromIcon(guiLeft + 13, (int) (guiTop + 18 + 50 - height2),
					tile.tanks[1].getFluid().getFluid().getIcon(), 18, (int) height2);
			}
		}catch(Exception e){}

		try{
			float filled3 = tile.tanks[2].getFluidAmount()/4000;
			int height3 = (int) (50F * filled3);
			if(height3>0){
				drawTexturedModelRectFromIcon(guiLeft + 13, guiTop + 18 + 50 - height3,
					tile.tanks[2].getFluid().getFluid().getIcon(), 18, height3);
			}
		}catch(Exception e){}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		if(x>=guiLeft+8 && x<=guiLeft+8+160 && y>=guiTop+77 && y<=guiTop+77+2){
			ArrayList<String> list = new ArrayList<String>();
			list.add(Integer.toString(tile.getEnergyStored(null))+ " RF");
			list.add(Integer.toString(tile.ticksRemain) + " Ticks left");
			try{
				float k = 0;
				int n = 0;
				for(Input in : Input.valid){
					int i = tile.getStackInSlot(2).stackTagCompound.getInteger(in.ident);
					if(i>=0){
						list.add(in.toString() + (i==0 ? "" : (i==1 ? " Power" : " Efficency")));
						k += (i==1 ? 1.5 : (i==2 ? 0.5 : 1));
						n++;
					}
				}
				list.add(Integer.toString((int) (tile.getStackInSlot(2).stackTagCompound.getInteger(ItemGearbox.output)* k / n)) + " RF/t");
			}catch(Exception e){}
			drawHoveringText(list, x-guiLeft, y-guiTop, fontRendererObj);
		}
	}
}
