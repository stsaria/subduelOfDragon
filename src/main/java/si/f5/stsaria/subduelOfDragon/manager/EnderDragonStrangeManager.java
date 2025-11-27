package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.event.world.ChunkLoadEvent;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.SubDuelOfDragon;

import java.util.Objects;

import static si.f5.stsaria.subduelOfDragon.util.Worlder.getWorldByEnvironment;

public class EnderDragonStrangeManager {
    private static int largestEver = 0;
    private static final int baseDragonStrange = 200;
    public static synchronized void updateEnderDragonStrange() {
        int players = Bukkit.getOnlinePlayers().size();
        int impactPlayers = (int) (players * Settings.getDouble("enderDragonStrangeImpactRatio"));
        int strange = baseDragonStrange + impactPlayers * Settings.getInt("enderDragonStrangePerPerson");
        largestEver = Math.max(largestEver, strange);
        for (Chunk c : getWorldByEnvironment(World.Environment.THE_END).getLoadedChunks()){
            loadChunk(c);
        }
    }
    public static void setEnderDragonStrange(EnderDragon dragon) {
        double healthRatio = dragon.getHealth() / Objects.requireNonNull(dragon.getAttribute(Attribute.MAX_HEALTH)).getBaseValue();
        Objects.requireNonNull(dragon.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(largestEver);
        dragon.setHealth(largestEver*healthRatio);
    }

    public static void loadChunk(Chunk chunk){
        for (Entity e : chunk.getEntities()){
            if (e instanceof EnderDragon dragon) {
                setEnderDragonStrange(dragon);
            }
        }
    }
}
