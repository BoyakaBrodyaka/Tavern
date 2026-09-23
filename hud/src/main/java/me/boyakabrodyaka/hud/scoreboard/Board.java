package me.boyakabrodyaka.hud.scoreboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.nio.charset.StandardCharsets;

@Getter
public class Board {

    private static final int TEAM_COUNT = 16;
    private static final int MAX_PREFIX_BYTES = 16;
    private static final char COLOR_CHAR = '§';
    private static final int MAX_LINE_INDEX = 15;

    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Team[] teams = new Team[TEAM_COUNT];

    public Board(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        this.objective = scoreboard.registerNewObjective("tvrn", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void setTitle(String title) {
        this.objective.setDisplayName(title);
    }

    public void setLine(int slot, String text) {
        if (slot < 0) return;
        if (slot >= TEAM_COUNT) return;

        String entry = COLOR_CHAR + Integer.toHexString(slot);

        Team team = this.teams[slot];
        if (team == null) {
            team = this.scoreboard.registerNewTeam("tvrn_" + slot);
            team.addEntry(entry);
            this.teams[slot] = team;
        }

        String[] parts = split(text);

        team.setPrefix(parts[0]);
        team.setSuffix(parts[1]);
        this.objective.getScore(entry).setScore(MAX_LINE_INDEX - slot);
    }

    private String[] split(String text) {
        StringBuilder prefix = new StringBuilder();
        StringBuilder suffix = new StringBuilder();

        int bytes = 0;
        boolean prefixDone = false;
        int index = 0;

        while (index < text.length()) {
            char character = text.charAt(index);

            if (isColorCode(character, index, text)) {
                String code = text.substring(index, index + 2);

                if (prefixDone) suffix.append(code);
                else prefix.append(code);

                index += 2;
                continue;
            }

            int charBytes = utf8Length(character);

            if (!prefixDone && bytes + charBytes > MAX_PREFIX_BYTES) {
                prefixDone = true;

                String lastColor = lastColor(prefix);
                if (!lastColor.isEmpty()) suffix.append(lastColor);
            }

            if (prefixDone) suffix.append(character);
            else {
                prefix.append(character);
                bytes += charBytes;
            }

            index++;
        }

        return new String[]{prefix.toString(), suffix.toString()};
    }

    private boolean isColorCode(char character, int index, String text) {
        if (character != COLOR_CHAR) return false;
        return index + 1 < text.length();
    }

    private int utf8Length(char character) {
        return String.valueOf(character).getBytes(StandardCharsets.UTF_8).length;
    }

    private String lastColor(StringBuilder builder) {
        for (int index = builder.length() - 2; index >= 0; index--) {
            if (builder.charAt(index) != COLOR_CHAR) continue;

            return builder.substring(index, index + 2);
        }

        return "";
    }
}