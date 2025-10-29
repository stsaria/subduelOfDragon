package si.f5.stsaria.subduelOfDragon;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import si.f5.stsaria.subduelOfDragon.command.CopyCommand;
import si.f5.stsaria.subduelOfDragon.command.HomeCommand;
import si.f5.stsaria.subduelOfDragon.command.SetHomeCommand;
import si.f5.stsaria.subduelOfDragon.command.UpgradeCommand;
import si.f5.stsaria.subduelOfDragon.manager.EnderDragonStrangeManager;
import si.f5.stsaria.subduelOfDragon.manager.HomeManager;
import si.f5.stsaria.subduelOfDragon.manager.StandByDimensionManager;

import java.io.IOException;
import java.util.Objects;

public final class SubDuelOfDragon extends JavaPlugin implements Listener {
    private static SubDuelOfDragon instance = null;

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        try {
            Settings.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Objects.requireNonNull(this.getCommand("up")).setExecutor(new UpgradeCommand());
        Objects.requireNonNull(this.getCommand("home")).setExecutor(new HomeCommand());
        Objects.requireNonNull(this.getCommand("sethome")).setExecutor(new SetHomeCommand());
        Objects.requireNonNull(this.getCommand("copy")).setExecutor(new CopyCommand());
        StandByDimensionManager.init();
        EnderDragonStrangeManager.updateEnderDragonStrange();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        Settings.save();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        EnderDragonStrangeManager.updateEnderDragonStrange();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        HomeManager.cancelEvent(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        HomeManager.cancelEvent(e.getPlayer());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if (!e.getEntityType().equals(EntityType.PLAYER)) return;
        HomeManager.cancelEvent((Player) e.getEntity());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e){
        if (e.getCause().name().endsWith("_PORTAL")) return;
        StandByDimensionManager.standByOrGo(e);
    }

    @EventHandler
    public void onSpawnEntity(EntitySpawnEvent e){
        if (e.getEntityType().equals(EntityType.ENDER_DRAGON))
            EnderDragonStrangeManager.setEnderDragonStrange((EnderDragon) e.getEntity());
    }
}
