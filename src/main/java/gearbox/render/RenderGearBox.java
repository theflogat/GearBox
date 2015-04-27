package fr.theflogat.gearbox.render;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.render.models.ModelGearbox;

public class RenderGearBox extends TileEntitySpecialRenderer{
	
	public static ResourceLocation texture = new ResourceLocation(ModLib.ModId.toLowerCase(), "textures/models/gearbox.png");
	public ModelGearbox model;
	
	public RenderGearBox() {
		model = new ModelGearbox();
	}
	
	
	@Override
	public void renderTileEntityAt(TileEntity item, double x, double y,	double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F,-1F,1F);
		
		bindTexture(texture);
		model.render(0.0625F);
		
		GL11.glPopMatrix();
	}

}