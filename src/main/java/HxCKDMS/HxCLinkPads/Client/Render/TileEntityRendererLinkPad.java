package HxCKDMS.HxCLinkPads.Client.Render;

import HxCKDMS.HxCLinkPads.Client.Models.ModelLinkPad;
import HxCKDMS.HxCLinkPads.Reference;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererLinkPad extends TileEntitySpecialRenderer {

    private final ResourceLocation texturePortalBase = new ResourceLocation(Reference.MOD_ID + ":textures/models/LinkPadBase.png");
    private final ResourceLocation texturePortalBase2 = new ResourceLocation(Reference.MOD_ID + ":textures/models/LinkPadBase_grey.png");
    private final ResourceLocation texturePortal_top = new ResourceLocation(Reference.MOD_ID + ":textures/models/LinkPad_top.png");
    
    private final ModelLinkPad modelLinkPad = new ModelLinkPad();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float alpha) {
        TileEntityLinkPad portalTileEntity = (TileEntityLinkPad)tileEntity;

        GL11.glPushMatrix();
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glTranslated(x + 0.5F, y + 0.0F, z + 0.5F);

        if (portalTileEntity.getType() != 0) {
            bindTexture(texturePortalBase2);
            GL11.glColor3f(29f / 255f, 97f / 255f, 114f / 255f);
            switch (portalTileEntity.getType()) {
                case 1: GL11.glColor3f(29f / 255f, 97f / 255f, 114f / 255f); break;
                case 2: GL11.glColor3f(137f / 255f, 137f / 255f, 140f / 255f); break;
                case 3: GL11.glColor3f(110f / 255f, 35f / 255f, 35f / 255f); break;
                case 4: GL11.glColor3f(29f / 255f, 97f / 255f, 35f / 255f); break;
            }
        } else bindTexture(texturePortalBase);
        modelLinkPad.renderPart("mainPad");

        bindTexture(texturePortal_top);
        GL11.glColor3f(portalTileEntity.getRGB()[0] / 255F, portalTileEntity.getRGB()[1] / 255F, portalTileEntity.getRGB()[2] / 255F);
        modelLinkPad.renderPart("colorPad");

        GL11.glPopMatrix();
    }
}
