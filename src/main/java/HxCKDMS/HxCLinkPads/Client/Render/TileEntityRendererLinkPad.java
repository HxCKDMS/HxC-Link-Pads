package HxCKDMS.HxCLinkPads.Client.Render;

import HxCKDMS.HxCLinkPads.Client.Models.ModelLinkPad;
import HxCKDMS.HxCLinkPads.Lib.Reference;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererLinkPad extends TileEntitySpecialRenderer {

    private final ResourceLocation texturePortal = new ResourceLocation(Reference.MOD_ID + ":textures/models/LinkPadBase.png");
    private final ResourceLocation texturePortal_top = new ResourceLocation(Reference.MOD_ID + ":textures/models/LinkPad_top.png");
    
    private final ModelLinkPad modelLinkPad = new ModelLinkPad();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float alpha) {
        TileEntityLinkPad portalTileEntity = (TileEntityLinkPad)tileEntity;

        GL11.glPushMatrix();
        {
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslated(x + 0.5F, y + 0.0F, z + 0.5F);

            this.bindTexture(texturePortal);

            modelLinkPad.renderPart("mainPad");

            this.bindTexture(texturePortal_top);
            GL11.glColor3f(portalTileEntity.RGB[0] / 255F, portalTileEntity.RGB[1] / 255F, portalTileEntity.RGB[2] / 255F);
            modelLinkPad.renderPart("colorPad");
        }
        GL11.glPopMatrix();
    }
}
