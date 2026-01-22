package si.f5.stsaria.subduelOfDragon.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.manager.OkTeleportManager;
import si.f5.stsaria.subduelOfDragon.util.Messager;

import java.util.Objects;

public class SetOkTpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        Location teleportLocation = player.getLocation();
        teleportLocation.setY(Objects.requireNonNull(teleportLocation.getWorld()).getHighestBlockYAt(teleportLocation)+1);
        OkTeleportManager.setTeleportLocation(teleportLocation);
        Messager.sendMessageForAllPlayer(Settings.get("messageNewOkTeleportByAdmin"));
        Messager.sendMessage(player, "set oktp");
        return true;
    }
}
