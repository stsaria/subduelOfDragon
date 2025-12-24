package si.f5.stsaria.subduelOfDragon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class HelpScoreBoard {
    public static void show(Player p){
        ScoreboardManager sbm = Objects.requireNonNull(Bukkit.getScoreboardManager());
        Scoreboard sb = sbm.getNewScoreboard();
        Objective o = sb.registerNewObjective("help", Criteria.DUMMY, Settings.get("scoreBoardTitleCommandHelp"));
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        String[] lines = Settings.get("scoreBoardMessageCommandHelp").split("\n");
        for (int i = 0; i < lines.length; i++){
            o.getScore(lines[i]).setScore(lines.length-1-i);
        }
        p.setScoreboard(sb);
    }
}
