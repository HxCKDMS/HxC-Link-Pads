package HxCKDMS.HxCLinkPads.Proxy;

import cpw.mods.fml.relauncher.Side;

@SuppressWarnings("unused")
public abstract class CommonProxy {
    public void preInit(){}

    public Side getSide(){
        return this instanceof  ClientProxy ? Side.CLIENT : Side.SERVER;
    }
}
