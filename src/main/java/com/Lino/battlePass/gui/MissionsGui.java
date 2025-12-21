package com.Lino.battlePass.gui;

import com.Lino.battlePass.BattlePass;
import com.Lino.battlePass.models.Mission;
import com.Lino.battlePass.models.PlayerData;
import com.Lino.battlePass.utils.GradientColorParser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MissionsGui extends BaseGui {

    private final Player player;
    private final PlayerData playerData;

    public MissionsGui(BattlePass plugin, Player player) {
        super(plugin, plugin.getMessageManager().getMessage("gui.missions"), 54);
        this.player = player;
        this.playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());
    }

    public void open() {
        Inventory gui = createInventory();

        setupTimerItem(gui);
        setupMissions(gui);
        gui.setItem(49, createBackButton());

        if (player.hasPermission("battlepass.admin")) {
            setupAdminResetButton(gui);
            setupMissionEditorButton(gui);
        }

        player.openInventory(gui);
    }

    private void setupTimerItem(Inventory gui) {
        ItemStack timer = new ItemStack(Material.CLOCK);
        ItemMeta meta = timer.getItemMeta();
        meta.setDisplayName(plugin.getMessageManager().getMessage("items.mission-timer.name"));

        List<String> lore = new ArrayList<>();
        for (String line : plugin.getMessageManager().getMessagesConfig().getStringList("items.mission-timer.lore")) {
            String processedLine = line.replace("%reset_time%", plugin.getMissionManager().getTimeUntilReset());
            lore.add(GradientColorParser.parse(processedLine));
        }

        meta.setLore(lore);
        timer.setItemMeta(meta);
        gui.setItem(4, timer);
    }

    private void setupMissions(Inventory gui) {
        List<Mission> allMissions = plugin.getMissionManager().getDailyMissions();

        List<Mission> regularMissions = new ArrayList<>();
        List<Mission> premiumMissions = new ArrayList<>();

        for (Mission mission : allMissions) {
            if (mission.isPremium) {
                premiumMissions.add(mission);
            } else {
                regularMissions.add(mission);
            }
        }

        int[] regularSlots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};
        int[] premiumSlots = {28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

        for (int i = 0; i < regularSlots.length; i++) {
            if (i < regularMissions.size()) {
                Mission mission = regularMissions.get(i);
                ItemStack missionItem = createMissionItem(mission, true);
                gui.setItem(regularSlots[i], missionItem);
            } else {
                gui.setItem(regularSlots[i], new ItemStack(Material.AIR));
            }
        }

        for (int i = 0; i < premiumSlots.length; i++) {
            if (i < premiumMissions.size()) {
                Mission mission = premiumMissions.get(i);
                boolean hasAccess = playerData != null && playerData.hasPremium;

                ItemStack missionItem = createMissionItem(mission, hasAccess);
                gui.setItem(premiumSlots[i], missionItem);
            } else {
                gui.setItem(premiumSlots[i], new ItemStack(Material.AIR));
            }
        }
    }

    private ItemStack createMissionItem(Mission mission, boolean hasAccess) {
        Material material;
        if (!hasAccess) {
            material = Material.BARRIER;
        } else {
            String key = mission.getKey();
            int progress = playerData.missionProgress.getOrDefault(key, 0);
            boolean completed = progress >= mission.required;
            material = completed ? Material.LIME_DYE : Material.GRAY_DYE;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (hasAccess) {
            int progress = playerData.missionProgress.getOrDefault(mission.getKey(), 0);
            boolean completed = progress >= mission.required;

            String itemNamePath = completed ? "items.mission-completed.name" : "items.mission-in-progress.name";
            String itemLorePath = completed ? "items.mission-completed.lore" : "items.mission-in-progress.lore";

            meta.setDisplayName(plugin.getMessageManager().getMessage(itemNamePath, "%mission%", mission.name));

            List<String> lore = plugin.getMessageManager().getMessages(itemLorePath,
                    "%progress%", String.valueOf(progress),
                    "%required%", String.valueOf(mission.required),
                    "%reward_xp%", String.valueOf(mission.xpReward),
                    "%reset_time%", plugin.getMissionManager().getTimeUntilReset()
            );

            if (mission.isPremium) {
                lore.addAll(plugin.getMessageManager().getMessages("items.premium-mission.additional-lore"));
            }

            meta.setLore(lore);
        } else {
            meta.setDisplayName(plugin.getMessageManager().getMessage("items.premium-mission.not-available.name",
                    "%mission%", mission.name));

            List<String> lore = plugin.getMessageManager().getMessages("items.premium-mission.not-available.lore",
                    "%reward_xp%", String.valueOf(mission.xpReward));

            meta.setLore(lore);
        }

        item.setItemMeta(meta);
        return item;
    }

    private void setupAdminResetButton(Inventory gui) {
        ItemStack resetButton = new ItemStack(Material.TNT);
        ItemMeta meta = resetButton.getItemMeta();

        meta.setDisplayName(GradientColorParser.parse("<gradient:#FF0000:#FF6B6B>Force Reset Missions</gradient>"));

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(GradientColorParser.parse("<gradient:#FF0000:#FF6B6B>Admin Only</gradient>"));
        lore.add(GradientColorParser.parse("&7Force reset all daily missions"));
        lore.add("");
        lore.add(GradientColorParser.parse("<gradient:#FF0000:#FF6B6B>▶ CLICK TO RESET</gradient>"));

        meta.setLore(lore);
        resetButton.setItemMeta(meta);
        gui.setItem(45, resetButton);
    }

    private void setupMissionEditorButton(Inventory gui) {
        ItemStack editButton = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = editButton.getItemMeta();

        meta.setDisplayName(GradientColorParser.parse("<gradient:#FFD700:#FFA500>Mission Editor</gradient>"));

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(GradientColorParser.parse("<gradient:#FFD700:#FFA500>Admin Only</gradient>"));
        lore.add(GradientColorParser.parse("&7Create, Edit or Delete"));
        lore.add(GradientColorParser.parse("&7mission templates."));
        lore.add("");
        lore.add(GradientColorParser.parse("<gradient:#00FF88:#45B7D1>▶ CLICK TO EDIT</gradient>"));

        meta.setLore(lore);
        editButton.setItemMeta(meta);
        gui.setItem(53, editButton);
    }
}
