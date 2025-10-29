package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.entity.Player;
import si.f5.stsaria.subduelOfDragon.CoolDownType;
import si.f5.stsaria.subduelOfDragon.model.CoolDown;

import java.util.ArrayList;
import java.util.List;

public class CoolDownManager {
    private static final List<CoolDown> coolDowns = new ArrayList<>();
    public static long getEpoch(){
        return System.currentTimeMillis() / 1000L;
    }
    public static synchronized boolean canAction(Player player, CoolDownType coolDownType) {
        for (CoolDown coolDown : coolDowns) {
            if (
                coolDown.getPlayer().equals(player) &&
                (coolDown.getExpireTime() < getEpoch() ||
                coolDownType.equals(coolDown.getType()))
            ) return false;
        }
        return true;
    }
    public static synchronized boolean action(Player player, CoolDownType type) {
        if (!canAction(player, type)){
            return false;
        }
        coolDowns.add(new CoolDown(type, getEpoch(), player));
        return true;
    }
}

