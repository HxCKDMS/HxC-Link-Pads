package HxCKDMS.HxCLinkPads.Blocks;

import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

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
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        TileEntityLinkPad lpad = (TileEntityLinkPad)world.getTileEntity(x, y, z);
        lpad.AllowUpdate = false;
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
}
