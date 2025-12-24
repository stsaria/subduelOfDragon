package si.f5.stsaria.subduelOfDragon;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.scheduler.BukkitRunnable;
import si.f5.stsaria.subduelOfDragon.util.Messager;

import java.util.HashSet;
import java.util.Set;

public class ShortSleeper {
    private static final Set<String> bedInPlayerNames = new HashSet<>();
    public static synchronized void bedIn(PlayerBedEnterEvent e) {
        int players = Bukkit.getOnlinePlayers().size();
        int minPlayersRatio = Settings.getInt("sleepMinPlayersRatio");

        bedInPlayerNames.add(e.getPlayer().getName());
        if (players >= minPlayersRatio){
            BossBar bossBar = Bukkit.createBossBar(
                Settings.get("bossBarMessageGoForSleep"),
                BarColor.RED,
                BarStyle.SOLID
            );
            bossBar.setProgress(0);
            Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);
            int sleepSec = Settings.getInt("sleepSec");
            long allTick = 20L*sleepSec;
            new BukkitRunnable() {
                private int ticks = 0;
                @Override
                public void run() {
                    ticks++;
                    bossBar.setProgress((double) ticks/allTick);
                    if (ticks == allTick) {
                        e.getPlayer().getWorld().setTime(1000L);
                        bossBar.removeAll();
                        cancel();
                    }
                }
            }.runTaskTimer(SubDuelOfDragon.getInstance(), 1L, 1L);
        } else {
            Messager.sendMessageForAllPlayer(
                Settings.get("messageSomeoneInBed")
                .replace("<player>", e.getPlayer().getName())
                .replace("<inBedPlayers>", String.valueOf(bedInPlayerNames.size()))
                .replace("<minInBedPlayers>", String.valueOf(minPlayersRatio*players))
            );
        }
    }
    public static synchronized void bedOut(Player p) {
        bedInPlayerNames.remove(p.getName());
    }
}
