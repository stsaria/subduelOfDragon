package si.f5.stsaria.subduelOfDragon.model;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FoundEquipment {
    private final Player player;
    private final Material material;
    public FoundEquipment(Player player, Material material) {
        this.player = player;
        this.material = material;
    }
    public Player getPlayer() {
        return player;
    }
    public Material getMaterial() {
        return material;
    }
}
