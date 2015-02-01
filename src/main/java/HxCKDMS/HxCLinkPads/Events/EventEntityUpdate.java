package HxCKDMS.HxCLinkPads.Events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.nbt.NBTTagCompound;
@SuppressWarnings("unused")
public class EventEntityUpdate {
    @SubscribeEvent
    public void PlayerTickEvent(TickEvent.PlayerTickEvent event){
        NBTTagCompound tag = event.player.getEntityData();
        int linkCooldown = tag.getInteger("LinkCooldown");
        if (linkCooldown != -0) {
            linkCooldown--;
            tag.setInteger("LinkCooldown", linkCooldown);
        }
    }
}
