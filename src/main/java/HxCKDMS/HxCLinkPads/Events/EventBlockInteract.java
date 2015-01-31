package HxCKDMS.HxCLinkPads.Events;

import HxCKDMS.HxCLinkPads.HxCLinkPads;
import HxCKDMS.HxCLinkPads.TileEntities.PortalTileEnt;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.EventListener;

public class EventBlockInteract implements EventListener {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.world.getBlock(event.x, event.y, event.z) == HxCLinkPads.blockLinkpad){
            if (event.entityPlayer.getHeldItem() != null) {
                Item item = event.entityPlayer.getHeldItem().getItem();
                int metadata = event.entityPlayer.getHeldItem().getItemDamageForDisplay();
                int i = item.getMetadata(item.getDamage(new ItemStack(item)));
                if (item instanceof ItemDye) {
                    try {
                        PortalTileEnt tileEntity = (PortalTileEnt)event.world.getTileEntity(event.x, event.y, event.z);

                        if(metadata == 1) {
                            if(event.entityPlayer.isSneaking()){
                                if(tileEntity.RGB[0] != 0)
                                    tileEntity.RGB[0] -= 5;
                            }else{
                                if(tileEntity.RGB[0] != 255)
                                    tileEntity.RGB[0] += 5;
                            }
                        }else if(metadata == 2) {
                            if(event.entityPlayer.isSneaking()){
                                if(tileEntity.RGB[1] != 0)
                                    tileEntity.RGB[1] -= 5;
                            }else{
                                if(tileEntity.RGB[1] != 255)
                                    tileEntity.RGB[1] += 5;
                            }
                        }else if(metadata == 4){
                            if(event.entityPlayer.isSneaking()){
                                if(tileEntity.RGB[2] != 0)
                                    tileEntity.RGB[2] -= 5;
                            }else{
                                if(tileEntity.RGB[2] != 255)
                                    tileEntity.RGB[2] += 5;
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
