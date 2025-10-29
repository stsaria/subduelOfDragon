package si.f5.stsaria.subduelOfDragon.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Worlder {
    public static World getWorldByEnvironment(World.Environment env) {
        return Bukkit.getWorlds().stream()
            .filter(w -> w.getEnvironment().equals(env))
            .findFirst()
            .orElse(null);
    }
}
