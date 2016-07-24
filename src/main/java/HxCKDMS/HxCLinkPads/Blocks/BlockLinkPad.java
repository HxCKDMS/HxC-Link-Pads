package HxCKDMS.HxCLinkPads.Blocks;

import HxCKDMS.HxCCore.Configs.Configurations;
import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.api.Utils.LogHelper;
import HxCKDMS.HxCLinkPads.Reference;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
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
        if (entLiv instanceof EntityPlayer && !world.isRemote) {
            EntityPlayer player = (EntityPlayer)entLiv;
            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
            TileEntityLinkPad LPad = (TileEntityLinkPad) world.getTileEntity(x, y, z), LPad2;
            int[] CurrentBlock = new int[]{x, y, z, world.provider.dimensionId},
                    PreviousBlock = NBTFileIO.getIntArray(CustomPlayerData, "PrevBlock");
            LPad.setOwner(player.getDisplayName());
            boolean mode = NBTFileIO.getBoolean(CustomPlayerData, "SettingPad");

            if (!mode) {
                NBTFileIO.setIntArray(CustomPlayerData, "PrevBlock", CurrentBlock);
                NBTFileIO.setBoolean(CustomPlayerData, "SettingPad", true);
            } else {
                LPad.bind(PreviousBlock);
                try {
                    LPad2 = (TileEntityLinkPad) HxCCore.server.worldServerForDimension(PreviousBlock[3]).getTileEntity(PreviousBlock[0], PreviousBlock[1], PreviousBlock[2]);
                    LPad2.bind(CurrentBlock);
                } catch (Exception e) {
                    if (Configurations.DebugMode) e.printStackTrace();
                    LogHelper.debug(e.getLocalizedMessage(), Reference.MOD_NAME);
                }
                NBTFileIO.setBoolean(CustomPlayerData, "SettingPad", false);
                player.addChatMessage(new ChatComponentText("\u00a73Bound pad @ X: " + CurrentBlock[0] + " Y: " + CurrentBlock[1] + " Z: " + CurrentBlock[2] + " Dim: " + CurrentBlock[3] + " to X: " +
                PreviousBlock[0] + " Y: " + PreviousBlock[1] + " Z: " + PreviousBlock[2] + " Dim: " + PreviousBlock[3]));
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
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        if (player.getHeldItem() != null && world.getTileEntity(x, y, z) != null) {
            ItemStack stack = player.getHeldItem();
            Item item = stack.getItem();
            int metadata = player.getHeldItem().getItemDamage();
            TileEntityLinkPad LPad = (TileEntityLinkPad) world.getTileEntity(x, y, z);
            if (item instanceof ItemDye) {
                if (metadata == 1) LPad.rgb((byte) 0, (byte) -5);
                else if (metadata == 2) LPad.rgb((byte) 1, (byte) -5);
                else if (metadata == 4) LPad.rgb((byte) 2, (byte) -5);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (player.getHeldItem() != null && world.getTileEntity(x, y, z) != null) {
            ItemStack stack = player.getHeldItem();
            Item item = stack.getItem();
            int metadata = player.getHeldItem().getItemDamage();
            TileEntityLinkPad LPad = (TileEntityLinkPad)world.getTileEntity(x, y, z);
            if (item instanceof ItemDye) {
                if (metadata == 1) return LPad.rgb((byte) 0, (byte) 5);
                else if (metadata == 2) return LPad.rgb((byte) 1, (byte) 5);
                else if (metadata == 4) return LPad.rgb((byte) 2, (byte) 5);
            } else if (item == Items.egg) {
                LPad.setType((byte) 2);
            } else if (item == Items.apple) {
                LPad.setType((byte) 1);
            } else if (item == Items.arrow) {
                LPad.setType((byte) 3);
            } else if (item == Items.stick) {
                LPad.setType((byte) 4);
            } else if (item == Items.clay_ball) {
                LPad.setType((byte) 0);
            }
        } else if (!world.isRemote && player.getHeldItem() == null && world.getTileEntity(x, y, z) != null) {
            TileEntityLinkPad LPad = (TileEntityLinkPad)world.getTileEntity(x, y, z);
            player.addChatMessage(new ChatComponentText("\u00a7bLinked to :" + LPad.getReadableBoundLocation()));
        }
        return false;
    }
}
