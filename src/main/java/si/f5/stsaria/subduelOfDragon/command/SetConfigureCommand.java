package si.f5.stsaria.subduelOfDragon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import si.f5.stsaria.subduelOfDragon.Settings;

import java.util.Arrays;

public class SetConfigureCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) return false;
        String key = args[0];
        String value = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        Settings.set(key, value);
        sender.sendMessage("set");
        return true;
    }
}
