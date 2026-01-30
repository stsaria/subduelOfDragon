package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.util.Messager;
import si.f5.stsaria.subduelOfDragon.util.WorldSearcher;

import java.util.*;

public class DimensionManager {
    private static final Set<String> standByPlayerNames = new HashSet<>();

    private static final List<World.Environment> lockedWorlds = new ArrayList<>(List.of(World.Environment.NETHER, World.Environment.THE_END));
    private static void unlock(Location gateLocation, Location toLocation){
        standByPlayerNames.clear();
        lockedWorlds.remove(Objects.requireNonNull(toLocation.getWorld()).getEnvironment());

        Location teleportLocation = Objects.requireNonNull(
            WorldSearcher.getSafeGroundByAroundXAndZ(Objects.requireNonNull(gateLocation.getWorld()), gateLocation.getBlockX(), gateLocation.getBlockZ())
        );

        OkTeleportManager.setTeleportLocation(teleportLocation);
        Bukkit.getOnlinePlayers().forEach(p -> Messager.sendMessage(
            p,
            Settings.get("messageUnlockedDimension")
            .replace("<x>", String.valueOf(teleportLocation.getBlockX()))
            .replace("<z>", String.valueOf(teleportLocation.getBlockZ()))
        ));
    }
    public static synchronized void standByOrGo(PlayerTeleportEvent e) {
        if (lockedWorlds.isEmpty()) return;
        Location to = Objects.requireNonNull(e.getTo());
        if (!lockedWorlds.contains(Objects.requireNonNull(to.getWorld()).getEnvironment())) return;
        else if (!Objects.requireNonNull(e.getFrom().getWorld()).getEnvironment().equals(World.Environment.NORMAL)) return;

        Location from = e.getFrom().clone();
        to = to.clone();
        e.setCancelled(true);

        if (!Objects.requireNonNull(to.getWorld()).getEnvironment().equals(lockedWorlds.getFirst())) return;
        Player player = e.getPlayer();
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) reboundEndPortal(e.getFrom(), player);

        if (standByPlayerNames.contains(player.getName())) return;
        standByPlayerNames.add(player.getName());
        int needPlayers = (int) (Bukkit.getOnlinePlayers().size()*Settings.getDouble("dimensionMoveStandByMinPlayersRatio"));
        if (standByPlayerNames.size() >= needPlayers) {
            unlock(from, to);
            return;
        }
        Messager.sendMessageForAllPlayer(
            Settings.get("messageAddedStandByDimensionPlayer")
            .replace("<player>", player.getName())
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
