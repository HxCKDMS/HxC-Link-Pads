package HxCKDMS.HxCLinkPads.Items;

import HxCKDMS.HxCLinkPads.Reference.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemLinker extends Item{
	public NBTTagCompound data;
	public ItemLinker(){
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("itemLinker");
		setTextureName(References.MOD_ID + ":itemLinker");
		setMaxStackSize(1);
		setMaxDamage(0);
	}

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        int[] pb = new int[5];
        data = getNBT(stack);
        pb[4] = 0;
        data.setIntArray("PB", pb);
    }

    static NBTTagCompound getNBT(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        return stack.stackTagCompound;
    }
}
