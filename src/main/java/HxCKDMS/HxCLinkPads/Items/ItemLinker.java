package HxCKDMS.HxCLinkPads.Items;

import HxCKDMS.HxCLinkPads.Reference.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemLinker extends Item{
	public NBTTagCompound data;

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltips, boolean boo) {
        super.addInformation(stack, player, tooltips, boo);
        try {
            int[] coords = stack.getTagCompound().getIntArray("PB");
            int mode = stack.getTagCompound().getInteger("Mode");
            tooltips.add("X:" + coords[0]);
            tooltips.add("Y:" + coords[1]);
            tooltips.add("Z:" + coords[2]);
            tooltips.add("Dim:" + coords[3]);
            if (mode == 1 || mode == 2) tooltips.add("Mode: Read/Write");
            else tooltips.add("Mode: Read Only");
        } catch (Exception e){
            tooltips.add("\u00A74ERROR");
        }
    }

    public ItemLinker(){
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("itemLinker");
		setTextureName(References.MOD_ID + ":itemLinker");
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
}
