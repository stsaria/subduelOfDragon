package si.f5.stsaria.subduelOfDragon.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messager {
    public static void sendMessage(Player player, String message) {
        for (String msg : message.split("\n")) player.sendMessage(msg);
    }
    public static void sendMessage(CommandSender sender, String message) {
        for (String msg : message.split("\n")) sender.sendMessage(msg);
    }
}
