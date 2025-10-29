package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderDragon;
import si.f5.stsaria.subduelOfDragon.Settings;

import java.util.Objects;

public class EnderDragonStrangeManager {
    private static int largestEver = 0;
    private static final int baseDragonStrange = 200;
    public static synchronized void updateEnderDragonStrange() {
        int players = Bukkit.getOnlinePlayers().size();
        int impactPlayers = (int) (players * Settings.getDouble("enderDragonStrangeImpactRatio"));
        int strange = baseDragonStrange + impactPlayers * Settings.getInt("enderDragonStrangePerPerson");
        largestEver = Math.max(largestEver, strange);
    }
    public static synchronized void setEnderDragonStrange(EnderDragon dragon) {
        Objects.requireNonNull(dragon.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(largestEver);
        dragon.setHealth(largestEver);
    }
}
