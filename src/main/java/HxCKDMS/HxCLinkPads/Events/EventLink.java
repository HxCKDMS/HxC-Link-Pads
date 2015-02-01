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
        world.markBlockForUpdate(coords[0], coords[1], coords[2]);
        List list  = world.getEntitiesWithinAABB(Entity.class, getAreaBoundingBox(coords[0], coords[1], coords[2]));
        TileEntity tile = world.getTileEntity(coords[0], coords[1], coords[2]);
        TileEntityLinkPad lp = (TileEntityLinkPad)tile;
        lp.getDescriptionPacket();
        for (Entity entity : (List<Entity>) list) {
            NBTTagCompound tag = entity.getEntityData();
            int linkCooldown = tag.getInteger("LinkCooldown");
            if (linkCooldown != -0) {
                linkCooldown--;
                tag.setInteger("LinkCooldown", linkCooldown);
            }
            if (!entity.isDead && linkCooldown == 0) {
                tag.setInteger("LinkCooldown", 60);
                int posx = lp.TargetX;
                int posy = lp.TargetY;
                int posz = lp.TargetZ;
                int posdim = lp.TargetDim;
                if (entity instanceof EntityPlayerMP) ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(posx+0.5, posy+1, posz+0.5, entity.rotationYaw, entity.rotationPitch);
                else if (entity instanceof EntityLiving) ((EntityLiving) entity).setPositionAndUpdate(posx+0.5, posy+1, posz+0.5);
                else entity.setPosition(posx+0.5, posy+1, posz+0.5);
            }
        }
    }

    protected AxisAlignedBB getAreaBoundingBox(float x, float y, float z) { return AxisAlignedBB.getBoundingBox(x-0.5, y, z-0.5, x+0.5, y + 2, z+0.5);}
}
