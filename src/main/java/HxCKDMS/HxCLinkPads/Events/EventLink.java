package HxCKDMS.HxCLinkPads.Events;

import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
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
            if (!entity.isDead) {
                world.markBlockForUpdate(coords[0], coords[1], coords[2]);
                tile.getDescriptionPacket();
                int[] pos = lp.OtherPos;
                if (entity instanceof EntityPlayerMP){
                    EntityPlayerMP player = (EntityPlayerMP)entity;
                    player.playerNetServerHandler.setPlayerLocation(pos[0], pos[1] + 2, pos[2], player.rotationYaw, player.rotationPitch);
                } else if (entity instanceof EntityVillager || entity instanceof EntityLiving) {
                    System.out.println(entity);
                    ((EntityLiving) entity).setPositionAndUpdate(pos[0], pos[1] + 2, pos[2]);
                } else {
                    System.out.println(entity);
                    entity.setPosition(pos[0], pos[1] + 2, pos[2]);
                }
            }
        }
    }

    protected AxisAlignedBB getAreaBoundingBox(float x, float y, float z) { return AxisAlignedBB.getBoundingBox(x, y, z, x, y + 1.5, z);}
}
