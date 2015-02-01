package HxCKDMS.HxCLinkPads.Blocks;

import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
	}

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityLinkPad();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        TileEntityLinkPad lpad = (TileEntityLinkPad)world.getTileEntity(x, y, z);
        lpad.x = x; lpad.y = y; lpad.z = z; lpad.AllowUpdate = false;
        world.markBlockForUpdate(x, y, z);
    }

    @Override
    public int getRenderType() {
        return -1;
    }
}
