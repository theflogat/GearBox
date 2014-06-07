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
	}

	public void render(float mult) {
		core.render(mult);
	}
}
