package HxCKDMS.HxCLinkPads;

import HxCKDMS.HxCLinkPads.Events.EventBlockInteract;
import HxCKDMS.HxCLinkPads.Events.Events;
import HxCKDMS.HxCLinkPads.Proxy.ClientProxy;
import HxCKDMS.HxCLinkPads.Proxy.ServerProxy;
import HxCKDMS.HxCLinkPads.Reference.References;
import HxCKDMS.HxCLinkPads.Registry.Registry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.VERSION)
public class HxCLinkPads {
    @Mod.Instance(References.MOD_ID)
    public static HxCLinkPads HxCLinkPads;

    @SidedProxy(serverSide = References.SERVER_PROXY_LOCATION, clientSide = References.CLIENT_PROXY_LOCATION)
    public static ClientProxy cProxy;
    public static ServerProxy sProxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
        cProxy.preInit();
        Registry.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new Events());
        MinecraftForge.EVENT_BUS.register(new EventBlockInteract());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
}