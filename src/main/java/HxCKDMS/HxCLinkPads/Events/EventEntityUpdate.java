package HxCKDMS.HxCLinkPads.Events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent;

@SuppressWarnings("unused")
public class EventEntityUpdate {
    @SubscribeEvent
    public void PlayerTickEvent(LivingEvent.LivingUpdateEvent event){
        if (event.entityLiving instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.entityLiving;
            NBTTagCompound tag = player.getEntityData();
            int linkCooldown = tag.getInteger("LinkCooldown");
            if (linkCooldown != -0) {
                linkCooldown--;
                tag.setInteger("LinkCooldown", linkCooldown);
            }
        }
    }
}
