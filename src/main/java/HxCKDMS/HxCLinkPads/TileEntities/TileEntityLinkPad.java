package HxCKDMS.HxCLinkPads.TileEntities;

import HxCKDMS.HxCLinkPads.Events.EventLink;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

@SuppressWarnings("unchecked")
public class TileEntityLinkPad extends TileEntity{
    public int[] NewPos;
    public int[] OtherPos;
//    private static int timer = 30;
    public int[] RGB = new int[]{0, 0, 0};
    public int x;
    public int y;
    public int z;
    private int[] coords = new int[3];
    public boolean AllowUpdate;
    public boolean Enabled;
    EventLink Link = new EventLink();

    public int TargetX;
    public int TargetY;
    public int TargetZ;
    public int TargetDim;

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
        par1.setBoolean("Enabled", AllowUpdate);
        par1.setInteger("x", x);
        par1.setInteger("y", y);
        par1.setInteger("z", z);
        par1.setIntArray("BoundBlockPos", data);
        par1.setIntArray("RGB", RGB);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1) {
        super.readFromNBT(par1);
        this.OtherPos = par1.getIntArray("BoundBlockPos");
        this.TargetX = OtherPos[0];
        this.TargetY = OtherPos[1];
        this.TargetZ = OtherPos[2];
        this.TargetDim = OtherPos[3];
        this.RGB = par1.getIntArray("RGB");
        this.x = par1.getInteger("x");
        this.y = par1.getInteger("y");
        this.z = par1.getInteger("z");
        this.Enabled = par1.getBoolean("Enabled");
    }

    @Override
    public boolean canUpdate() {
        return super.canUpdate();
    }

    public void updateEntity(){
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
        if(worldObj != null && !worldObj.isRemote && Enabled){
            Link.Link(coords, worldObj);
        }
    }

    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) { readFromNBT(packet.func_148857_g()); }

    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tagCompound);
    }
}