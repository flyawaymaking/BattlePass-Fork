# Disclaimer

This is a **fork** of the original BattlePass plugin by [lino9999](https://github.com/lino9999/BattlePass).

## ⚠️ Important Notes:

### Version Compatibility
- **This build was compiled for Paper 1.21.10**
- Compatibility with other server versions or platforms is **NOT guaranteed**
- Use on different versions may require additional modifications

### Modifications from Original
This fork includes the following enhancements:
- **Expanded mission functionality** with additional features and mission types
- Various improvements and customizations beyond the original codebase

## New Features & Improvements

### 1. Added support for **additional targets** for task completion
This allows tasks to be more generic and flexible. Example:

```yaml
kill-skeletons:
  type: KILL_MOB
  target: SKELETON
  additional-targets:
    - STRAY
    - BOGGED
  display-name: "Kill <amount> Skeletons"
  min-required: 10
  max-required: 25
  min-xp: 100
  max-xp: 200
  weight: 10
```

---

### 2. Added support for selecting **specific villager professions** for trading
Supported targets: `ANY`, `FARMER`, `FISHERMAN`, `SHEPHERD`, `FLETCHER`, `LIBRARIAN`, `CARTOGRAPHER`, `CLERIC`, `ARMORER`, `WEAPONSMITH`, `TOOLSMITH`, `BUTCHER`, `LEATHERWORKER`, `MASON`, `MERCHANT`. Example:

```yaml
trade-agriculture:
  type: TRADE_VILLAGER
  target: FARMER
  additional-targets:
    - FISHERMAN
    - SHEPHERD
    - BUTCHER
  display-name: "Trade with Agriculture Villagers <amount> Times"
  min-required: 4
  max-required: 10
  min-xp: 200
  max-xp: 350
  weight: 8
```

---

### 3. Added support for **enchanting multiple item types**
Example:

```yaml
enchant-swords:
  type: ENCHANT_ITEM
  target: DIAMOND_SWORD
  additional-targets:
    - "NETHERITE_SWORD"
    - "IRON_SWORD"
    - "GOLDEN_SWORD"
    - "STONE_SWORD"
    - "WOODEN_SWORD"
  display-name: "Enchant <amount> swords"
  min-required: 1
  max-required: 3
  min-xp: 150
  max-xp: 300
  weight: 5
```

---

### 4. Added support for **dealing damage to specific mobs**
Supported targets: `ANY`, `PLAYER`, `ANY MOBS`. Example:

```yaml
damage-sea-creatures:
  type: DAMAGE_DEALT
  target: GUARDIAN
  additional-targets:
    - ELDER_GUARDIAN
    - SQUID
    - GLOW_SQUID
    - DOLPHIN
    - TURTLE
    - COD
    - SALMON
    - PUFFERFISH
    - TROPICAL_FISH
    - AXOLOTL
  display-name: "Deal <amount> Damage to sea creatures"
  min-required: 200
  max-required: 600
  min-xp: 180
  max-xp: 350
  weight: 5
```

---

### 5. Added support for **taking damage from specific mobs or damage types**
Supported targets: `ANY`, `PLAYER`, `ANY MOBS`, `FIRE`, `LAVA`, `FALL`, `DROWNING`, `POISON`, `EXPLOSION`, `VOID`, `STARVATION`, `CONTACT`. Example:

```yaml
take-damage-fall:
  type: DAMAGE_TAKEN
  target: FALL
  display-name: "Take <amount> Damage from fall"
  min-required: 25
  max-required: 75
  min-xp: 100
  max-xp: 200
  weight: 4
```

---

### 6. Added support for **death from specific mobs or damage types**
Supported targets: `ANY`, `PLAYER`, `ANY MOBS`, `FIRE`, `LAVA`, `FALL`, `DROWNING`, `POISON`, `EXPLOSION`, `VOID`, `STARVATION`, `CONTACT`. Example:

```yaml
die-times-explosion:
  type: DEATH
  target: EXPLOSION
  display-name: "Die <amount> Times from explosion"
  min-required: 1
  max-required: 3
  min-xp: 100
  max-xp: 200
  weight: 3
```

---

### 7. Added support for **specific types of player movement**
Supported targets: `ANY`, `WALK`, `SWIM`, `FLY`, `SNEAK`. Example:

```yaml
swim-blocks:
  type: WALK_DISTANCE
  target: SWIM
  display-name: "Swim <amount> Blocks"
  min-required: 1000
  max-required: 3000
  min-xp: 100
  max-xp: 200
  weight: 8
```

---

### Support
- This is a **community-maintained fork** - not the official version
- Issues should be reported to this fork's repository, not the original
- No official support is provided by the original author for this modified version

### Credits
All original credit goes to [lino9999](https://github.com/lino9999/BattlePass) for creating the base plugin.
This fork builds upon their work with additional features and updates.

---

## Original Documentation

*The following content is from the original BattlePass repository:*

# ⚔️ BattlePass - Ultimate Progression Plugin for Minecraft (1.21+)

> **The most advanced, feature-rich Battle Pass system for Spigot & Paper servers.**
> Engage your players with Daily Missions, Tiered Rewards, and a custom Currency Shop.
> **No config editing required** — manage everything via In-Game GUI!

![Java](https://img.shields.io/badge/Java-21-orange) ![Spigot](https://img.shields.io/badge/API-1.21-yellow) ![License](https://img.shields.io/badge/License-MIT-blue)

---

## 🌟 Why Choose BattlePass?
Unlike other plugins, **BattlePass** focuses on ease of use for admins and engagement for players. It includes a powerful **In-Game Editor**, robust **MySQL Database** support for networks, and deep integrations with popular plugins like **MythicMobs**.

### 🔥 Key Features

* **🏆 Seasonal Progression System**
    * Fully customizable tier system (default 54 levels).
    * **Dual Reward Tracks**: Free Pass (for everyone) and Premium Pass (VIP/Paid).
    * Automatic season reset options (Monthly or Duration-based).

* **🛠️ In-Game GUI Editor (No YAML needed!)**
    * **Mission Editor**: Create, edit, or delete daily missions directly inside the game.
    * **Rewards Editor**: Drag-and-drop items from your inventory to set rewards for any level.

* **💾 Database & Sync Support**
    * **SQLite** (Default): Plug and play for single servers.
    * **MySQL**: Full support for syncing player progress, XP, and rewards across a BungeeCord/Velocity network.

* **📜 Dynamic Missions**
    * **7 Daily Missions** generated randomly every day.
    * **Mission Types**: Mining, Crafting, Fishing, Farming, Killing Mobs, Playtime, Walking Distance, and more!.

* **💰 Battle Coins & Shop**
    * Players earn **Battle Coins** by ranking in the daily leaderboard.
    * Spend coins in the customizable **Shop GUI** for exclusive items, XP boosts, or commands.

* **🔌 Powerful Integrations**
    * **PlaceholderAPI**: Full support for scoreboards, tabs, and chat.
    * **MythicMobs**: Create missions to kill specific custom bosses or mobs.

---

## 📥 Installation

1.  Download `BattlePass.jar`.
2.  Drop it into your server's `/plugins/` folder.
3.  (Optional) Install **PlaceholderAPI** for placeholders.
4.  Restart your server.
5.  Enjoy! Config files (`config.yml`, `missions.yml`, `shop.yml`) will generate automatically.

---

## 🎮 Commands & Permissions

| Command | Permission | Description |
| :--- | :--- | :--- |
| `/bp` or `/battlepass` | `battlepass.use` | Opens the main Battle Pass menu. |
| `/bp help` | `battlepass.use` | Shows the help menu. |
| `/bp giveitem <type> <player> <amount>` | `battlepass.admin` | Give special items (Premium Voucher, Coins, XP Boosts). |
| `/bp addpremium <player>` | `battlepass.admin` | Force unlock Premium Pass for a player. |
| `/bp addxp <player> <amount>` | `battlepass.admin` | Give XP to a player. |
| `/bp reset season` | `battlepass.admin` | Force reset the entire season progress. |
| `/bp reset missions` | `battlepass.admin` | Force generate new daily missions. |
| `/bp resetplayer <name>` | `battlepass.admin` | Reset battle pass progress for a player. |
| `/bp reload` | `battlepass.admin` | Reloads all configuration files. |

---

## 🧩 Placeholders (PAPI)

Add these to your scoreboard or tablist!

* `%battlepass_level%` - Player's current tier.
* `%battlepass_xp_progress%` - Formatted XP (e.g., 50/200).
* `%battlepass_premium_status%` - Returns "Active" or "Inactive".
* `%battlepass_season_time%` - Time remaining in the current season.
* `%battlepass_coins%` - Current Battle Coins balance.
* `%battlepass_daily_reward_available%` - Check if daily reward is ready ("Yes"/"No").

*(See `Placeholders.md` for the full list)*

---

## 📸 Screenshots

<img width="927" height="352" alt="image" src="https://github.com/user-attachments/assets/1ed73a90-6776-4746-a52a-7c57d4389cf9" />


---

<div align="center">
   <p>I've just launched https://www.hytaleservers.it/</p>
   <p>Are you working on a server? List it now for free and build your audience before launch.​</p>
</div>

---

<div align="center">
  <p>Made with ❤️ by Lino</p>
  <p>Found a bug? Report it in the Issues tab!</p>
</div>
