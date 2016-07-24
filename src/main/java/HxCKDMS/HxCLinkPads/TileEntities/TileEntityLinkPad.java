package HxCKDMS.HxCLinkPads.TileEntities;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.api.Utils.AABBUtils;
import HxCKDMS.HxCCore.api.Utils.LogHelper;
import HxCKDMS.HxCCore.api.Utils.Teleporter;
import HxCKDMS.HxCLinkPads.Configurations;
import HxCKDMS.HxCLinkPads.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.io.File;
import java.util.List;

public class TileEntityLinkPad extends TileEntity{
    private int[] RGB = new int[]{0, 0, 0}, OtherPos;
    private short delay;
    private AxisAlignedBB box;
    private String owner;
    private byte type = 0;

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        writeSyncableDataToNBT(tagCompound);
        if(OtherPos != null) tagCompound.setIntArray("BoundBlockPos", OtherPos);
        if(owner != null) tagCompound.setString("Owner", owner);
        if(type != 0) tagCompound.setByte("Type", type);
    }

    private void writeSyncableDataToNBT(NBTTagCompound tagCompound) {
        tagCompound.setIntArray("RGB", RGB);
        tagCompound.setByte("Type", type);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        readSyncableDataFromNBT(tagCompound);
        
        OtherPos = tagCompound.getIntArray("BoundBlockPos");
        owner = tagCompound.getString("Owner");
        type = tagCompound.getByte("Type");
    }

    private void readSyncableDataFromNBT(NBTTagCompound tagCompound) {
        RGB = tagCompound.getIntArray("RGB");
        type = tagCompound.getByte("Type");
    }

    @Override
    public void updateEntity(){
        if (worldObj != null && !worldObj.isRemote) {
            box = AABBUtils.getAreaBoundingBox(xCoord, yCoord, zCoord, 0);
            if (hasLink() && delay == 0) {
                delay = Link(worldObj) ? Configurations.Delay : delay;
            } else if (delay > 0) delay--;
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound syncData = new NBTTagCompound();
        writeSyncableDataToNBT(syncData);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readSyncableDataFromNBT(pkt.func_148857_g());
    }

    private boolean hasLink() {
        switch (Configurations.LinkType) {
            case "WorldMap" : return getWorldTile() != null && getWorldTile().length > 2;
            case "PlayerMap" : return getPlayerTile() != null && getPlayerTile().length > 2;
            default : return OtherPos != null && OtherPos.length > 2;
        }
    }

    private void setLink(int[] arr) {
        switch (Configurations.LinkType) {
            case "WorldMap" : setWorldTile(arr); break;
            case "PlayerMap" : setPlayerTile(arr); break;
            default : OtherPos = arr; break;
        }
    }

    public int[] getRGB() {
        return RGB;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte t) {
        type = t;
    }

    private void setWorldTile(int[] arr) {
        NBTFileIO.setIntArray(HxCCore.CustomWorldData, "BoundTile_" + xCoord + "_" + yCoord + "_" + zCoord + "_" + worldObj.provider.dimensionId, arr);
        NBTFileIO.setIntArray(HxCCore.CustomWorldData, "BoundTile_" + arr[0] + "_" + arr[1] + "_" + arr[2] + "_" + arr[3], new int[]{xCoord, yCoord, zCoord, worldObj.provider.dimensionId});
    }

    private int[] getWorldTile() {
        OtherPos = NBTFileIO.getIntArray(HxCCore.CustomWorldData, "BoundTile_" + xCoord + "_" + yCoord + "_" + zCoord + "_" + worldObj.provider.dimensionId);
        return OtherPos;
    }

    public String getReadableBoundLocation() {
        if (OtherPos == null || OtherPos.length < 4) return " Nothing";
        else return " X: " + OtherPos[0] + " Y: " + OtherPos[1] + " Z: " + OtherPos[2] + " Dim: " + OtherPos[3];
    }

    private void setPlayerTile(int[] arr) {
        String UUID = !owner.isEmpty() ? HxCCore.server.getEntityWorld().getPlayerEntityByName(owner).getGameProfile().getId().toString() : "";
        if (UUID.isEmpty()) return;
        File data = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
        NBTFileIO.setIntArray(data, "BoundTile_" + xCoord + "_" + yCoord + "_" + zCoord + "_" + worldObj.provider.dimensionId, arr);
        NBTFileIO.setIntArray(data, "BoundTile_" + arr[0] + "_" + arr[1] + "_" + arr[2] + "_" + arr[3], new int[]{xCoord, yCoord, zCoord, worldObj.provider.dimensionId});
    }

    public void setOwner(String player) {
        owner = player;
    }

    private int[] getPlayerTile() {
        String UUID = !owner.isEmpty() ? HxCCore.server.getEntityWorld().getPlayerEntityByName(owner).getGameProfile().getId().toString() : "";
        if (UUID.isEmpty()) return null;
        File data = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
        OtherPos = NBTFileIO.getIntArray(data, "BoundTile_" + xCoord + "_" + yCoord + "_" + zCoord + "_" + worldObj.provider.dimensionId);
        return OtherPos;
    }

    public void bind(int[] coords) {
        setLink(coords);
    }

    public boolean rgb(byte colour, byte amount) {
        RGB[colour] = RGB[colour] + amount > 255 ? 255 : RGB[colour] + amount < 0 ? 0 : RGB[colour] + amount;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return !(RGB[colour] == 255 || RGB[colour] == 0);
    }

    @SuppressWarnings("unchecked")
    private boolean Link(World world) {
        TileEntityLinkPad lp = this;
        List list  = world.getEntitiesWithinAABB(Entity.class, box);
        if (Configurations.PlayersOnly)
            list = world.getEntitiesWithinAABB(EntityPlayer.class, box);
        if (list.isEmpty()) return false;
        for (Entity entity : (List<Entity>) list) {
            switch (type) {
                case 0: break;
                case 1: if (!(entity instanceof EntityPlayer)) continue; break;
                case 2: if (!(entity instanceof EntityLiving)) continue; break;
                case 3: if (!(entity instanceof EntityCreature)) continue; break;
                case 4: if (!(entity instanceof EntityItem)) continue; break;
            }
            try {
                int PosX = lp.OtherPos[0], PosY = lp.OtherPos[1], PosZ = lp.OtherPos[2], Dimension = lp.OtherPos.length == 4 ? lp.OtherPos[3] : entity.dimension;
                TileEntity t = HxCCore.server.worldServerForDimension(Dimension).getTileEntity(PosX, PosY, PosZ);
                boolean LinkPad = t instanceof TileEntityLinkPad;
                if (!entity.isDead && LinkPad) {
                    TileEntityLinkPad tile = (TileEntityLinkPad) t;
                    if (entity instanceof EntityPlayerMP && (entity.isSneaking() || !Configurations.RequireSneaking)) {
                        EntityPlayerMP player = (EntityPlayerMP) entity;
                        if (player.worldObj.provider.dimensionId == Dimension)
                            player.playerNetServerHandler.setPlayerLocation(PosX + 0.5, PosY + 0.25, PosZ + 0.5, entity.rotationYaw, entity.rotationPitch);
                        else Teleporter.transferPlayerToDimension(player, Dimension, PosX, PosY, PosZ);
                    } else if (!Configurations.PlayersOnly && entity instanceof EntityLiving) {
                        if (entity.dimension == Dimension) {
                            ((EntityLiving) entity).setPositionAndUpdate(PosX + 0.5, PosY + 0.25, PosZ + 0.5);
                        } else {
                            entity.travelToDimension(Dimension);
                            entity.setPosition(PosX, PosY, PosZ);
                        }
                    } else if (!Configurations.PlayersOnly) {
                        if (entity.dimension == Dimension) {
                            entity.setPosition(PosX + 0.5, PosY + 0.25, PosZ + 0.5);
                        } else {
                            entity.travelToDimension(Dimension);
                            entity.setPosition(PosX, PosY, PosZ);
                        }
                    }
                    tile.delay = Configurations.Delay;
                }
            } catch (Exception e) {
                if (HxCKDMS.HxCCore.Configs.Configurations.DebugMode)
                    e.printStackTrace();
                LogHelper.debug(e.getLocalizedMessage(), Reference.MOD_NAME);
                return false;
            }
        }
        return true;
    }
}