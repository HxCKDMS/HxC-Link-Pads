package HxCKDMS.HxCLinkPads;

import HxCKDMS.HxCCore.api.Configuration.Config;

@Config
public class Configurations {
    public static short Delay = 120;
    public static boolean RequireSneaking, PlayersOnly = true;
    @Config.comment(value = "Valid options, Default/NBT, WorldMap, PlayerMap")
    public static String LinkType = "Default";
}
