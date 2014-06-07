package fr.theflogat.gearbox.render.models;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelDynamo extends ModelBase{
	private ArrayList<ModelRenderer> parts;
	private ArrayList<ModelRenderer> partsRot;
	private ModelRenderer core;

	public ModelDynamo() {
		parts = new ArrayList<ModelRenderer>();
		partsRot = new ArrayList<ModelRenderer>();

		textureHeight = 64;
		textureWidth = 64;

		core = new ModelRenderer(this,0,0);
		core.addBox(-7, -7, -7, 14, 14, 14);
		core.setRotationPoint(-8, -8, 8);

		ModelRenderer chimney = new ModelRenderer(this,0,32);
		chimney.addBox(-1.5F, -4, -1.5F, 3, 8, 3);
		chimney.setRotationPoint(0,-16,6);
		parts.add(chimney);

		ModelRenderer chimney2 = new ModelRenderer(this,0,32);
		chimney2.addBox(-1.5F, -4, -1.5F, 3, 8, 3);
		chimney2.setRotationPoint(0,-16,10);
		parts.add(chimney2);

		ModelRenderer chimney3 = new ModelRenderer(this,0,32);
		chimney3.addBox(-1.5F, -4, -1.5F, 3, 8, 3);
		chimney3.setRotationPoint(0,-16,6);
		parts.add(chimney3);

		ModelRenderer chimney4 = new ModelRenderer(this,0,32);
		chimney4.addBox(-1.5F, -4, -1.5F, 3, 8, 3);
		chimney4.setRotationPoint(0,-16,16);
		parts.add(chimney4);

		ModelRenderer chimneyRot = new ModelRenderer(this,0,32);
		chimney.addBox(-1.5F, -4, -1.5F, 3, 8, 3);
		chimney.setRotationPoint(-6,-16,16);
		partsRot.add(chimneyRot);

		ModelRenderer chimneyRot2 = new ModelRenderer(this,0,32);
		chimney2.addBox(-1.5F, -4, -1.5F, 3, 8, 3);
		chimney2.setRotationPoint(-10,-16,16);
		partsRot.add(chimneyRot2);

		ModelRenderer chimneyRot3 = new ModelRenderer(this,0,32);
		chimney3.addBox(-1.5F, -4, -1.5F, 3, 8, 3);
		chimney3.setRotationPoint(-10,-16,0);
		partsRot.add(chimneyRot3);

		ModelRenderer chimneyRot4 = new ModelRenderer(this,0,32);
		chimney4.addBox(-1.5F, -4, -1.5F, 3, 8, 3);
		chimney4.setRotationPoint(-6,-16,0);
		partsRot.add(chimneyRot4);
	}

	public void render1(float mult) {
		core.render(mult);
//		for(ModelRenderer part: partsRot){
//			part.render(mult);
//		}
	}
	
	public void render2(float mult) {
		core.render(mult);
//		for(ModelRenderer rend: parts){
//			rend.render(mult);
//		}
	}
}
