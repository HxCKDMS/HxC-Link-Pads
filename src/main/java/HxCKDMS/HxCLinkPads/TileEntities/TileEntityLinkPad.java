package HxCKDMS.HxCLinkPads.TileEntities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

@SuppressWarnings("unchecked")
public class TileEntityLinkPad extends TileEntity{
    public int[] NewPos;
    public int[] OtherPos = new int[4];

    public int[] RGB = new int[]{0, 0, 0};

    @Override
    public void writeToNBT(NBTTagCompound par1) {
        super.writeToNBT(par1);
        int[] data = new int[4];
        try {
            data[0] = NewPos[0];
            data[1] = NewPos[1];
            data[2] = NewPos[2];
            data[3] = NewPos[3];
        } catch (Exception e){
            data[0] = xCoord;
            data[1] = yCoord;
            data[2] = zCoord;
            data[3] = worldObj.provider.dimensionId;
        }
        par1.setIntArray("BoundBlockPos", data);
        par1.setIntArray("RGB", RGB);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1) {
        super.readFromNBT(par1);
        OtherPos = par1.getIntArray("BoundBlockPos");
        RGB = par1.getIntArray("RGB");
    }
	
    public void updateEntity(){ if(worldObj != null && !worldObj.isRemote){ Search(); } }

    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) { readFromNBT(packet.func_148857_g()); }
	
    protected AxisAlignedBB getAreaBoundingBox(float x, float y, float z) { return AxisAlignedBB.getBoundingBox(x, y, z, x+1, y + 1, z+1);}

    public void Search() {
        List list  = worldObj.getEntitiesWithinAABB(Entity.class, getAreaBoundingBox(xCoord, yCoord, zCoord));
        for (Entity entity : (List<Entity>) list) {
            if (!entity.isDead) {
                entity.setPosition(OtherPos[0], OtherPos[1], OtherPos[2]);
            }
        }
    }
	
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tagCompound);
    }
}