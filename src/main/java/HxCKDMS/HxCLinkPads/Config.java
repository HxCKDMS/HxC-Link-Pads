package HxCKDMS.HxCLinkPads;

import net.minecraftforge.common.config.Configuration;

public class Config
{
    public static int Delay;
    public static boolean sneaking, enablewip;
    public Config(Configuration config) {
        config.load();

        sneaking = config.getBoolean("RequireSneaking", "Features", false, "Enable to force players to be sneaking to tp");
        enablewip = config.getBoolean("EnableWIP", "Features", false, "Enable things that need lots of testing");
        Delay = config.getInt("MaxRange", "Features", 100, 30, 10000, "Delay between teleports");

        if(config.hasChanged()) config.save();
    }
}
