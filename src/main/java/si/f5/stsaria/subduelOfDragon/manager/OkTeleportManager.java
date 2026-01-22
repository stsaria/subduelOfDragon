package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OkTeleportManager {
    private static final List<String> okPlayerNames = new ArrayList<>();
    private static Location teleportLocation = null;

    public synchronized static void setTeleportLocation(Location l) {
        teleportLocation = l;
        okPlayerNames.clear();
    }

    public synchronized static boolean ok(Player p) {
        if (teleportLocation == null) return false;
        else if (okPlayerNames.contains(p.getName())) return false;
        okPlayerNames.add(p.getName());
        p.teleport(teleportLocation);
        p.setRespawnLocation(teleportLocation, true);
        return true;
    }
}
