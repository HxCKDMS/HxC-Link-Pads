package HxCKDMS.HxCLinkPads.Items;

import HxCKDMS.HxCLinkPads.Lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemLinker extends Item{
	public NBTTagCompound data;

    public ItemLinker(){
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("itemLinker");
		setTextureName(Reference.MOD_ID + ":itemLinker");
		setMaxStackSize(1);
		setMaxDamage(0);
	}

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        data = getNBT(stack);
        int mode = 4;
        int[] pb = new int[4];
        pb[0] = 0; pb[1] = 0; pb[2] = 0; pb[3] = 0;
        data.setInteger("Mode", mode);
        data.setIntArray("PB", pb);
        stack.setTagCompound(data);
    }

    static NBTTagCompound getNBT(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        return stack.stackTagCompound;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltips, boolean boo) {
        super.addInformation(stack, player, tooltips, boo);
        try {
            int[] coords = stack.getTagCompound().getIntArray("PB");
            int mode = stack.getTagCompound().getInteger("Mode");
            tooltips.add("\u00A79X:" + coords[0]);
            tooltips.add("\u00A79Y:" + coords[1]);
            tooltips.add("\u00A79Z:" + coords[2]);
            tooltips.add("\u00A71Dim:" + coords[3]);
            if (mode == 1 || mode == 2) tooltips.add("\u00A73Mode: Read/Write");
            else tooltips.add("\u00A73Mode: Read Only");
        } catch (Exception e) {
            tooltips.add("\u00A74Nil");
        }
    }
}
