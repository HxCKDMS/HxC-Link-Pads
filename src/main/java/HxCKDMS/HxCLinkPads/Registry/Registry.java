package HxCKDMS.HxCLinkPads.Registry;

import HxCKDMS.HxCLinkPads.Blocks.BlockLinkPad;
import HxCKDMS.HxCLinkPads.Items.ItemLinker;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Registry {
    //blocks
    public static BlockLinkPad blockLinkpad = new BlockLinkPad(Material.iron);

    //items
    public static ItemLinker itemLinker = new ItemLinker();

    public static void preInit(){
        registerBlocks();
        registerItems();
        registerTileEntities();
        registerCraftingRecipes();
    }

    private static void registerBlocks() {
        GameRegistry.registerBlock(blockLinkpad = new BlockLinkPad(Material.iron), "blockLinkPad");
    }

    private static void registerItems() {
        GameRegistry.registerItem(itemLinker, "itemLinker");
    }

    private static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityLinkPad.class, "TileEntityLinkPad");
    }

    private static void registerCraftingRecipes() {
        GameRegistry.addShapedRecipe(new ItemStack(itemLinker), "g g", "dgd", " g ", 'g', Items.gold_ingot, 'd', Items.diamond);

        GameRegistry.addRecipe(new ItemStack(blockLinkpad,2), "eee", "eoe", "eee", 'e', Items.ender_eye, 'o', Blocks.obsidian);
    }
}
