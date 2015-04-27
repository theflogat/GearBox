package fr.theflogat.gearbox.api.util;

import net.minecraft.world.World;

public class Coordinates {
	
	int x;
	int y;
	int z; 
	public World w;
	
	
	public Coordinates(int x, int y, int z, World w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}


	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public double getZ() {
		return z;
	}
}
