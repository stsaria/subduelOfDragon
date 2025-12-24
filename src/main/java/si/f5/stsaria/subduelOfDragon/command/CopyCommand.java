package si.f5.stsaria.subduelOfDragon.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import si.f5.stsaria.subduelOfDragon.CoolDownType;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.manager.CoolDownManager;
import si.f5.stsaria.subduelOfDragon.util.Messager;

public class CopyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        PlayerInventory inventory = player.getInventory();
        ItemStack item = inventory.getItemInMainHand();
        if (item.getType().equals(Material.AIR)) {
            Messager.sendMessage(player, Settings.get("messageCantCopyAir"));
            return true;
        }
        long remainingTime = CoolDownManager.action(player, CoolDownType.COPY);
        if (remainingTime > 0) {
            Messager.sendMessage(
                player,
                Settings.get("messageCantCopyCoolDown")
                .replace("<cooldown>", String.valueOf(remainingTime))
            );
            return true;
        }
        inventory.addItem(item);
        Messager.sendMessage(player, Settings.get("messageCopied"));
        return true;
    }
}
