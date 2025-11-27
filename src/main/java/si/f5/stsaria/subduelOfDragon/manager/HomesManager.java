package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import si.f5.stsaria.subduelOfDragon.util.Worlder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomesManager {
    private static final Map<String, Location> playerNameAndHomeLocation = new HashMap<>();
    private static final List<String> teleportStandByPlayerNames = new ArrayList<>();
    public static synchronized Location getHomeLocation(Player player) {
        return playerNameAndHomeLocation.get(player.getName());
    }
    public static synchronized void setHomeLocation(Player player, Location location) {
        playerNameAndHomeLocation.put(player.getName(), location);
    }
    public static synchronized void startTeleportStandBy(Player player) {
        teleportStandByPlayerNames.add(player.getName());
    }
    public static synchronized boolean isStartedTeleportStandBy(Player player) {
        return teleportStandByPlayerNames.contains(player.getName());
    }
    public static synchronized void stopTeleportStandBy(Player player) {
        teleportStandByPlayerNames.remove(player.getName());
    }
    public static synchronized void cancelEvent(Player player) {
        if (!isStartedTeleportStandBy(player)) return;
        stopTeleportStandBy(player);
    }
    public static synchronized Map<String, List<Integer>> getHomes(){
        Map<String, List<Integer>> homes = new HashMap<>();
        playerNameAndHomeLocation.forEach((pN, location) -> homes.put(pN, List.of(location.getBlockX(), location.getBlockY(), location.getBlockZ())));
        return homes;
    }
    public static synchronized void setHomes(Map<String, List<Integer>> homes){
        World world = Worlder.getWorldByEnvironment(World.Environment.NORMAL);
        homes.forEach((pN, locations) -> {
            if (locations.size() != 3) return;
            playerNameAndHomeLocation.put(pN, new Location(world, locations.get(0), locations.get(1), locations.get(2)));
        });
    }

}
