package HxCKDMS.HxCLinkPads.Client.Render;

import HxCKDMS.HxCLinkPads.Reference.References;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererLinkPad extends TileEntitySpecialRenderer {

    private final ResourceLocation texturePortal = new ResourceLocation(References.MOD_ID + ":textures/models/blockLinkpad.png");
    private final ResourceLocation texturePortal_top_outer = new ResourceLocation(References.MOD_ID + ":textures/models/blockLinkpad_outer_top.png");
    private final ResourceLocation texturePortal_top_inner = new ResourceLocation(References.MOD_ID + ":textures/models/blockLinkpad_inner_top.png");

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float alpha) {
        TileEntityLinkPad portalTileEntity = (TileEntityLinkPad)tileEntity;

        GL11.glPushMatrix();
        {
            GL11.glTranslated(x, y, z);
            Tessellator tessellator = Tessellator.instance;

            //sides
            this.bindTexture(texturePortal);
            tessellator.startDrawingQuads();
            {
                tessellator.addVertexWithUV(0, 0, 1, 1, 1);
                tessellator.addVertexWithUV(0, 1, 1, 1, 0);
                tessellator.addVertexWithUV(0, 1, 0, 0, 0);
                tessellator.addVertexWithUV(0, 0, 0, 0, 1);

                tessellator.addVertexWithUV(1, 1, 1, 0, 1);
                tessellator.addVertexWithUV(1, 0, 1, 0, 0);
                tessellator.addVertexWithUV(1, 0, 0, 1, 0);
                tessellator.addVertexWithUV(1, 1, 0, 1, 1);

                tessellator.addVertexWithUV(0, 0, 0, 0, 1);
                tessellator.addVertexWithUV(0, 1, 0, 0, 0);
                tessellator.addVertexWithUV(1, 1, 0, 1, 0);
                tessellator.addVertexWithUV(1, 0, 0, 1, 1);

                tessellator.addVertexWithUV(0, 1, 1, 0, 1);
                tessellator.addVertexWithUV(0, 0, 1, 0, 0);
                tessellator.addVertexWithUV(1, 0, 1, 1, 0);
                tessellator.addVertexWithUV(1, 1, 1, 1, 1);

                tessellator.addVertexWithUV(0, 0, 1, 0, 1);
                tessellator.addVertexWithUV(0, 0, 0, 0, 0);
                tessellator.addVertexWithUV(1, 0, 0, 1, 0);
                tessellator.addVertexWithUV(1, 0, 1, 1, 1);
            }
            tessellator.draw();

            //outer top
            this.bindTexture(texturePortal_top_outer);
            tessellator.startDrawingQuads();
            {
                tessellator.addVertexWithUV(1, 1, 0, 0, 1);
                tessellator.addVertexWithUV(0, 1, 0, 0, 0);
                tessellator.addVertexWithUV(0, 1, 1, 1, 0);
                tessellator.addVertexWithUV(1, 1, 1, 1, 1);
            }
            tessellator.draw();

            //inner top
            this.bindTexture(texturePortal_top_inner);
            tessellator.startDrawingQuads();
            {
                tessellator.setColorOpaque(portalTileEntity.RGB[0], portalTileEntity.RGB[1], portalTileEntity.RGB[2]);

                tessellator.addVertexWithUV(1 - 0.03125 * 2, 1, 0.03125 * 2, 0, 1);
                tessellator.addVertexWithUV(0.03125 * 2, 1, 0.03125 * 2, 0, 0);
                tessellator.addVertexWithUV(0.03125 * 2, 1, 1 - 0.03125 * 2, 1, 0);
                tessellator.addVertexWithUV(1 - 0.03125 * 2, 1, 1 - 0.03125 * 2, 1, 1);
            }
            tessellator.draw();
        }
        GL11.glPopMatrix();
    }
}
