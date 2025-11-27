package si.f5.stsaria.subduelOfDragon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import si.f5.stsaria.subduelOfDragon.Saver;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.util.Messager;

import java.io.IOException;
import java.util.Map;

public class GetSavesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            Messager.sendMessage(sender, Saver.getAllString());
        } catch (IOException ignore) {}
        return true;
    }
}
