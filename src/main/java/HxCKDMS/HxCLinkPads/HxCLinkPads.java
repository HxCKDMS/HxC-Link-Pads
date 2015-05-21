package HxCKDMS.HxCLinkPads;

import HxCKDMS.HxCCore.Utils.LogHelper;
import HxCKDMS.HxCLinkPads.Proxy.CommonProxy;
import HxCKDMS.HxCLinkPads.Registry.ModRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@SuppressWarnings("unused")
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class HxCLinkPads {
    @Mod.Instance(Reference.MOD_ID)
    public static HxCLinkPads HxCLinkPads;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_LOCATION, clientSide = Reference.CLIENT_PROXY_LOCATION)
    public static CommonProxy proxy;

    public static Config Config;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Config = new Config(new Configuration(event.getSuggestedConfigurationFile()));
        ModRegistry.preInit();
        proxy.preInit();

        LogHelper.info("Pre initialization has been completed.", Reference.MOD_NAME);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        ModRegistry.init();
        LogHelper.info("Initialization has been completed.", Reference.MOD_NAME);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        LogHelper.info("Post initialization has been completed.", Reference.MOD_NAME);
    }
}
