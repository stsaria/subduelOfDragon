package si.f5.stsaria.subduelOfDragon.model;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FoundEquipment {
    private final String playerName;
    private final Material material;
    public FoundEquipment(String playerName, Material material) {
        this.playerName = playerName;
        this.material = material;
    }
    public String getPlayerName() {
        return playerName;
    }
    public Material getMaterial() {
        return material;
    }
}
