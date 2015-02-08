package HxCKDMS.HxCLinkPads.Registry;

import HxCKDMS.HxCLinkPads.Blocks.BlockLinkPad;
import HxCKDMS.HxCLinkPads.Events.EventBlockInteract;
import HxCKDMS.HxCLinkPads.Events.EventEntityUpdate;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModRegistry {
    //blocks
    public static BlockLinkPad blockLinkpad = new BlockLinkPad(Material.iron);

    public static void preInit(){
        registerBlocks();
        registerTileEntities();
        registerCraftingRecipes();
    }
    public static void init(){
        MinecraftForge.EVENT_BUS.register(new EventBlockInteract());
        MinecraftForge.EVENT_BUS.register(new EventEntityUpdate());
    }

    private static void registerBlocks() {
        GameRegistry.registerBlock(blockLinkpad = new BlockLinkPad(Material.iron), "blockLinkPad");
    }

    private static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityLinkPad.class, "TileEntityLinkPad");
    }

    private static void registerCraftingRecipes() {
        GameRegistry.addRecipe(new ItemStack(blockLinkpad, 2), "opo", "pep", "opo", 'e', Items.ender_eye, 'o', Blocks.obsidian, 'p', Items.ender_pearl);
    }
}
