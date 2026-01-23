package si.f5.stsaria.subduelOfDragon;

import org.apache.commons.io.FileUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import si.f5.stsaria.subduelOfDragon.manager.DimensionManager;
import si.f5.stsaria.subduelOfDragon.manager.HomesManager;
import si.f5.stsaria.subduelOfDragon.manager.OkTeleportManager;
import si.f5.stsaria.subduelOfDragon.manager.UpgradeManager;
import si.f5.stsaria.subduelOfDragon.util.WorldSearcher;
import si.f5.stsaria.subduelOfDragon.util.Worlder;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Saver {
    private static File f = null;
    private static YamlConfiguration config = null;
    public static <T> Map<String, List<T>> castToListMapFromObjectMap(Map<String, Object> map, Class<T> listType){
        Map<String, List<T>> newMap = new HashMap<>();
        map.forEach(
            (key, value) -> {
                newMap.put(key, new ArrayList<>());
                ((List<T>) value).forEach(i -> newMap.get(key).add(i));
            }
        );
        return newMap;
    }
    public static synchronized void init() throws IOException {
        f = new File(SubDuelOfDragon.getInstance().getDataFolder(), "save.yml");
        config = YamlConfiguration.loadConfiguration(f);
        config.options().copyDefaults(true);


        // List<String>
        config.addDefault("lockedWorlds", List.of(World.Environment.NETHER.name(), World.Environment.THE_END.name()));

        // Map<String(playerName): List<Integer(x), Integer(y), Integer(z)>
        config.addDefault("homes", new HashMap<>());

        // Map<String(equipType): List<String(equipName), String(foundPlayerName)>>
        config.addDefault("upgradedEquipments", new HashMap<>());

        // List<Integer(x), Integer(z)>
        config.addDefault("respawnPoint",  List.of(0, 0));

        config.save(f);

        List<Integer> point = config.getIntegerList("respawnPoint");
        DimensionManager.setLockedWorlds(config.getStringList("lockedWorlds"));
        OkTeleportManager.setTeleportLocation(
                WorldSearcher.getSafeGroundByAroundXAndZ(
                Worlder.getWorldByEnvironment(World.Environment.NORMAL),
                point.get(0), point.get(1)
            )
        );

        ConfigurationSection selection;

        selection = config.getConfigurationSection("homes");
        if (selection == null) selection = config.createSection("homes");
        HomesManager.setHomes(castToListMapFromObjectMap(selection.getValues(false), Integer.class));

        selection = config.getConfigurationSection("upgradedEquipments");
        if (selection == null) selection = config.createSection("upgradedEquipments");
        UpgradeManager.setEquips(castToListMapFromObjectMap(selection.getValues(false), String.class));

    }
    public static synchronized void save() {
        config.set("lockedWorlds", DimensionManager.getLockedWorlds());
        config.set("homes", HomesManager.getHomes());
        config.set("upgradedEquipments", UpgradeManager.getEquips());
        Location l = OkTeleportManager.getTeleportLocation();
        config.set("respawnPoint", List.of(l.getBlockX(), l.getBlockZ()));
        try {
            config.save(f);
        } catch (IOException ignored) {}
    }
    public static synchronized String getAllString() throws IOException {
        return FileUtils.readFileToString(f);
    }
}
