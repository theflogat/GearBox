package fr.theflogat.gearbox.api.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldUtil {
	public static void spawnItemStack(Coordinates coords, ItemStack item){
		if (item == null) {
			return;
		}
		double dx = coords.w.rand.nextFloat() * 0.5D - 0.5D;
		double dy = coords.w.rand.nextFloat() * 0.5D - 0.5D;
	    double dz = coords.w.rand.nextFloat() * 0.5D - 0.5D;
	    
	    EntityItem entityitem = new EntityItem(coords.w, coords.getX() + 0.5D, coords.getY() + 0.5D, coords.getZ() + 0.5D, item);
	    entityitem.motionY = (0.2D + coords.w.rand.nextGaussian() * 0.02D);
	    entityitem.motionX = (coords.w.rand.nextGaussian() * 0.02D);
	    entityitem.motionZ = (coords.w.rand.nextGaussian() * 0.02D);
	    coords.w.spawnEntityInWorld(entityitem);
	}
	
	/**
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @param player
	 * @return meta
	 * 
	 * Copied from pistonBase
	 */
    public static int determineOrientation(World w, int x, int y, int z, EntityLivingBase player)
    {
        if (MathHelper.abs((float)player.posX - (float)x) < 2.0F && MathHelper.abs((float)player.posZ - (float)z) < 2.0F)
        {
            double d0 = player.posY + 1.82D - (double)player.yOffset;

            if (d0 - (double)y > 2.0D)
            {
                return 1;
            }

            if ((double)y - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }
}