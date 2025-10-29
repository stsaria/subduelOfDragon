package si.f5.stsaria.subduelOfDragon.model;

import org.bukkit.entity.Player;
import si.f5.stsaria.subduelOfDragon.CoolDownType;

public class CoolDown {
    private final CoolDownType type;
    private final long expireTime;
    private final Player player;
    public CoolDown(CoolDownType type, long expireTime, Player player) {
        this.type = type;
        this.expireTime = expireTime;
        this.player = player;
    }

    public CoolDownType getType() {
        return type;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public Player getPlayer() {
        return player;
    }
}
