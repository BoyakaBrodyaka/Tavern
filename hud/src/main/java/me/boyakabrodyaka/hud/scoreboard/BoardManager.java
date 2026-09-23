package me.boyakabrodyaka.hud.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.concurrent.ConcurrentHashMap;

public class BoardManager {

    private static final String WORLD_SUFFIX = "_map";

    private final ConcurrentHashMap<String, Board> boards = new ConcurrentHashMap<>();
    private final BoardLayout layout;
    private final ScoreboardManager scoreboardManager;

    public BoardManager() {
        this.layout = new BoardLayout();
        this.scoreboardManager = Bukkit.getScoreboardManager();
    }

    public void update(Player player) {
        World world = player.getWorld();
        if (world == null) return;

        if (!isPlayerWorld(world.getName())) {
            hide(player);
            return;
        }

        show(player);
    }

    public void show(Player player) {
        Board board = this.boards.computeIfAbsent(player.getName(), k -> new Board(this.scoreboardManager.getNewScoreboard()));
        this.layout.apply(board, player);
        player.setScoreboard(board.getScoreboard());
    }

    public void hide(Player player) {
        player.setScoreboard(this.scoreboardManager.getMainScoreboard());
    }

    public void remove(Player player) {
        this.boards.remove(player.getName());
    }

    public void clear() {
        this.boards.clear();
    }

    private boolean isPlayerWorld(String worldName) {
        return worldName.contains(WORLD_SUFFIX);
    }
}