package HxCKDMS.HxCLinkPads.Blocks;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.io.File;
import java.util.List;

public class BlockLinkPad extends BlockContainer {

    public BlockLinkPad(Material material) {
		super(material);
		setCreativeTab(CreativeTabs.tabTransport);
		setBlockName("blockLinkPad");
		setStepSound(soundTypeMetal);
		setHardness(1.0F);
		setResistance(1600.0F);
        setLightLevel(1);
        setLightOpacity(0);
        setBlockBounds(0F, 0F, 0F, 1F, 0.25F, 1F);
	}

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityLinkPad();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entLiv, ItemStack stack) {
        if (entLiv instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entLiv;
            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
            TileEntityLinkPad LPad = (TileEntityLinkPad) world.getTileEntity(x, y, z);
            int[] CurrentBlock = new int[]{x, y, z, world.provider.dimensionId};
            int mode;
            int[] PreviousBlock;
            TileEntityLinkPad LPad2;
            try {
                mode = NBTFileIO.getInteger(CustomPlayerData, "PadMode");
                PreviousBlock = NBTFileIO.getIntArray(CustomPlayerData, "PrevBlock");
            } catch (Exception ignored) {
                mode = 0;
                PreviousBlock = new int[]{x, y, z, world.provider.dimensionId};
            }
            if (mode == 0) {
                NBTFileIO.setIntArray(CustomPlayerData, "PrevBlock", CurrentBlock);
                NBTFileIO.setInteger(CustomPlayerData, "PadMode", 1);
            } else if (mode == 1) {
                LPad.OtherPos = PreviousBlock;
                try {LPad2 = (TileEntityLinkPad) world.getTileEntity(PreviousBlock[0], PreviousBlock[1], PreviousBlock[2]);}
                catch (Exception ignored) {LPad2 = null;}
                if (LPad2 != null) LPad2.OtherPos = CurrentBlock;
                NBTFileIO.setInteger(CustomPlayerData, "PadMode", 0);
            }
        }
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
        super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (entityPlayer.getHeldItem() != null && world.getTileEntity(x, y, z) != null) {
            ItemStack stack = entityPlayer.getHeldItem();
            Item item = stack.getItem();
            int metadata = entityPlayer.getHeldItem().getItemDamageForDisplay();
            TileEntityLinkPad LPad = (TileEntityLinkPad)world.getTileEntity(x, y, z);
            if (item instanceof ItemDye) {
                if (metadata == 1) {
                    if (entityPlayer.isSneaking()) {
                        if (LPad.RGB[0] != 0)
                            LPad.RGB[0] -= 5;
                    } else {
                        if (LPad.RGB[0] != 255)
                            LPad.RGB[0] += 5;
                    }
                } else if (metadata == 2) {
                    if (entityPlayer.isSneaking()) {
                        if (LPad.RGB[1] != 0)
                            LPad.RGB[1] -= 5;
                    } else {
                        if (LPad.RGB[1] != 255)
                            LPad.RGB[1] += 5;
                    }
                } else if (metadata == 4) {
                    if (entityPlayer.isSneaking()) {
                        if (LPad.RGB[2] != 0)
                            LPad.RGB[2] -= 5;
                    } else {
                        if (LPad.RGB[2] != 255)
                            LPad.RGB[2] += 5;
                    }
                }
            }
        }
        return false;
    }
}
