package HxCKDMS.HxCLinkPads.Client.Models;

import HxCKDMS.HxCLinkPads.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

@SideOnly(Side.CLIENT)
public class ModelLinkPad {
    private IModelCustom modelLinkPad;

    public ModelLinkPad() {
        modelLinkPad = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MOD_ID + ":textures/models/LinkPad.obj"));
    }
    
    public void render() {
        modelLinkPad.renderAll();
    }
    
    public void renderPart(String part) {
        modelLinkPad.renderPart(part);
    }
}
