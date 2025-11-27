package si.f5.stsaria.subduelOfDragon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import si.f5.stsaria.subduelOfDragon.Saver;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.SubDuelOfDragon;
import si.f5.stsaria.subduelOfDragon.util.Messager;

import java.io.IOException;

public class SaveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        SubDuelOfDragon.getInstance().save();
        sender.sendMessage("saved");
        return true;
    }
}
