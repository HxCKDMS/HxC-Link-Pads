package HxCKDMS.HxCLinkPads.TileEntities;

import HxCKDMS.HxCLinkPads.Events.EventLink;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLinkPad extends TileEntity{
    public int[] OtherPos;
    public int[] RGB = new int[]{0, 0, 0};
    public boolean AllowUpdate = false;
    EventLink Link = new EventLink();

    public int TargetDim;

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        writeSyncableDataToNBT(tagCompound);
        
        tagCompound.setBoolean("Enabled", AllowUpdate);
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
            this.AllowUpdate = tagCompound.getBoolean("Enabled");
        }catch(NullPointerException ignored){}
    }

    private void readSyncableDataFromNBT(NBTTagCompound tagCompound) {
        this.RGB = tagCompound.getIntArray("RGB");
    }

    public void updateEntity(){
        if(worldObj != null && !worldObj.isRemote && AllowUpdate) Link.Link(new int[]{xCoord, yCoord, zCoord}, worldObj);
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
}