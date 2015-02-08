package HxCKDMS.HxCLinkPads.Events;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCLinkPads.Blocks.BlockLinkPad;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.io.File;
import java.util.EventListener;

@SuppressWarnings("unused")
public class EventBlockInteract implements EventListener {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.world.getTileEntity(event.x, event.y, event.z) instanceof TileEntityLinkPad){
            if (event.entityPlayer.getHeldItem() != null) {
                Item item = event.entityPlayer.getHeldItem().getItem();
                ItemStack stack = event.entityPlayer.getHeldItem();
                World world = event.entityPlayer.worldObj;
                int metadata = event.entityPlayer.getHeldItem().getItemDamageForDisplay();
                TileEntityLinkPad LPad = (TileEntityLinkPad)event.world.getTileEntity(event.x, event.y, event.z);
                if (item instanceof ItemDye) {
                    try {
                        if (metadata == 1) {
                            if (event.entityPlayer.isSneaking()) {
                                if (LPad.RGB[0] != 0)
                                    LPad.RGB[0] -= 5;
                            } else {
                                if (LPad.RGB[0] != 255)
                                    LPad.RGB[0] += 5;
                            }
                        } else if (metadata == 2) {
                            if (event.entityPlayer.isSneaking()) {
                                if (LPad.RGB[1] != 0)
                                    LPad.RGB[1] -= 5;
                            } else {
                                if (LPad.RGB[1] != 255)
                                    LPad.RGB[1] += 5;
                            }
                        } else if (metadata == 4) {
                            if (event.entityPlayer.isSneaking()) {
                                if (LPad.RGB[2] != 0)
                                    LPad.RGB[2] -= 5;
                            } else {
                                if (LPad.RGB[2] != 255)
                                    LPad.RGB[2] += 5;
                            }
                        }
                    } catch (Exception ignored) {

                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void blockPlaceEvent(BlockEvent.PlaceEvent event) {
        Block block = event.block;
        if (block instanceof BlockLinkPad){
            EntityPlayer player = event.player;
            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
            TileEntityLinkPad LPad = (TileEntityLinkPad)event.world.getTileEntity(event.x, event.y, event.z);
            int[] CurrentBlock = new int[]{event.x, event.y, event.z, event.world.provider.dimensionId};
            int mode; int[] PreviousBlock; TileEntityLinkPad LPad2;
            try {
                mode = NBTFileIO.getInteger(CustomPlayerData, "PadMode");
                PreviousBlock = NBTFileIO.getIntArray(CustomPlayerData, "PrevBlock");
            } catch (Exception ignored) {
                mode = 0;
                PreviousBlock = new int[]{event.x, event.y, event.z, event.world.provider.dimensionId};
            }
            if (mode == 0){
                NBTFileIO.setIntArray(CustomPlayerData, "PrevBlock", CurrentBlock);
                NBTFileIO.setInteger(CustomPlayerData, "PadMode", 1);
            } else if (mode == 1) {
                LPad.OtherPos = PreviousBlock;
                LPad.AllowUpdate = true;
                try {
                    LPad2 = (TileEntityLinkPad)event.world.getTileEntity(PreviousBlock[0], PreviousBlock[1], PreviousBlock[2]);
                } catch (Exception ignored) {
                    LPad2 = null;
                }
                if (LPad2 != null) {
                    LPad2.OtherPos = CurrentBlock;
                    LPad2.AllowUpdate = true;
                }
                NBTFileIO.setInteger(CustomPlayerData, "PadMode", 0);
            }
        }
    }
}
