# Disclaimer

This is a **fork** of the original BattlePass plugin by [lino9999](https://github.com/lino9999/BattlePass).

## ⚠️ Important Notes:

### Version Compatibility
- **This build was compiled for Paper 1.21.8**
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

BattlePass is a complete plugin for Minecraft servers that adds a seasonal progression system similar to modern battle royale games.

Main Features:

Level System: 54 levels to unlock by earning XP

Dual-Track Rewards: Free rewards for all players + exclusive premium rewards

Daily Missions: 5 random missions that reset every day (kills, mining, PvP, etc.)

Limited Seasons: Each season lasts 30 days with automatic reset

Global Leaderboard: Top 10 players with highest levels achieved

Intuitive GUI: Complete graphical interface with page navigation

SQLite Database: Secure saving of all progress

Smart Notifications: Automatic alerts for new available rewards



Permissions:

battlepass.admin - Access to administrative commands
