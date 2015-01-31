package HxCKDMS.HxCLinkPads.Proxy;

import HxCKDMS.HxCLinkPads.TileEntities.PortalTileEnt;
import HxCKDMS.HxCLinkPads.TileEntities.PortalTileEntRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
public class ClientProxy extends CommonProxy{
    @Override
    public void preInit() {
        ClientRegistry.bindTileEntitySpecialRenderer(PortalTileEnt.class, new PortalTileEntRenderer());
        super.preInit();
    }
}
