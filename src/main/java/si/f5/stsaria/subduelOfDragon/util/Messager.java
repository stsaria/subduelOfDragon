package si.f5.stsaria.subduelOfDragon.util;

import org.bukkit.entity.Player;

public class Messager {
    public static void sendMessage(Player player, String message) {
        for (String msg : message.split("\n")) player.sendMessage(msg);
    }
}
