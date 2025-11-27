package si.f5.stsaria.subduelOfDragon;

import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import si.f5.stsaria.subduelOfDragon.manager.DimensionManager;
import si.f5.stsaria.subduelOfDragon.manager.HomesManager;
import si.f5.stsaria.subduelOfDragon.manager.UpgradeManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Saver {
    private static File f = null;
    private static YamlConfiguration config = null;
    public static <T> Map<String, List<T>> castToListMapFromObjectMap(Map<String, Object> map, Class<T> listType){
        Map<String, List<T>> newMap = new HashMap<>();
        map.forEach(
            (key, value) -> {
                newMap.put(key, new ArrayList<>());
                ((List<T>) value).forEach(i -> {
                    newMap.get(key).add(i);
                });
            }
        );
        return newMap;
    }
    public static synchronized void init() throws IOException {
        f = new File(SubDuelOfDragon.getInstance().getDataFolder(), "save.yml");
        config = YamlConfiguration.loadConfiguration(f);
        config.options().copyDefaults(true);

        config.addDefault("lockedWorlds", List.of(World.Environment.NETHER.name(), World.Environment.THE_END.name()));

        // Map<String(playerName): List<Integer(x), Integer(y), Integer(z)>
        config.addDefault("homes", new HashMap<>());

        // Map<String(equipType): List<String(equipName), String(foundPlayerName)>>
        config.addDefault("upgradedEquipments", new HashMap<>());

        config.save(f);

        DimensionManager.setLockedWorlds(config.getStringList("lockedWorlds"));

        ConfigurationSection homesSection = config.getConfigurationSection("homes");
        if (homesSection == null) homesSection = config.createSection("homes");
        HomesManager.setHomes(castToListMapFromObjectMap(homesSection.getValues(false), Integer.class));

        ConfigurationSection upgradedEquipmentsSection = config.getConfigurationSection("upgradedEquipments");
        if (upgradedEquipmentsSection == null) upgradedEquipmentsSection = config.createSection("homes");
        UpgradeManager.setEquips(castToListMapFromObjectMap(upgradedEquipmentsSection.getValues(false), String.class));
    }
    public static synchronized void save() {
        config.set("lockedWorlds", DimensionManager.getLockedWorlds());
        config.set("homes", HomesManager.getHomes());
        config.set("upgradedEquipments", UpgradeManager.getEquips());
        try {
            config.save(f);
        } catch (IOException ignored) {}
    }
    public static synchronized String getAllString() throws IOException {
        return FileUtils.readFileToString(f);
    }
}
