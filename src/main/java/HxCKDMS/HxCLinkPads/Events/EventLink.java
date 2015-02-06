package HxCKDMS.HxCLinkPads.Events;

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
        List list  = world.getEntitiesWithinAABB(Entity.class, getAreaBoundingBox(coords[0], coords[1], coords[2]));
        TileEntity tile = world.getTileEntity(coords[0], coords[1], coords[2]);
        TileEntityLinkPad lp = (TileEntityLinkPad)tile;
        for (Entity entity : (List<Entity>) list) {
            NBTTagCompound tag = entity.getEntityData();
            int linkCooldown = tag.getInteger("LinkCooldown");
            if (linkCooldown != -0) {
                linkCooldown--;
                tag.setInteger("LinkCooldown", linkCooldown);
            }
            if (!entity.isDead && linkCooldown == 0) {
                tag.setInteger("LinkCooldown", 60);
                int posx = lp.OtherPos[0];
                int posy = lp.OtherPos[1];
                int posz = lp.OtherPos[2];
                int posdim = lp.TargetDim;
                if (entity instanceof EntityPlayerMP) ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(posx+0.5, posy+0.25, posz+0.5, entity.rotationYaw, entity.rotationPitch);
                else if (entity instanceof EntityLiving) ((EntityLiving) entity).setPositionAndUpdate(posx+0.5, posy+0.25, posz+0.5);
                else entity.setPosition(posx+0.5, posy+0.25, posz+0.5);
            }
        }
    }

    protected AxisAlignedBB getAreaBoundingBox(float x, float y, float z) { return AxisAlignedBB.getBoundingBox(x-0.5, y, z-0.5, x+0.5, y + 2, z+0.5);}
}
