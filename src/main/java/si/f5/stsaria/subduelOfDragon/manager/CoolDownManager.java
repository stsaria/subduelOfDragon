package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.entity.Player;
import si.f5.stsaria.subduelOfDragon.CoolDownType;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.model.CoolDown;

import java.util.ArrayList;
import java.util.List;

public class CoolDownManager {
    private static final List<CoolDown> coolDowns = new ArrayList<>();
    public static long getEpoch(){
        return System.currentTimeMillis() / 1000L;
    }
    public static synchronized void gcCoolDowns() {
        coolDowns.removeIf(
             coolDown ->
                Settings.getInt(coolDown.getType().name().toLowerCase() + "CoolDown") - (getEpoch() - coolDown.getTimestamp()) <= 0
        );
    }
    private static long getRemainingTime(Player player, CoolDownType coolDownType) {
        for (CoolDown coolDown : coolDowns) {
            if (
                coolDown.getPlayer().equals(player) &&
                coolDownType.equals(coolDown.getType())
            ){
                return Settings.getInt(coolDownType.name().toLowerCase()+"CoolDown") - (getEpoch() - coolDown.getTimestamp());
            }
        }
        return 0;
    }
    public static synchronized long action(Player player, CoolDownType type) {
        long remainingTime = getRemainingTime(player, type);
        if (remainingTime <= 0) coolDowns.add(new CoolDown(type, getEpoch(), player));
        return remainingTime;
    }
}

