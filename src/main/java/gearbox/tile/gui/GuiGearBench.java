package fr.theflogat.gearbox.tile.gui;

import org.lwjgl.opengl.GL11;

import fr.theflogat.gearbox.api.util.Template;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.tile.TEGearBench;
import fr.theflogat.gearbox.tile.buttons.Tab;
import fr.theflogat.gearbox.tile.container.ContainerGearBench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiGearBench extends GuiR{

	private TEGearBench te;
	private InventoryPlayer inv;
	public static final ResourceLocation texture = new ResourceLocation(ModLib.ModId.toLowerCase(), "textures/gui/gearBench.png");
	public static final ResourceLocation tab = new ResourceLocation(ModLib.ModId.toLowerCase(), "textures/gui/gearBenchTab.png");
	public static final Tab[] tabs= {
		new Tab(-16, 2, 65, 0, 16, 17, tab, 0),
		new Tab(-16, 19, 65, 65, 16, 17, tab, 1)
	};
	private static final String[] help = {
		"More than 1 ouput shaft",
		"won't work. You can have",
		"multiple input shafts.",
		"Efficiency is multiplicative.",
		"Output is additive. At",
		"intersections power is /2",
		"efficiency is *2.",
		"Cannot remove a shaft if",
		"gears are attached to it.",
		"Cannot add a gear without",
		"a shaft to hook onto.",
		"If instability >20 then",
		"it won't work."
	};
	
	private static final String[] out = {
		
	};
	
	private int activeTab = -1;
	
	
	public GuiGearBench(InventoryPlayer inv, TEGearBench tile) {
		super(new ContainerGearBench(inv, tile));
		te = tile;
		this.inv = inv;
		xSize = 256;
		ySize = 256;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j){
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(activeTab==-1 || activeTab>1){
			for(Tab tab : tabs){
				tab.drawSmall(this);
			}
		} else {
			for(Tab tab : tabs){
				if(tab.id==activeTab){
					switch (tab.id) {
					case 0:
						tab.drawLarge(this, -128, tab.y, 128, 0, 128, 128);
						tab.drawText(this, help, -128+1, tab.y+1);
						break;
					case 1:
						tab.drawLarge(this, -128, tab.y, 128, 128, 128, 128);
						tab.drawStatText(this, new Template(te), -128+1, tab.y+1);
						break;
					}
				} else {
					switch (tab.id) {
					case 0:
						tab.drawSmall(this);
						break;
					case 1:
						tab.drawLarge(this, -16, 130, 65, 65, 16, 17);
						break;
					}
				}
			}
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int par3) {
		for(Tab tab : tabs){
			if(tab.isInRegion(this, mouseX, mouseY, tab.id!=0 && activeTab==0))
				activeTab = tab.id;
		}
		
		super.mouseClicked(mouseX, mouseY, par3);
	}
}
