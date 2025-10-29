package si.f5.stsaria.subduelOfDragon.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.SubDuelOfDragon;
import si.f5.stsaria.subduelOfDragon.manager.HomeManager;
import si.f5.stsaria.subduelOfDragon.manager.StandByDimensionManager;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        Location home = HomeManager.getHomeLocation(player);
        if (home == null){
            player.sendMessage(Settings.get("messageCantTeleportHome"));
            return true;
        }
        HomeManager.startTeleportStandBy(player);
        player.sendMessage(Settings.get("messageStartedTeleport"));
        Bukkit.getScheduler().scheduleSyncDelayedTask(SubDuelOfDragon.getInstance(), () -> {
            float loopDelay = 0.05F;
            for (int i = 0; i < ((float) Settings.getInt("homeStandByDelay"))/loopDelay; i++) {
                if (!HomeManager.isStartedTeleportStandBy(player)) {
                    player.sendMessage(Settings.get("messageCanceledTeleportHome"));
                    return;
                }
            }
            player.teleport(home);
            StandByDimensionManager.removeStandByPlayer(player);
            HomeManager.stopTeleportStandBy(player);
            player.sendMessage(Settings.get("messageTeleportedHome"));
        });
        return true;
    }
}
