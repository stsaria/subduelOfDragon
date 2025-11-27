package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import si.f5.stsaria.subduelOfDragon.Settings;

import java.util.*;

public class DimensionManager {
    private static final Set<String> standByPlayerNames = new HashSet<>();

    private static final List<World.Environment> lockedWorlds = new ArrayList<>(List.of(World.Environment.NETHER, World.Environment.THE_END));
    private static synchronized void unlock(Location l){
        standByPlayerNames.forEach(pN -> {
            Player p = Bukkit.getPlayer(pN);
            if (p == null) return;
            p.sendMessage(Settings.get("titleUnlockedDimension"));
        });
        standByPlayerNames.clear();
        lockedWorlds.remove(Objects.requireNonNull(l.getWorld()).getEnvironment());
    }
    private static synchronized void sendMessages(String message){
        standByPlayerNames.forEach(pN -> {
            Player p = Bukkit.getPlayer(pN);
            if (p == null) return;
            p.sendMessage(message);
        });
    }
    public static synchronized void standByOrGo(PlayerTeleportEvent e) {
        if (lockedWorlds.isEmpty()) return;
        Location to = Objects.requireNonNull(e.getTo());
        if (!lockedWorlds.contains(Objects.requireNonNull(to.getWorld()).getEnvironment())) return;
        else if (!Objects.requireNonNull(e.getFrom().getWorld()).getEnvironment().equals(World.Environment.NORMAL)) return;
        to = to.clone();
        e.setCancelled(true);
        if (!Objects.requireNonNull(to.getWorld()).getEnvironment().equals(lockedWorlds.getFirst())) return;
        Player player = e.getPlayer();
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) reboundEndPortal(e.getFrom(), player);
        standByPlayerNames.add(player.getName());
        player.sendMessage(Settings.get("messageStartStandByDimension"));
        int needPlayers = Bukkit.getOnlinePlayers().size()*Settings.getInt("dimensionMoveStandByMinPlayersRatio");
        if (standByPlayerNames.size() >= Bukkit.getOnlinePlayers().size()*Settings.getInt("dimensionMoveStandByMinPlayersRatio")) {
            unlock(to);
            return;
        }
        sendMessages(
            Settings.get("messageAddedStandByDimensionPlayer")
            .replace("<playersLen>", String.valueOf(standByPlayerNames.size()))
            .replace("<needPlayersLen>", String.valueOf(needPlayers))
        );
    }
    private static void reboundEndPortal(Location from, Player p){
        from = from.clone();

        Vector look = p.getLocation().getDirection().clone();
        look.setY(0).normalize();

        Vector pushBack = look.multiply(-3.5);
        from.add(pushBack);
        from.setY(p.getLocation().getY() + 1);

        from.setYaw(p.getLocation().getYaw());
        from.setPitch(p.getLocation().getPitch());

        p.teleport(from, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
    public static synchronized void removeStandByPlayer(Player player) {
        standByPlayerNames.remove(player.getName());
    }
    public static synchronized ArrayList<String> getLockedWorlds() {
        ArrayList<String> worlds = new ArrayList<>();
        lockedWorlds.forEach(pN -> worlds.add(pN.name()));
        return worlds;
    }
    public static synchronized void setLockedWorlds(List<String> worlds) {
        lockedWorlds.clear();
        worlds.forEach(pN -> lockedWorlds.add(World.Environment.valueOf(pN)));
    }
}
