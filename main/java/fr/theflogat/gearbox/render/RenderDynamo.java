package fr.theflogat.gearbox.render;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.render.models.ModelDynamo;

public class RenderDynamo extends TileEntitySpecialRenderer{
	
	public static ResourceLocation texture = new ResourceLocation(ModLib.ModId.toLowerCase(), "textures/models/dynamo.png");
	public ModelDynamo model;
	
	public RenderDynamo() {
		model = new ModelDynamo();
	}
	
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y,	double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F,-1F,1F);
		
		bindTexture(texture);
		if(te.blockMetadata==2||te.blockMetadata==3){
			model.render1(0.0625F);
		}else{
			model.render2(0.0625F);
		}
		
		GL11.glPopMatrix();
	}

}