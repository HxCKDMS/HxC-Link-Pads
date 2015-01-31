package HxCKDMS.HxCLinkPads.Proxy;

import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityRendererLinkPad;
import cpw.mods.fml.client.registry.ClientRegistry;
public class ClientProxy extends CommonProxy{
    @Override
    public void preInit() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLinkPad.class, new TileEntityRendererLinkPad());
        super.preInit();
    }
}
