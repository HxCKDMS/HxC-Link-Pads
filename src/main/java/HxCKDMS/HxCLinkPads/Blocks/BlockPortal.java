package HxCKDMS.HxCLinkPads.Blocks;

import HxCKDMS.HxCLinkPads.TileEntities.PortalTileEnt;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPortal extends BlockContainer {
	
	public BlockPortal(String unlocalizedName, Material material) {
		super(material);
		setCreativeTab(CreativeTabs.tabTransport);
		setBlockName(unlocalizedName);
		setStepSound(soundTypeMetal);
		setLightLevel(0.4F);
		setHardness(2.0F);
		setResistance(20.0F);
	}

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new PortalTileEnt();
    }

    @Override
    public int getRenderType() {
        return -1;
    }
}
