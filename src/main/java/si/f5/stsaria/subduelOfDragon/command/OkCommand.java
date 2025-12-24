package si.f5.stsaria.subduelOfDragon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.manager.OkTeleportManager;
import si.f5.stsaria.subduelOfDragon.util.Messager;

public class OkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (OkTeleportManager.ok(player)) Messager.sendMessage(player, Settings.get("messageOkTeleported"));
        else Messager.sendMessage(player, Settings.get("messageYouDontHaveTellMeOk"));
        return true;
    }
}
