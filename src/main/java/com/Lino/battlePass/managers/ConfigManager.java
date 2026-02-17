package com.Lino.battlePass.managers;

import com.Lino.battlePass.BattlePass;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigManager {

    private final BattlePass plugin;
    private FileConfiguration config;
    private FileConfiguration missionsConfig;
    private FileConfiguration messagesConfig;
    private FileConfiguration shopConfig;
    private FileConfiguration battlePassFreeConfig;
    private FileConfiguration battlePassPremiumConfig;

    private int xpPerLevel = 200;
    private int dailyMissionsCount = 7;
    private int premiumAdditionalMissionsCount = 2;
    private String seasonResetType = "DURATION";
    private int seasonDuration = 30;
    private int dailyRewardXP = 200;
    private final List<Integer> coinsDistribution = new ArrayList<>();
    private boolean shopEnabled = true;
    private boolean resetCoinsOnSeasonEnd = true;
    private int coinsDistributionHours = 24;
    private int missionResetHours = 24;
    private boolean customItemSoundsEnabled = true;

    private Material guiFreeLockedMaterial = Material.GRAY_STAINED_GLASS;
    private Material guiPremiumLockedMaterial = Material.GRAY_STAINED_GLASS;
    private Material guiPremiumNoPassMaterial = Material.IRON_BARS;
    private Material guiRewardAvailableMaterial = Material.CHEST;
    private Material guiSeparatorMaterial = Material.GRAY_STAINED_GLASS_PANE;
    private Material guiFreeClaimedMaterial = Material.GREEN_STAINED_GLASS;
    private Material guiPremiumClaimedMaterial = Material.LIME_STAINED_GLASS;
    private boolean hideFreeClaimedRewards = false;
    private boolean hidePremiumClaimedRewards = false;

    private String databaseType;
    private String dbHost;
    private int dbPort;
    private String dbName;
    private String dbUser;
    private String dbPass;
    private String dbPrefix;
    private int dbPoolSize;

    public ConfigManager(BattlePass plugin) {
        this.plugin = plugin;
        reload();
    }

    public FileConfiguration migrateConfig(String fileName) {
        boolean isMainConfig = fileName.equalsIgnoreCase("config.yml");

        File file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }

        FileConfiguration config;
        if (isMainConfig) {
            plugin.reloadConfig();
            config = plugin.getConfig();
        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }

        YamlConfiguration defaultConfig;
        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(plugin.getResource(fileName)),
                StandardCharsets.UTF_8
        )) {
            defaultConfig = YamlConfiguration.loadConfiguration(reader);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load default " + fileName);
            return config;
        }

        int currentVersion = defaultConfig.getInt("version", 1);
        int fileVersion = config.getInt("version", 0);

        if (fileVersion < currentVersion) {
            plugin.getLogger().info("Updating " + fileName +
                    " from version " + fileVersion +
                    " to " + currentVersion);

            config.setDefaults(defaultConfig);
            config.options().copyDefaults(true);
            config.set("version", currentVersion);

            try {
                if (isMainConfig) {
                    plugin.saveConfig();
                } else {
                    config.save(file);
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to save " + fileName);
            }
        }

        return config;
    }

    public void reload() {
        config = migrateConfig("config.yml");
        xpPerLevel = config.getInt("experience.xp-per-level", 200);
        seasonResetType = config.getString("season.reset-type", "DURATION");
        seasonDuration = config.getInt("season.duration", 30);
        dailyRewardXP = config.getInt("daily-reward.xp", 200);
        shopEnabled = config.getBoolean("shop.enabled", true);
        resetCoinsOnSeasonEnd = config.getBoolean("season.reset-coins-on-season-end", true);
        coinsDistributionHours = config.getInt("battle-coins.distribution-hours", 24);
        missionResetHours = config.getInt("missions.reset-hours", 24);
        customItemSoundsEnabled = config.getBoolean("custom-items.sounds-enabled", true);

        databaseType = config.getString("database.type", "SQLITE");
        dbHost = config.getString("database.host", "localhost");
        dbPort = config.getInt("database.port", 3306);
        dbName = config.getString("database.database", "battlepass");
        dbUser = config.getString("database.username", "root");
        dbPass = config.getString("database.password", "");
        dbPrefix = config.getString("database.prefix", "bp_");
        dbPoolSize = config.getInt("database.pool-size", 10);

        guiFreeLockedMaterial = parseMaterial(config.getString("gui.reward-locked.free", "GRAY_STAINED_GLASS"), Material.GRAY_STAINED_GLASS);
        guiPremiumLockedMaterial = parseMaterial(config.getString("gui.reward-locked.premium", "GRAY_STAINED_GLASS"), Material.GRAY_STAINED_GLASS);
        guiPremiumNoPassMaterial = parseMaterial(config.getString("gui.premium-no-pass", "IRON_BARS"), Material.IRON_BARS);
        guiRewardAvailableMaterial = parseMaterial(config.getString("gui.reward-available", "CHEST"), Material.CHEST);
        guiSeparatorMaterial = parseMaterial(config.getString("gui.separator", "GRAY_STAINED_GLASS_PANE"), Material.GRAY_STAINED_GLASS_PANE);

        String freeClaimedStr = config.getString("gui.reward-claimed.free", "GREEN_STAINED_GLASS");
        if (freeClaimedStr.equalsIgnoreCase("NONE")) {
            hideFreeClaimedRewards = true;
            guiFreeClaimedMaterial = null;
        } else {
            hideFreeClaimedRewards = false;
            guiFreeClaimedMaterial = parseMaterial(freeClaimedStr, Material.GREEN_STAINED_GLASS);
        }

        String premiumClaimedStr = config.getString("gui.reward-claimed.premium", "LIME_STAINED_GLASS");
        if (premiumClaimedStr.equalsIgnoreCase("NONE")) {
            hidePremiumClaimedRewards = true;
            guiPremiumClaimedMaterial = null;
        } else {
            hidePremiumClaimedRewards = false;
            guiPremiumClaimedMaterial = parseMaterial(premiumClaimedStr, Material.LIME_STAINED_GLASS);
        }

        coinsDistribution.clear();
        ConfigurationSection section = config.getConfigurationSection("battle-coins.distribution");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                coinsDistribution.add(section.getInt(key));
            }
        }

        File missionsFile = new File(plugin.getDataFolder(), "missions.yml");
        missionsConfig = YamlConfiguration.loadConfiguration(missionsFile);
        dailyMissionsCount = missionsConfig.getInt("daily-missions-count", 7);
        premiumAdditionalMissionsCount = getMissionsConfig().getInt("premium-additional-missions", 2);

        messagesConfig = migrateConfig("messages.yml");

        File shopFile = new File(plugin.getDataFolder(), "shop.yml");
        shopConfig = YamlConfiguration.loadConfiguration(shopFile);

        File battlePassFreeFile = new File(plugin.getDataFolder(), "BattlePassFREE.yml");
        if (!battlePassFreeFile.exists()) {
            plugin.saveResource("BattlePassFREE.yml", false);
        }
        battlePassFreeConfig = YamlConfiguration.loadConfiguration(battlePassFreeFile);

        File battlePassPremiumFile = new File(plugin.getDataFolder(), "BattlePassPREMIUM.yml");
        if (!battlePassPremiumFile.exists()) {
            plugin.saveResource("BattlePassPREMIUM.yml", false);
        }
        battlePassPremiumConfig = YamlConfiguration.loadConfiguration(battlePassPremiumFile);
    }

    private Material parseMaterial(String materialName, Material defaultMaterial) {
        if (materialName == null || materialName.isEmpty()) {
            return defaultMaterial;
        }

        try {
            return Material.matchMaterial(materialName.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid material '" + materialName + "' in config. Using default: " + defaultMaterial.name());
            return defaultMaterial;
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getMissionsConfig() {
        return missionsConfig;
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public FileConfiguration getShopConfig() {
        return shopConfig;
    }

    public FileConfiguration getBattlePassFreeConfig() {
        return battlePassFreeConfig;
    }

    public FileConfiguration getBattlePassPremiumConfig() {
        return battlePassPremiumConfig;
    }

    public int getXpPerLevel() {
        return xpPerLevel;
    }

    public int getDailyMissionsCount() {
        return dailyMissionsCount;
    }

    public int getPremiumAdditionalMissionsCount() {
        return premiumAdditionalMissionsCount;
    }

    public String getSeasonResetType() {
        return seasonResetType;
    }

    public int getSeasonDuration() {
        return seasonDuration;
    }

    public int getDailyRewardXP() {
        return dailyRewardXP;
    }

    public List<Integer> getCoinsDistribution() {
        return coinsDistribution;
    }

    public boolean isShopEnabled() {
        return shopEnabled;
    }

    public boolean isResetCoinsOnSeasonEnd() {
        return resetCoinsOnSeasonEnd;
    }

    public int getCoinsDistributionHours() {
        return coinsDistributionHours;
    }

    public int getMissionResetHours() {
        return missionResetHours;
    }

    public boolean isCustomItemSoundsEnabled() {
        return customItemSoundsEnabled;
    }

    public Material getGuiFreeLockedMaterial() {
        return guiFreeLockedMaterial;
    }

    public Material getGuiPremiumLockedMaterial() {
        return guiPremiumLockedMaterial;
    }

    public Material getGuiPremiumNoPassMaterial() {
        return guiPremiumNoPassMaterial;
    }

    public Material getGuiRewardAvailableMaterial() {
        return guiRewardAvailableMaterial;
    }

    public Material getGuiSeparatorMaterial() {
        return guiSeparatorMaterial;
    }

    public Material getGuiFreeClaimedMaterial() {
        return guiFreeClaimedMaterial;
    }

    public Material getGuiPremiumClaimedMaterial() {
        return guiPremiumClaimedMaterial;
    }

    public boolean shouldHideFreeClaimedRewards() {
        return hideFreeClaimedRewards;
    }

    public boolean shouldHidePremiumClaimedRewards() {
        return hidePremiumClaimedRewards;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public String getDbHost() {
        return dbHost;
    }

    public int getDbPort() {
        return dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    public String getDbPrefix() {
        return dbPrefix;
    }

    public int getDbPoolSize() {
        return dbPoolSize;
    }
}
