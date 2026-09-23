package me.boyakabrodyaka.hud.menu.game;

import me.boyakabrodyaka.hud.menu.game.button.GameButton;
import me.boyakabrodyaka.hud.menu.game.button.GameButtonRegistry;
import me.boyakabrodyaka.hud.menu.game.button.exit.CloseButton;
import me.boyakabrodyaka.hud.menu.game.button.lobby.CancelLobbyButton;
import me.boyakabrodyaka.hud.menu.game.button.lobby.ConfirmLobbyButton;
import me.boyakabrodyaka.hud.menu.game.button.lobby.DayActiveCheck;
import me.boyakabrodyaka.hud.menu.game.button.lobby.LobbyButton;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class GameMenuManager {

    private static final int MENU_SIZE = 54;
    private static final int MENU_CENTER = 22;
    private static final int MENU_SPACING = 1;
    private static final int SLOT_COUNT = 10;

    private static final int CONFIRM_SLOT_CONFIRM = 11;
    private static final int CONFIRM_SLOT_CANCEL = 15;

    private static final String LOBBY_WORLD = "tvrn_lobby";

    private final GameButtonRegistry buttonRegistry;
    private final GameButtonRegistry confirmRegistry;
    private final GameMenuLayout layout;
    private final ConcurrentHashMap<String, GameMenu> menus = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, GameConfirmMenu> confirmMenus = new ConcurrentHashMap<>();
    private final DayActiveCheck dayActiveCheck;
    private final LobbyButton lobbyButton;

    public GameMenuManager(DayActiveCheck dayActiveCheck) {
        this.buttonRegistry = new GameButtonRegistry();
        this.confirmRegistry = new GameButtonRegistry();
        this.layout = new GameMenuLayout(MENU_SIZE, MENU_CENTER, MENU_SPACING);
        this.dayActiveCheck = dayActiveCheck;
        this.lobbyButton = createLobbyButton();
        registerDefaultButtons();
        registerConfirmButtons();
    }

    public void open(Player player) {
        GameMenu menu = this.menus.computeIfAbsent(player.getName(), k -> new GameMenu());
        menu.render(player, this.buttonRegistry.getAll().toArray(new GameButton[0]));
        menu.open(player);
    }

    public void openConfirm(Player player) {
        GameConfirmMenu menu = this.confirmMenus.computeIfAbsent(player.getName(), k -> new GameConfirmMenu());

        for (GameButton button : this.confirmRegistry.getAll()) menu.getInventory().setItem(button.getSlot(), button.getItem(player));

        menu.open(player);
    }

    public void closeConfirm(Player player) { this.confirmMenus.remove(player.getName()); }

    public GameButton getButton(int slot) { return this.buttonRegistry.getBySlot(slot); }

    public GameButton getConfirmButton(int slot) { return this.confirmRegistry.getBySlot(slot); }

    public void remove(Player player) {
        this.menus.remove(player.getName());
        this.confirmMenus.remove(player.getName());
    }

    public void clear() {
        this.menus.clear();
        this.confirmMenus.clear();
        this.buttonRegistry.clear();
        this.confirmRegistry.clear();
    }

    private LobbyButton createLobbyButton() {
        int[] slots = this.layout.computeSlots(SLOT_COUNT);

        return new LobbyButton(slots[0], LOBBY_WORLD, this.dayActiveCheck, this);
    }

    private void registerDefaultButtons() {
        int[] slots = this.layout.computeSlots(SLOT_COUNT);

        this.buttonRegistry.register(this.lobbyButton);
        this.buttonRegistry.register(new CloseButton(slots[SLOT_COUNT - 1]));
    }

    private void registerConfirmButtons() {
        this.confirmRegistry.register(new ConfirmLobbyButton(CONFIRM_SLOT_CONFIRM, this, this.lobbyButton));
        this.confirmRegistry.register(new CancelLobbyButton(CONFIRM_SLOT_CANCEL, this));
    }
}