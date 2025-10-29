package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeManager {
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
}
