package HxCKDMS.HxCLinkPads.TileEntities;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.Utils.AABBUtils;
import HxCKDMS.HxCCore.Utils.Teleporter;
import HxCKDMS.HxCLinkPads.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityLinkPad extends TileEntity{
    public int[] OtherPos;
    public int[] RGB = new int[]{0, 0, 0};
    public boolean AllowUpdate;
    public int TargetDim;

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        writeSyncableDataToNBT(tagCompound);
        
        if(!AllowUpdate) tagCompound.setBoolean("Enabled", AllowUpdate);
        if(OtherPos != null)
            tagCompound.setIntArray("BoundBlockPos", OtherPos);
        
    }

    private void writeSyncableDataToNBT(NBTTagCompound tagCompound) {
        tagCompound.setIntArray("RGB", RGB);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        readSyncableDataFromNBT(tagCompound);
        
        this.OtherPos = tagCompound.getIntArray("BoundBlockPos");
        try{
            AllowUpdate = tagCompound.getBoolean("Enabled");
        }catch(NullPointerException ignored){}
    }

    private void readSyncableDataFromNBT(NBTTagCompound tagCompound) {
        this.RGB = tagCompound.getIntArray("RGB");
    }

    public void updateEntity(){
        if(worldObj != null && !worldObj.isRemote && AllowUpdate) Link(new int[]{xCoord, yCoord, zCoord}, worldObj);
        if(worldObj != null && !worldObj.isRemote)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeSyncableDataToNBT(syncData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readSyncableDataFromNBT(pkt.func_148857_g());
    }

    public void Link(int[] coords, World world){
        TileEntity tile = world.getTileEntity(coords[0], coords[1], coords[2]);
        TileEntityLinkPad lp = (TileEntityLinkPad)tile;
        List list  = world.getEntitiesWithinAABB(Entity.class, AABBUtils.getAreaBoundingBox(coords[0], coords[1], coords[2], 0));
        for (Entity entity : (List<Entity>) list) {
            NBTTagCompound tag = entity.getEntityData();
            int linkCooldown = tag.getInteger("LinkCooldown");
            if (linkCooldown != 0) {
                linkCooldown--;
                tag.setInteger("LinkCooldown", linkCooldown);
            }
            int posx = lp.OtherPos[0];
            int posy = lp.OtherPos[1];
            int posz = lp.OtherPos[2];
            int posdim = lp.TargetDim;
            boolean LinkPad = HxCCore.server.worldServerForDimension(posdim).getTileEntity(posx, posy, posz) instanceof TileEntityLinkPad;
            if (!entity.isDead && linkCooldown == 0 && LinkPad) {
                tag.setInteger("LinkCooldown", Config.Delay);
                if (entity instanceof EntityPlayerMP) {
                    EntityPlayerMP player = (EntityPlayerMP) entity;
                    if (player.worldObj.provider.dimensionId == posdim) {
                        player.playerNetServerHandler.setPlayerLocation(posx + 0.5, posy + 0.25, posz + 0.5, entity.rotationYaw, entity.rotationPitch);
                    } else {
                        Teleporter.transferPlayerToDimension(player, posdim, posx, posy, posz);
                    }
                } else if (entity instanceof EntityLiving) {
                    if (entity.dimension == posdim) ((EntityLiving) entity).setPositionAndUpdate(posx + 0.5, posy + 0.25, posz + 0.5);
                    else {
                        entity.travelToDimension(posdim);
                        entity.setPosition(posx, posy, posz);
                    }
                }
            }
        }
    }
}