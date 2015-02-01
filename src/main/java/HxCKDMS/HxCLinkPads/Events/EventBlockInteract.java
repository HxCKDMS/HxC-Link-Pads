package HxCKDMS.HxCLinkPads.Events;

import HxCKDMS.HxCLinkPads.Items.ItemLinker;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.EventListener;

@SuppressWarnings("unused")
public class EventBlockInteract implements EventListener {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.world.getTileEntity(event.x, event.y, event.z) instanceof TileEntityLinkPad){
            TileEntityLinkPad LPad = (TileEntityLinkPad)event.world.getTileEntity(event.x, event.y, event.z);
            if (event.entityPlayer.getHeldItem() != null) {
                Item item = event.entityPlayer.getHeldItem().getItem();
                ItemStack stack = event.entityPlayer.getHeldItem();
                World world = event.entityPlayer.worldObj;
                int metadata = event.entityPlayer.getHeldItem().getItemDamageForDisplay();
                if (item instanceof ItemDye) {
                    try {
                        if(metadata == 1) {
                            if(event.entityPlayer.isSneaking()){
                                if(LPad.RGB[0] != 0)
                                    LPad.RGB[0] -= 5;
                            }else{
                                if(LPad.RGB[0] != 255)
                                    LPad.RGB[0] += 5;
                            }
                        }else if(metadata == 2) {
                            if(event.entityPlayer.isSneaking()){
                                if(LPad.RGB[1] != 0)
                                    LPad.RGB[1] -= 5;
                            }else{
                                if(LPad.RGB[1] != 255)
                                    LPad.RGB[1] += 5;
                            }
                        }else if(metadata == 4){
                            if(event.entityPlayer.isSneaking()){
                                if(LPad.RGB[2] != 0)
                                    LPad.RGB[2] -= 5;
                            }else{
                                if(LPad.RGB[2] != 255)
                                    LPad.RGB[2] += 5;
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else if (item instanceof ItemLinker) {
                    NBTTagCompound dat = stack.getTagCompound();
                    int[] pb = dat.getIntArray("PB");
                    int[] pb2 = new int[4];
                    int mode = dat.getInteger("Mode");
                    pb2[0] = event.x; pb2[1] = event.y; pb2[2] = event.z; pb2[3] = world.provider.dimensionId;
                    if (mode == 1){
                        mode = 2;
                        LPad.NewPos = pb;
                        world.markBlockForUpdate(event.x, event.y, event.z);
                    } else if (mode == 2){
                        mode = 0;
                        LPad.NewPos = pb;
                        world.markBlockForUpdate(event.x, event.y, event.z);
                    } else{
                        mode = 1;
                    }
                    dat.setIntArray("PB", pb2);
                    dat.setInteger("Mode", mode);
                    stack.setTagCompound(dat);
                }
            }
        }
    }
}
