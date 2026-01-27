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
import si.f5.stsaria.subduelOfDragon.manager.DimensionManager;
import si.f5.stsaria.subduelOfDragon.manager.HomesManager;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        Location home = HomesManager.getHomeLocation(player);
        if (home == null){
            player.sendMessage(Settings.get("messageCantTeleportHome"));
            return true;
        }
        HomesManager.startTeleportStandBy(player);
        Integer delay = Settings.getInt("homeStandByDelay");
        player.sendMessage(
            Settings.get("messageStartedTeleport")
            .replace("<cooldown>", String.valueOf(delay))
        );
        new Thread(() -> {
            float loopDelay = 0.1F;
            for (int i = 0; i < ((float) delay)/loopDelay; i++) {
                if (!HomesManager.isStartedTeleportStandBy(player)) {
                    player.sendMessage(Settings.get("messageCanceledTeleportHome"));
                    return;
                }
                try {
                    Thread.sleep((long) (1000*loopDelay));
                } catch (InterruptedException e) {return;}
            }
            Bukkit.getScheduler().runTask(SubDuelOfDragon.getInstance(), () -> {
                player.teleport(home);
                DimensionManager.removeStandByPlayer(player);
                HomesManager.stopTeleportStandBy(player);
                player.sendMessage(Settings.get("messageTeleportedHome"));
            });
        }).start();
        return true;
    }
}
