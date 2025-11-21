package com.Lino.battlePass.managers;

import com.Lino.battlePass.BattlePass;
import com.Lino.battlePass.gui.*;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuiManager {

    private final BattlePass plugin;

    private final Map<Integer, Integer> currentPages = new ConcurrentHashMap<>();

    public GuiManager(BattlePass plugin) {
        this.plugin = plugin;
    }

    public void openBattlePassGUI(Player player, int page) {
        if (page < 1) page = 1;
        int maxPage = plugin.getConfigManager().getMaxPage();
        if (page > maxPage) page = maxPage;

        BattlePassGui gui = new BattlePassGui(plugin, player, page);
        gui.open();
    }

    public void openMissionsGUI(Player player) {
        MissionsGui gui = new MissionsGui(plugin, player);
        gui.open();
    }

    public void openLeaderboardGUI(Player player) {
        LeaderboardGui gui = new LeaderboardGui(plugin, player);
        gui.open();
    }

    public void openShopGUI(Player player) {
        ShopGui gui = new ShopGui(plugin, player);
        gui.open();
    }

    public void clearCache() {
        currentPages.clear();
    }

    public void cleanExpiredCachePublic() {
        // This method is no longer needed with the new structure
        // but we keep it for compatibility
    }

    public Map<Integer, Integer> getCurrentPages() {
        return currentPages;
    }
}
