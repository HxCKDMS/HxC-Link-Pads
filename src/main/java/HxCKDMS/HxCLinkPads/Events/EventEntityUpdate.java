package HxCKDMS.HxCLinkPads.Events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent;

@SuppressWarnings("unused")
public class EventEntityUpdate {
    @SubscribeEvent
    public void PlayerTickEvent(LivingEvent.LivingUpdateEvent event){
        if (event.entityLiving instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;
            NBTTagCompound tag = player.getEntityData();
            int linkCooldown = tag.getInteger("LinkCooldown");
            if (linkCooldown > 0) {
                linkCooldown--;
                tag.setInteger("LinkCooldown", linkCooldown);
            }
        }
        //TODO:Move this to the core less methods is better
        //Having 2+ PlayerTickEvents is stupid just have it tick on core only thus no need to have it tick on core and on mod...
        //Say if I have a tick event in the core checking something else why have something ticking here as well when I can just
        //add this to the checks on the core side...
    }
}
