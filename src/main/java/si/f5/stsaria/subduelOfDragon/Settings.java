package si.f5.stsaria.subduelOfDragon;

import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class Settings {
    private static FileConfiguration config = null;

    private static File getFile() {
        return new File(SubDuelOfDragon.getInstance().getDataFolder(), "config.yml");
    }

    public static synchronized void init() throws IOException {
        config = SubDuelOfDragon.getInstance().getConfig();

        config.options().copyDefaults(true);

        config.addDefault("upgradeCoolDown", 10*60);
        config.addDefault("copyCoolDown", 15*60);
        config.addDefault("enderDragonStrangePerPerson", 75);
        config.addDefault("enderDragonStrangeImpactRatio", 0.25);
        config.addDefault("homeStandByDelay", 5);
        config.addDefault("dimensionMoveStandByMinPlayersRatio", 0.25);

        save();
        SubDuelOfDragon.getInstance().reloadConfig();

        config.set("upgradedItemDiscovererSig", "<name>が発見した<item>");
        config.set("messageCantUpgrade", ChatColor.RED+"アップグレードできません。最後のアップグレードから"+get("upgradeCoolDown")+"秒待ってください。");
        config.set("messageUpgraded", "アップグレードされました！");
        config.set("messageCantCopyCoolDown", ChatColor.RED+"コピーできません。最後のコピーから"+get("copyCoolDown")+"秒待ってください。");
        config.set("messageCantCopyAir", ChatColor.RED+"コピーできません。持っているアイテムは空です。");
        config.set("messageCopied", "コピーしました！");
        config.set("messageSetHome", "設定しました！");
        config.set("messageCantSetHome", ChatColor.RED+"設定できません。初期ワールドにのみ設定できます。");
        config.set("messageStartedTeleport", "テレポートまで"+config.get("homeStandByDelay")+"秒待ってください。\n途中でダメージ・移動するとキャンセルされます。");
        config.set("messageTeleportedHome", "テレポートしました！");
        config.set("messageCanceledTeleportHome", ChatColor.RED+"テレポートをキャンセルしました。");
        config.set("messageCantTeleportHome", ChatColor.RED+"テレポートできません。\nテレポート先がまだ選択されていないようです。\n/sethomeで今いる位置に設定できます。");
        config.set("messageStartStandByDimension", "次のディメンション待機列に入りました！\n");
        config.set("messageAddedStandByDimensionPlayer", ChatColor.AQUA+"待機列に並びました！：<playersLen>/<needPlayersLen>人");
        config.set("titleUnlockedDimension", "ディメンション開放！");

        save();
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
            config.save(getFile());
        } catch (IOException ignore) {}
    }

    public static synchronized String getAllString() throws IOException {
        return FileUtils.readFileToString(getFile());
    }
}
