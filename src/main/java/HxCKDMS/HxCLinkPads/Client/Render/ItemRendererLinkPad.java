package HxCKDMS.HxCLinkPads.Client.Render;

import HxCKDMS.HxCLinkPads.Reference.References;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererLinkPad implements IItemRenderer {
    private final ResourceLocation texturePortal = new ResourceLocation(References.MOD_ID + ":textures/models/blockLinkpad.png");
    private final ResourceLocation texturePortal_top_outer = new ResourceLocation(References.MOD_ID + ":textures/models/blockLinkpad_outer_top.png");
    private final ResourceLocation texturePortal_top_inner = new ResourceLocation(References.MOD_ID + ":textures/models/blockLinkpad_inner_top.png");

    private TileEntity tileEntity;
    private TileEntitySpecialRenderer renderer;

    public ItemRendererLinkPad(TileEntitySpecialRenderer renderer, TileEntity tileEntity) {
        this.renderer = renderer;
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
        this.renderer.renderTileEntityAt(this.tileEntity, 0.0D, 0.0D, 0.0D, 0.0F);
    }
}
