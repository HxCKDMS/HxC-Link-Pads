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
                    int[] cb; int mode;
                    int[] pb = new int[]{event.x, event.y, event.z, world.provider.dimensionId};
                    try {
                        cb = dat.getIntArray("PB");
                        mode = dat.getInteger("Mode");
                    } catch (Exception ignored) {
                        cb = new int[]{0,0,0,0};
                        mode = 0;
                    }
                    if (mode == 1){
                        mode = 2;
                        LPad.OtherPos = cb;
                        LPad.AllowUpdate = true;
                    } else if (mode == 2){
                        mode = 0;
                        LPad.OtherPos = cb;
                        LPad.AllowUpdate = true;
                    } else {
                        mode = 1;
                    }
                    dat.setIntArray("PB", pb);
                    dat.setInteger("Mode", mode);
                    stack.setTagCompound(dat);
                }
            }
        }
    }
}
