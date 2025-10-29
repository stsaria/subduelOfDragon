package si.f5.stsaria.subduelOfDragon.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import si.f5.stsaria.subduelOfDragon.Settings;
import si.f5.stsaria.subduelOfDragon.model.FoundEquipment;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

class FoundEquipments {
    public static FoundEquipment pickaxe = null;
    public static FoundEquipment axe = null;
    public static FoundEquipment shovel = null;
    public static FoundEquipment sword = null;
    public static FoundEquipment hoe = null;
    public static FoundEquipment helmet = null;
    public static FoundEquipment chestplate = null;
    public static FoundEquipment leggings = null;
    public static FoundEquipment boots = null;

    static void set(String key, FoundEquipment value) {
        try {
            Field field = FoundEquipments.class.getField(key);
            field.set(FoundEquipments.class, value);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid key: " + key, e);
        }
    }

    static FoundEquipment get(String key) {
        try {
            Field field = FoundEquipments.class.getField(key);
            return (FoundEquipment) field.get(FoundEquipments.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid key: " + key, e);
        }
    }
}

public class UpgradeManager {
    private static final List<String> ranks = List.of("wooden", "stone", "iron", "diamond", "netherite");

    private static String getRankByName(String name) {
        for (String s : ranks) {
            if (name.startsWith(s+"_")) {
                return s;
            }
        }
        return null;
    }

    public static ItemStack upgrade(Material material) {
        String materialName = material.name().toLowerCase();
        String rank = getRankByName(materialName);
        if (rank == null) return null;
        String equipType = materialName.replace(rank +"_", "");

        FoundEquipment nowEquip = FoundEquipments.get(equipType);
        if (nowEquip == null) return null;
        String nowEquipRank = getRankByName(nowEquip.getMaterial().name().toLowerCase());
        if (ranks.indexOf(nowEquipRank) > ranks.indexOf(rank)) {
            int upgradedMaterialIndex = ranks.indexOf(nowEquipRank)-1;
            if (upgradedMaterialIndex < 0) return null;
            Material upgradedMaterial = Material.getMaterial((ranks.get(upgradedMaterialIndex)+"_"+equipType).toUpperCase());
            if (upgradedMaterial == null) return null;
            ItemStack upgraded = new ItemStack(upgradedMaterial);
            ItemMeta meta = Objects.requireNonNull(upgraded.getItemMeta());
            meta.setDisplayName(
                Settings.get("upgradedItemDiscovererSig")
                .replace("<name>", nowEquip.getPlayer().getName())
                .replace("<item>", meta.getDisplayName())
            );
            upgraded.setItemMeta(meta);
            return upgraded;
        }
        return null;
    }

    public static void found(Material material, Player player) {
        String materialName = material.name().toLowerCase();
        String rank = getRankByName(materialName);
        if (rank == null) return;
        String equipType = materialName.replace(rank +"_", "");

        FoundEquipment nowEquip = FoundEquipments.get(equipType);
        if (nowEquip == null) return;
        String nowEquipRank = getRankByName(nowEquip.getMaterial().name().toLowerCase());
        if (ranks.indexOf(nowEquipRank) >= ranks.indexOf(rank)) return;
        FoundEquipment equip = new FoundEquipment(player, material);
        FoundEquipments.set(equipType, equip);
    }
}
