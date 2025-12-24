package si.f5.stsaria.subduelOfDragon.model;

import org.bukkit.entity.Player;
import si.f5.stsaria.subduelOfDragon.CoolDownType;

public class CoolDown {
    private final CoolDownType type;
    private final long timestamp;
    private final Player player;
    public CoolDown(CoolDownType type, long timestamp, Player player) {
        this.type = type;
        this.timestamp = timestamp;
        this.player = player;
    }

    public CoolDownType getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Player getPlayer() {
        return player;
    }
}
