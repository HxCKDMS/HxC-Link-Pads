package HxCKDMS.HxCLinkPads.Events;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings("unchecked")
public class EventLink {
    public void Link(int[] coords, World world){
        TileEntity tile = world.getTileEntity(coords[0], coords[1], coords[2]);
        TileEntityLinkPad lp = (TileEntityLinkPad)tile;
        int nx; int ny; int nz;
        if (coords[0] >= 0) nx = coords[0] + 1;
        else nx = coords[0] - 1;
        if (coords[1] >= 0) ny = coords[1] + 1;
        else ny = coords[1] - 1;
        if (coords[2] >= 0) nz = coords[2] + 1;
        else nz = coords[2] - 1;
        List list  = world.getEntitiesWithinAABB(Entity.class, getAreaBoundingBox(coords[0], coords[1], coords[2], nx, ny, nz));
        for (Entity entity : (List<Entity>) list) {
            NBTTagCompound tag = entity.getEntityData();
            int linkCooldown = tag.getInteger("LinkCooldown");
            if (linkCooldown != -0) {
                linkCooldown--;
                tag.setInteger("LinkCooldown", linkCooldown);
            }
            int posx = lp.OtherPos[0];
            int posy = lp.OtherPos[1];
            int posz = lp.OtherPos[2];
            int posdim = lp.TargetDim;
            boolean LinkPad = HxCCore.server.worldServerForDimension(posdim).getTileEntity(posx, posy, posz) instanceof TileEntityLinkPad;
            if (!entity.isDead && linkCooldown == 0 && LinkPad) {
                tag.setInteger("LinkCooldown", 60);
                if (entity instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) entity;
//                    if (player.worldObj.provider.dimensionId == posdim) {
                        player.playerNetServerHandler.setPlayerLocation(posx + 0.5, posy + 0.25, posz + 0.5, entity.rotationYaw, entity.rotationPitch);
//                    } else {
//                        Teleporter.transferPlayerToDimension(player, posdim, HxCCore.server.getConfigurationManager(), posx, posy, posz);
//                    }
                } else if (entity instanceof EntityLiving) ((EntityLiving) entity).setPositionAndUpdate(posx+0.5, posy+0.25, posz+0.5);
                else entity.setPosition(posx+0.5, posy+0.25, posz+0.5);
            }
        }
    }

    protected AxisAlignedBB getAreaBoundingBox(int mx, int my, int mz, int Mx, int My, int Mz) {
        return AxisAlignedBB.getBoundingBox(mx, my, mz, Mx, My, Mz);
    }
}
