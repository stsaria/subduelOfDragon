package si.f5.stsaria.subduelOfDragon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import si.f5.stsaria.subduelOfDragon.CoolDownType;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.manager.CoolDownManager;
import si.f5.stsaria.subduelOfDragon.manager.UpgradeManager;
import si.f5.stsaria.subduelOfDragon.util.Messager;

public class UpgradeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (!CoolDownManager.action(player, CoolDownType.UPGRADE)) {
            player.sendMessage(Settings.get("messageCantUpgrade"));
            return true;
        }
        Inventory inventory = player.getInventory();
        ItemStack upgraded, root;
        ItemMeta upgradedMeta, rootMeta;
        String upgradedName, rootName;
        StringBuilder upgradedLog = new StringBuilder();
        for (int i = 0; i < inventory.getSize(); i++) {
            root = inventory.getItem(i);
            if (root == null) continue;
            upgraded = UpgradeManager.upgrade(root.getType());
            if (upgraded != null) {
                inventory.setItem(i, upgraded);

                upgradedMeta = upgraded.getItemMeta();
                rootMeta = root.getItemMeta();
                if (upgradedMeta == null) continue;
                else if (rootMeta == null) continue;
                rootName = rootMeta.hasItemName() ? rootMeta.getItemName() : root.getType().name();
                upgradedName = upgradedMeta.getDisplayName();
                upgradedLog.append(rootName).append(" -> ").append(upgradedName).append("\n");
            }
        }
        Messager.sendMessage(player, Settings.get("messageUpgraded") + "\n" + upgradedLog);
        return true;
    }
}
