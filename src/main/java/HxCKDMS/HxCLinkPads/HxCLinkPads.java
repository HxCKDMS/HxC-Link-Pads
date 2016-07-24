package HxCKDMS.HxCLinkPads;

import HxCKDMS.HxCLinkPads.Configurations;
import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Configuration.HxCConfig;
import HxCKDMS.HxCLinkPads.Proxy.CommonProxy;
import HxCKDMS.HxCLinkPads.Registry.ModRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings("unused")
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class HxCLinkPads {
    @Mod.Instance(Reference.MOD_ID)
    public static HxCLinkPads HxCLinkPads;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_LOCATION, clientSide = Reference.CLIENT_PROXY_LOCATION)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        HxCConfig hxCConfig = new HxCConfig(Configurations.class, "HxCLinkPads", HxCCore.HxCConfigDir, "cfg");
        hxCConfig.initConfiguration();
        ModRegistry.preInit();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){}

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){}
}
