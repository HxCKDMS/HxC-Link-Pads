package HxCKDMS.HxCLinkPads.Events;

import HxCKDMS.HxCLinkPads.Portals;
import HxCKDMS.HxCLinkPads.TileEntities.PortalTileEnt;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Events {
	public static void link(EntityPlayer player, NBTTagCompound dat, int x, int y, int z){
        int[] pb;
        int[] pb2 = new int[5];
        pb2[0] = x; pb2[1] = y; pb2[2] = z; pb2[3] = player.dimension;
        ItemStack linker = player.getHeldItem();
        NBTTagCompound linkdat = linker.getTagCompound();
        Block block = player.worldObj.getBlock(x, y, z);
        PortalTileEnt tileEntity = (PortalTileEnt)player.worldObj.getTileEntity(x, y, z);
		if(block == Portals.blockLinkpad && player.getHeldItem().getItem() == Portals.itemLinker) {
            try {
                pb = linkdat.getIntArray("PB");
            } catch (Exception e) {
                pb = new int[4];
                pb[0] = 0;
            }

            if (pb[4] == 1) pb2[4] = 2;
            else if (pb[4] == 2) pb2[4] = 0;
            else pb2[4] = 1;
            linkdat.setIntArray("PB", pb2);
            linker.setTagCompound(linkdat);
            tileEntity.NewPos = pb;
            player.worldObj.markBlockForUpdate(x, y, z);
		}
	}
	
	public static void check(EntityPlayer player, ItemStack is, int x, int y, int z, int meta){
		player.worldObj.setBlockMetadataWithNotify(x, y, z, meta, 2);
	}
}
