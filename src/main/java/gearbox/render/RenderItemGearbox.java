package fr.theflogat.gearbox.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import fr.theflogat.gearbox.items.ItemGearbox;
import fr.theflogat.gearbox.lib.ModLib;
import fr.theflogat.gearbox.render.models.ModelGearbox;

public class RenderItemGearbox implements IItemRenderer{
	
	public ModelGearbox model;
	public static ResourceLocation texture = new ResourceLocation(ModLib.ModId.toLowerCase(), "textures/models/gearbox.png");
	private static RenderItem renderItem = new RenderItem();
	
	public RenderItemGearbox() {
		model = new ModelGearbox();
	}

	public boolean handleRenderType(ItemStack item, ItemRenderType type){
		switch (type)
		{
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case INVENTORY:
		case ENTITY:
			return true;

		default:
			return false;
		}
	}

	public boolean shouldUseRenderHelper(ItemRenderType var1, ItemStack var2, ItemRendererHelper var3)
	{
		return false;
	}

	public void renderItem(ItemRenderType type, ItemStack item, Object ... data)
	{
		switch (type)
		{
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
			GL11.glScalef(-1F,-1F,1F);
			GL11.glTranslatef(0F, 0F, 0F);
			GL11.glRotatef(0, 0, 0, 0);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			this.model.render(0.0625F);
			GL11.glPopMatrix();
			break;
		case INVENTORY:
			GL11.glPushMatrix();
			GL11.glScalef(-16F,-16F,16F);
			GL11.glTranslatef(0F, 0F, 0);
			GL11.glRotatef(0, 0, 0, 0);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			model.render(0.0625F);
			
			GL11.glPopMatrix();
			break;

		case ENTITY:
			GL11.glPushMatrix();
			GL11.glScalef(-1F,-1F,1F);
			GL11.glTranslatef(0F, 0F, 0);
			GL11.glRotatef(0, 0, 0, 0);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			model.render(0.0625F);
			
			GL11.glPopMatrix();
			break;
		default:
		}
	}
}
