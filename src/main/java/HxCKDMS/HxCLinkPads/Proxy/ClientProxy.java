package HxCKDMS.HxCLinkPads.Proxy;

import HxCKDMS.HxCLinkPads.Client.Render.ItemRendererLinkPad;
import HxCKDMS.HxCLinkPads.Client.Render.TileEntityRendererLinkPad;
import HxCKDMS.HxCLinkPads.Registry.Registry;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy{
    @Override
    public void preInit() {
        TileEntitySpecialRenderer renderer = new TileEntityRendererLinkPad();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLinkPad.class, renderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Registry.blockLinkpad), new ItemRendererLinkPad(renderer, new TileEntityLinkPad()));
        super.preInit();
    }
}
