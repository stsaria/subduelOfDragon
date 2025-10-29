package si.f5.stsaria.subduelOfDragon;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

public class Settings {
    private static FileConfiguration config = null;

    public static synchronized void init() throws IOException {
        config = SubDuelOfDragon.getInstance().getConfig();

        config.addDefault("upgradeCoolDown", 10*60);
        config.addDefault("copyCooldown", 15*60);
        config.addDefault("enderDragonStrangePerPerson", 75);
        config.addDefault("enderDragonStrangeImpactRatio", 1/4);
        config.addDefault("homeStandByDelay", 5);
        config.addDefault("dimensionMoveStandByMinPlayersRatio", 1/4);
        config.addDefault("standByDimensionName", "standby");
        config.addDefault("standByDimensionSpawnX", 0);
        config.addDefault("standByDimensionSpawnY", 5);
        config.addDefault("standByDimensionSpawnZ", 0);
        config.addDefault("upgradedItemDiscovererSig", "<name>'s <item>");
        config.addDefault("messageCantUpgrade", "アップグレードできません。最後のアップグレードから"+get("upgradeCooldown")+"秒待ってください。");
        config.addDefault("messageUpgraded", "アップグレードされました！");
        config.addDefault("messageCantCopy", ChatColor.RED+"コピーできません。最後のコピーから"+get("copyCooldown")+"秒待ってください。");
        config.addDefault("messageSetHome", "設定しました！");
        config.addDefault("messageCantSetHome", ChatColor.RED+"設定できません。初期ワールドにのみ設定できます。");
        config.addDefault("messageStartedTeleport", "テレポートまで"+config.get("homeStandByDelay")+"秒待ってください。\n途中でダメージ・移動するとキャンセルされます。");
        config.addDefault("messageTeleportedHome", "テレポートしました！");
        config.addDefault("messageCanceledTeleportHome", ChatColor.RED+"テレポートをキャンセルしました。");
        config.addDefault("messageCantTeleportHome", ChatColor.RED+"テレポートできません。\nテレポート先がまだ選択されていないようです。\n/sethomeで今いる位置に設定できます。");

        config.options().copyDefaults(true);
        config.save(config.getCurrentPath());
    }

    public static synchronized String get(String key){
        return config.getString(key);
    }

    public static synchronized int getInt(String key){
        return config.getInt(key);
    }

    public static synchronized double getDouble(String key){
        return config.getDouble(key);
    }

    public static synchronized void set(String key, Object value){
        config.set(key, value);
    }

    public static synchronized void save(){
        try {
            config.save(config.getCurrentPath());
        } catch (IOException ignored) {}
    }
}
