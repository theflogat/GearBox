package fr.theflogat.gearbox.render.models;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelGearbox extends ModelBase{
	
	private ArrayList<ModelRenderer> parts;
	
	public ModelGearbox() {
		textureHeight = 64;
		textureWidth = 64;
		parts = new ArrayList<ModelRenderer>();
		
		ModelRenderer core = new ModelRenderer(this,0,0);
		core.addBox(-8, -8, -8, 16, 16, 16);
		core.setRotationPoint(-8, -8, 8);
		parts.add(core);
		
		ModelRenderer chimney = new ModelRenderer(this,0,32);
		chimney.addBox(-1.5F, -4, -1.5F, 3, 8, 3);
		chimney.setRotationPoint(0,-16,5);
		parts.add(chimney);
	}
	
	
	public void render(float mult) {
		for(ModelRenderer part:parts){
			part.render(mult);
		}
	}
}
