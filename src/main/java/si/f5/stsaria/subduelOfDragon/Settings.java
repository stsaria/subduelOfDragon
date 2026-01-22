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
        config.addDefault("sleepMinPlayersRatio", 0.3);
        config.addDefault("sleepSec", 5);
        config.addDefault("gateTeleportSquareLocationLengthOfSide", 5);
        config.addDefault("gateTeleportSquareLocationStepBetweenNeighbor", 4);

        save();
        SubDuelOfDragon.getInstance().reloadConfig();

        config.addDefault("upgradedItemDiscovererSig", "<name>が発見した<item>");
        config.addDefault("messageCantUpgradeCoolDown", ChatColor.RED+"アップグレードできません。<cooldown>秒待ってください。");
        config.addDefault("messageUpgraded", ChatColor.GOLD+"アップグレードされました！");
        config.addDefault("messageCantCopyCoolDown", ChatColor.RED+"コピーできません。<cooldown>秒待ってください。");
        config.addDefault("messageCantCopyAir", ChatColor.RED+"コピーできません。持っているアイテムは空です。");
        config.addDefault("messageCopied", ChatColor.GOLD+"コピーしました！");
        config.addDefault("messageSetHome", ChatColor.GOLD+"設定しました！");
        config.addDefault("messageCantSetHome", ChatColor.RED+"設定できません。初期ワールドにのみ設定できます。");
        config.addDefault("messageStartedTeleport", "テレポートまで<cooldown>秒待ってください。\n途中でダメージ・移動するとキャンセルされます。");
        config.addDefault("messageTeleportedHome", ChatColor.GOLD+"テレポートしました！");
        config.addDefault("messageCanceledTeleportHome", ChatColor.RED+"テレポートをキャンセルしました。");
        config.addDefault("messageCantTeleportHome", ChatColor.RED+"テレポートできません。\nテレポート先がまだ選択されていないようです。\n/sethomeで今いる位置に設定できます。");
        config.addDefault("messageAddedStandByDimensionPlayer", ChatColor.GREEN+"<player>が待機列に並びました！　<playersLen>/<needPlayersLen>人");

        config.addDefault("messageUnlockedDimension", ChatColor.GREEN+"ディメンション開放！\nX座標:<x> Z座標:<z>\n/okで付近にテレポートして、リスポーン位置を固定しましょう！");
        config.addDefault("scoreBoardTitleCommandHelp", "ヘルプ");
        config.addDefault("scoreBoardMessageCommandHelp",
                """
                コマンド一覧
                /copy アイテムをコピーする
                /up このワールドで最高ランク
                の一つ下の武器を入手する
                /sethome 家の座標を設定する
                /home 設定した家の座標にテレ
                ポートする
                /ok 注目されている場所にテレポート
                して、リスポーン座標を更新する
                """
        );
        config.addDefault("actionBarCoord", ChatColor.RED+"X座標:<x> "+ChatColor.GREEN+"Y座標(高さ):<y> "+ChatColor.BLUE+"Z座標:<z>");
        config.addDefault("bossBarMessageGoForSleep", "睡眠中");
        config.addDefault("messageSomeoneInBed", ChatColor.DARK_AQUA+"<player>がベッドに寝ました。<inBedPlayers>/<minInBedPlayers>");
        config.addDefault("messageOkTeleported", ChatColor.GOLD+"テレポートし、リスポーン位置を変更しました！");
        config.addDefault("messageYouDontHaveTellMeOk", ChatColor.RED+"承諾するべき位置はありません。");
        config.addDefault("messageNewOkTeleportByAdmin", ChatColor.GREEN+"管理者がテレポートとリスポーン位置の固定を求めています！\n/okで付近にテレポートして、リスポーン位置を固定しましょう！");

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
