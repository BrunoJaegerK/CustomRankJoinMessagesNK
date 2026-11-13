# CustomRankJoinMessagesNK

Custom join and quit messages and rank-based sounds for **NukkitPetteriM1Edition**, selected using the player's primary group in **LuckPerms**.

Created by **BrunoJaegerK**.

## Features

- Custom join and quit messages by LuckPerms primary group.
- A configurable join sound and quit sound for each rank.
- Sounds are heard by all other online players, including players in other worlds; the joining/leaving player is excluded.
- Enable or disable join and quit messages independently.
- Enable or disable each rank's join/quit sound independently.
- `{name}` placeholder for the player's username.
- Case-insensitive group matching.
- LuckPerms is optional: when unavailable, the plugin uses the default entries.
- Unknown groups fall back to default.

## Requirements

- NukkitPetteriM1Edition.
- Java 17.
- LuckPerms is optional and only needed for rank-specific entries.

## Installation

1. Download the latest `CustomRankJoinMessagesNK` JAR from the repository's **Actions** artifact or **Releases**.
2. Put the JAR in your server's `plugins` folder.
3. Start the server once to generate `plugins/CustomRankJoinMessagesNK/config.yml`.
4. Edit the configuration and restart the server.

## Configuration

~~~yaml
ModifyJoinMessages: true
ModifyLeaveMessages: true

joinMessages:
  owner: "§l§7[§a+§7] [§cOwner§7] {name}"
  default: "§l§7[§a+§7] {name}"

leaveMessages:
  owner: "§l§7[§c-§7] [§cOwner§7] {name}"
  default: "§l§7[§c-§7] {name}"

joinSounds:
  owner:
    enabled: true
    sound: "random.levelup"
  vip:
    enabled: true
    sound: "note.pling"
  default:
    enabled: true
    sound: "random.pop"

leaveSounds:
  owner:
    enabled: true
    sound: "random.totem"
  vip:
    enabled: true
    sound: "random.pop2"
  default:
    enabled: true
    sound: "random.pop"
~~~

### Sound configuration

Each rank under `joinSounds` and `leaveSounds` has two settings:

- `enabled`: `true` or `false`. Set it to `false` to mute that rank for that event.
- `sound`: the sound identifier, written exactly as shown in the sound list below.

Use separate identifiers for each rank and event to make the sounds different. If a rank has no entry, the `default` sound entry is used. Sounds are sent individually at each listener's position, so every other online player can hear them regardless of distance or world. The player joining or leaving is excluded.

### Available sound identifiers

The following are examples of valid identifiers included in the `cn.nukkit.level.Sound` enum in the **NukkitPetteriM1Edition release** source. Use the identifier exactly as written, including dots and underscores.

**Notification and short effects**

~~~yaml
random.levelup
random.orb
random.pop
random.pop2
random.totem
random.toast
random.click
random.fizz
random.break
random.burp
random.splash
random.potion.brewed
~~~

**Musical and note sounds**

~~~yaml
note.pling
note.bell
note.cow_bell
note.enderdragon
~~~

**Fireworks, portals and special effects**

~~~yaml
firework.blast
firework.large_blast
firework.launch
firework.shoot
firework.twinkle
portal.portal
portal.travel
portal.trigger
raid.horn
~~~

**Blocks and environment**

~~~yaml
block.bell.hit
block.enchanting_table.use
block.end_portal.spawn
block.end_portal_frame.fill
beacon.activate
beacon.deactivate
beacon.power
conduit.activate
ambient.cave
ambient.weather.lightning.impact
~~~

**Mobs and creatures**

~~~yaml
mob.allay.hurt
mob.blaze.shoot
mob.cat.meow
mob.creeper.say
mob.enderdragon.growl
mob.endermen.portal
mob.ghast.scream
mob.warden.roar
mob.wither.spawn
mob.villager.yes
mob.villager.no
mob.zombie.say
mob.piglin.angry
mob.ravager.roar
mob.wolf.growl
~~~

**Items and combat**

~~~yaml
item.trident.throw
item.trident.hit
item.trident.thunder
item.trident.riptide_1
item.trident.riptide_2
item.trident.riptide_3
crossbow.shoot
crossbow.loading.start
crossbow.loading.end
~~~

This is a curated selection, not the entire sound catalog. The PM1E `Sound.java` source contains a much larger list of identifiers (including block, mob, item, ambient, music and UI sounds): [View the NukkitPetteriM1Edition sound list](https://github.com/PetteriM1/NukkitPetteriM1Edition/blob/release/src/main/java/cn/nukkit/level/Sound.java).

**Compatibility note:** the sound identifier must exist in the Bedrock client version/resource pack used by the player. A valid server-side identifier may not produce audible sound if that client does not contain the corresponding sound event. Custom resource-pack sounds are not automatically registered by this plugin.

### General configuration notes

- Group keys must match the primary group names configured in LuckPerms.
- Group matching ignores uppercase/lowercase differences.
- `{name}` is replaced with the player's username.
- If LuckPerms is missing, the player's LuckPerms user is not loaded, or their group has no configured entry, the `default` entry is used.
- Set `ModifyJoinMessages` or `ModifyLeaveMessages` to `false` to leave that event's default server message untouched. Sound settings are independent of these message toggles.
- Use an empty string for a group message if you intentionally want that group's message to be blank.

## Building

Build with Maven and Java 17:

~~~bash
mvn clean package
~~~

The output JAR is created in `target/CustomRankJoinMessagesNK-1.0.0.jar`.

## Support

Report bugs or request features through [GitHub Issues](https://github.com/BrunoJaegerK/CustomRankJoinMessagesNK/issues).

## License

This project is distributed under the MIT License. See [LICENSE](LICENSE).
