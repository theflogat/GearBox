package fr.theflogat.gearbox.tile.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import fr.theflogat.gearbox.lib.GuiIds;
import fr.theflogat.gearbox.tile.TEDynamo;
import fr.theflogat.gearbox.tile.TEGearBench;
import fr.theflogat.gearbox.tile.TETemplate;
import fr.theflogat.gearbox.tile.container.ContainerDynamo;
import fr.theflogat.gearbox.tile.container.ContainerGearBench;
import fr.theflogat.gearbox.tile.container.ContainerTemplate;
import fr.theflogat.gearbox.tome.GuiTomeTemplate;
import fr.theflogat.gearbox.tome.GuiTypesTome;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null){
			switch(id){
			case GuiIds.gearBench:
				return new ContainerGearBench(player.inventory, (TEGearBench) tile);
			case GuiIds.dynamo:
				return new ContainerDynamo(player.inventory, (TEDynamo) tile);
			case GuiIds.tome:
				return null;
			case GuiIds.tetemp:
				return new ContainerTemplate(player.inventory, (TETemplate) tile);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,	int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);

		switch(id){
		case GuiIds.gearBench:
			if(tile != null)
				return new GuiGearBench(player.inventory, (TEGearBench) tile);
		case GuiIds.dynamo:
			if(tile != null)
				return new GuiDynamo(player.inventory, (TEDynamo) tile);
		case GuiIds.tome:
			return new GuiTypesTome();
		case GuiIds.tetemp:
			return new GuiTETemplate(/*(TETemplate)tile).getNBT(),*/ player.inventory, (TETemplate) tile);
		}

		return null;
	}
}
