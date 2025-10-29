package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import si.f5.stsaria.subduelOfDragon.Settings;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static si.f5.stsaria.subduelOfDragon.util.Worlder.getWorldByEnvironment;

public class StandByDimensionManager {
    private static World standByWorld = null;
    private static final Set<String> standByPlayerNames = new HashSet<>();
    private static World nextUnlockWorld = null;
    private static boolean end = false;
    public static synchronized void init() {
        WorldCreator wC = new WorldCreator(Settings.get("standByDimensionName"));
        wC.environment(World.Environment.CUSTOM);
        wC.type(WorldType.FLAT);
        wC.generateStructures(false);
        standByWorld = Objects.requireNonNull(Bukkit.createWorld(wC));
        standByWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);

        nextUnlockWorld = getWorldByEnvironment(World.Environment.NETHER);
    }
    private static synchronized void unlock(){
        standByPlayerNames.forEach(pN -> {
            Player p = Bukkit.getPlayer(pN);
            if (p == null) return;
            p.teleport(nextUnlockWorld.getSpawnLocation());
        });
        standByPlayerNames.clear();
        if (nextUnlockWorld.getEnvironment().equals(World.Environment.THE_END)) {
            end = true;
            return;
        }
        nextUnlockWorld = getWorldByEnvironment(World.Environment.THE_END);
    }
    public static synchronized void standByOrGo(PlayerTeleportEvent e) {
        if (end){
            return;
        }
        e.setCancelled(true);
        Player player = e.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        Location loc = new Location(standByWorld, Settings.getDouble("standByDimensionSpawnX"),  Settings.getInt("standByDimensionSpawnY"), Settings.getInt("standByDimensionSpawnZ"));
        player.teleport(loc);
        standByPlayerNames.add(player.getName());
        if (standByPlayerNames.size() >= Bukkit.getOnlinePlayers().size()*Settings.getInt("dimensionMoveStandByMinPlayersRatio")) {
            unlock();
        }
    }
    public static synchronized void removeStandByPlayer(Player player) {
        standByPlayerNames.remove(player.getName());
    }
}
