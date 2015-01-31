package HxCKDMS.HxCLinkPads;

import HxCKDMS.HxCLinkPads.Blocks.BlockPortal;
import HxCKDMS.HxCLinkPads.Events.EventBlockInteract;
import HxCKDMS.HxCLinkPads.Events.Events;
import HxCKDMS.HxCLinkPads.Items.ItemLinker;
import HxCKDMS.HxCLinkPads.Proxy.ClientProxy;
import HxCKDMS.HxCLinkPads.Proxy.ServerProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "HxCLinkPads", name = "HxCLinkPads", version = "1.0")
public class HxCLinkPads {

	public static Block blockLinkpad;
	public static Item itemLinker = new ItemLinker();
    public static String MODID = "HxCLinkPads";

    @Mod.Instance("portals")
    public static HxCLinkPads HxCLinkPads;

    @SidedProxy(serverSide = "HxCKDMS.HxCLinkPads.Proxy.ServerProxy", clientSide = "HxCKDMS.HxCLinkPads.Proxy.ClientProxy")
    public static ClientProxy cProxy;
    public static ServerProxy sProxy;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		//Block & Item Registry
		GameRegistry.registerBlock(blockLinkpad = new BlockPortal("blockLinkpad", Material.iron), "blockLinkpad");
		GameRegistry.registerItem(itemLinker, "itemLinker");

		//Crafting Registry
		GameRegistry.addShapedRecipe(new ItemStack(itemLinker), "g g", "dgd", " g ", 'g', Items.gold_ingot, 'd', Items.diamond);
		
		GameRegistry.addRecipe(new ItemStack(blockLinkpad,2), "eee", "eoe", "eee", 'e', Items.ender_eye, 'o', Blocks.obsidian);

        cProxy.preInit();
        MinecraftForge.EVENT_BUS.register(new EventBlockInteract());
	}
	@EventHandler
	public void init(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new Events());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}

}
