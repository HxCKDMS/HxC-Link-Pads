package HxCKDMS.HxCLinkPads.TileEntities;

import HxCKDMS.HxCLinkPads.Events.EventLink;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLinkPad extends TileEntity{
    public int[] OtherPos;
    public int[] RGB = new int[]{0, 0, 0};
    public boolean AllowUpdate;
    EventLink Link = new EventLink();

    public int TargetDim;

    @Override
    public void writeToNBT(NBTTagCompound par1) {
        super.writeToNBT(par1);
        par1.setBoolean("Enabled", AllowUpdate);
        par1.setIntArray("BoundBlockPos", OtherPos);
        par1.setIntArray("RGB", RGB);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1) {
        super.readFromNBT(par1);
        this.OtherPos = par1.getIntArray("BoundBlockPos");
        this.RGB = par1.getIntArray("RGB");
        this.AllowUpdate = par1.getBoolean("Enabled");
    }

    public void updateEntity(){
        if(worldObj != null && !worldObj.isRemote && AllowUpdate) Link.Link(new int[]{xCoord, yCoord, zCoord}, worldObj);
    }
}