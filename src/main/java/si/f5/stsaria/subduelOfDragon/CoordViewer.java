package si.f5.stsaria.subduelOfDragon;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.entity.Player;
import si.f5.stsaria.subduelOfDragon.util.Messager;


public class CoordViewer {
    public static void view(Player p) {
        Messager.sendMessage(p, ChatMessageType.ACTION_BAR,
            Settings.get("actionBarCoord")
            .replace("<x>", String.valueOf(p.getLocation().getBlockX()))
            .replace("<y>", String.valueOf(p.getLocation().getBlockY()))
            .replace("<z>", String.valueOf(p.getLocation().getBlockZ()))
        );
    }
}
