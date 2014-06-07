package fr.theflogat.gearbox.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import fr.theflogat.gearbox.blocks.BlockDynamo;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.render.models.ModelDynamo;

public class BlockDynamoRenderer implements ISimpleBlockRenderingHandler{

	public ModelDynamo model = new ModelDynamo();
	public ResourceLocation texture = new ResourceLocation(ModLib.ModId.toLowerCase(), "textures/models/dynamo.png");

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if(block instanceof BlockDynamo){
			GL11.glPushMatrix();
			GL11.glScalef(-1F,-1F,1F);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			model.render(0.0625F);
			
			GL11.glPopMatrix();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public int getRenderId() {
		return -1;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

}
