package si.f5.stsaria.subduelOfDragon.command;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.manager.HomeManager;
import si.f5.stsaria.subduelOfDragon.util.Messager;

import java.util.Objects;

import static si.f5.stsaria.subduelOfDragon.util.Worlder.getWorldByEnvironment;

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        Location home = player.getLocation();
        if (!Objects.equals(home.getWorld(), getWorldByEnvironment(World.Environment.NORMAL))){
            Messager.sendMessage(player, Settings.get("messageCantSetHome"));
            return true;
        }
        HomeManager.setHomeLocation(player, home);
        Messager.sendMessage(player, Settings.get("messageSetHome"));
        return true;
    }
}
