package si.f5.stsaria.subduelOfDragon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import si.f5.stsaria.subduelOfDragon.command.*;
import si.f5.stsaria.subduelOfDragon.manager.*;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public final class SubDuelOfDragon extends JavaPlugin implements Listener {
    private static SubDuelOfDragon instance = null;

    public static SubDuelOfDragon getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        try {
            Settings.init();
            Saver.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        save();

        Objects.requireNonNull(this.getCommand("up")).setExecutor(new UpgradeCommand());
        Objects.requireNonNull(this.getCommand("home")).setExecutor(new HomeCommand());
        Objects.requireNonNull(this.getCommand("sethome")).setExecutor(new SetHomeCommand());
        Objects.requireNonNull(this.getCommand("copy")).setExecutor(new CopyCommand());
        Objects.requireNonNull(this.getCommand("setcon")).setExecutor(new SetConfigureCommand());
        Objects.requireNonNull(this.getCommand("getcons")).setExecutor(new GetConfiguresCommand());
        Objects.requireNonNull(this.getCommand("getsaves")).setExecutor(new GetSavesCommand());
        Objects.requireNonNull(this.getCommand("savedragon")).setExecutor(new SaveCommand());
        Objects.requireNonNull(this.getCommand("ok")).setExecutor(new OkCommand());
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getScheduler().runTaskLater(this, EnderDragonStrangeManager::updateEnderDragonStrange, 6*20);

        new BukkitRunnable() {
            @Override
            public void run() {
                CoolDownManager.gcCoolDowns();  
            }
        }.runTaskTimer(this, 20L, 20L);
    }

    public void save(){
        this.getLogger().log(Level.INFO, "Saving dragon...");
        Settings.save();
        Saver.save();
    }

    @Override
    public void onDisable() {
        this.save();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        HelpScoreBoard.show(e.getPlayer());
        EnderDragonStrangeManager.updateEnderDragonStrange();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Location f, t;
        f = e.getFrom();
        t = Objects.requireNonNull(e.getTo());
        if (f.getX() == t.getX() && f.getY() == t.getY() && f.getZ() == t.getZ()) return;
        HomesManager.cancelEvent(e.getPlayer());
        if (f.getBlockX() != t.getBlockX() || f.getBlockY() != t.getBlockY() || f.getBlockZ() != t.getBlockZ()){
            CoordViewer.view(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        HomesManager.cancelEvent(e.getPlayer());
        ShortSleeper.bedOut(e.getPlayer());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if (!e.getEntityType().equals(EntityType.PLAYER)) return;
        HomesManager.cancelEvent((Player) e.getEntity());
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent e) {
        DimensionManager.standByOrGo(e);
    }

    @EventHandler
    public void onSpawnEntity(EntitySpawnEvent e){
        if (e.getEntityType().equals(EntityType.ENDER_DRAGON))
            EnderDragonStrangeManager.setEnderDragonStrange((EnderDragon) e.getEntity());
    }

    @EventHandler
    public void onLoadChunk(ChunkLoadEvent e){
        if (!e.getChunk().getWorld().getEnvironment().equals(World.Environment.THE_END)) return;
        EnderDragonStrangeManager.loadChunk(e.getChunk());
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        UpgradeManager.found(Objects.requireNonNull(e.getCurrentItem()).getType(), player);
    }

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        this.save();
    }

    @EventHandler
    public void onPlayerEnterBed(PlayerBedEnterEvent e){
        ShortSleeper.bedIn(e);
    }
}
