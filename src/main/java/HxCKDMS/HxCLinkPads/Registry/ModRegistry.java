package HxCKDMS.HxCLinkPads.Registry;

import HxCKDMS.HxCLinkPads.Blocks.BlockLinkPad;
import HxCKDMS.HxCLinkPads.Events.EventEntityUpdate;
import HxCKDMS.HxCLinkPads.TileEntities.TileEntityLinkPad;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRegistry {
    //blocks
    public static BlockLinkPad blockLinkpad = new BlockLinkPad(Material.iron);

    public static void preInit(){
        registerBlocks();
        registerTileEntities();
        registerCraftingRecipes();
    }
    public static void init(){
        MinecraftForge.EVENT_BUS.register(new EventEntityUpdate());
        //TODO:Move to core...
    }

    private static void registerBlocks() {
        GameRegistry.registerBlock(blockLinkpad = new BlockLinkPad(Material.iron), "blockLinkPad");
    }

    private static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityLinkPad.class, "TileEntityLinkPad");
    }

    private static void registerCraftingRecipes() {
        if(!Loader.isModLoaded("HxCWorldGen"))GameRegistry.addRecipe(new ItemStack(blockLinkpad, 2), "opo", "pep", "opo", 'e', Items.ender_eye, 'o', Blocks.obsidian, 'p', Items.ender_pearl);
        else GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockLinkpad, 2), "tgt", "pep", "tpt", 'e', Items.ender_eye, 't', "ingotTitanium", 'p', Items.ender_pearl, 'g', "gemAventurine"));
    }
}
