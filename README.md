# CustomRankJoinMessagesNK

Custom join and quit messages and rank-based sounds for **NukkitPetteriM1Edition**, selected using the player's primary group in **LuckPerms**.

Created by **BrunoJaegerK**.

## Features

- Custom join and quit messages by LuckPerms primary group.
- A configurable join sound and quit sound for each rank.
- Sounds are heard by all other online players, including players in other worlds; the joining/leaving player is excluded.
- Enable or disable join and quit messages independently.
- Enable or disable each rank's join/quit sound independently.
- {name} placeholder for the player's username.
- Case-insensitive group matching.
- LuckPerms is optional: when unavailable, the plugin uses the default entries.
- Unknown groups fall back to default.

## Requirements

- NukkitPetteriM1Edition.
- Java 17.
- LuckPerms is optional and only needed for rank-specific entries.

## Installation

1. Download the latest CustomRankJoinMessagesNK JAR from the repository's Actions artifact or Releases.
2. Put the JAR in your server's plugins folder.
3. Start the server once to generate plugins/CustomRankJoinMessagesNK/config.yml.
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
  default:
    enabled: true
    sound: "random.pop"

leaveSounds:
  owner:
    enabled: true
    sound: "random.pop"
  default:
    enabled: true
    sound: "random.pop"
~~~

### Sound configuration

Each rank under joinSounds and leaveSounds has:

- enabled: true or false.
- sound: Bedrock/Nukkit sound identifier, for example random.levelup, random.pop, or random.orb.

Use separate identifiers for each rank and event to make the sounds different. If a rank has no entry, the default sound is used. Setting enabled: false disables only that rank's sound for that event. Sounds are sent individually at each listener's position, so every other online player can hear them regardless of distance or world. The player joining or leaving is excluded.

### General configuration notes

- Group keys must match the primary group names configured in LuckPerms.
- Group matching ignores uppercase/lowercase differences.
- {name} is replaced with the player's username.
- If LuckPerms is missing, the player's LuckPerms user is not loaded, or their group has no configured entry, the default entry is used.
- Set ModifyJoinMessages or ModifyLeaveMessages to false to leave that event's default server message untouched. Sound settings are independent of these message toggles.
- Use an empty string for a group message if you intentionally want that group's message to be blank.

## Building

Build with Maven and Java 17:

~~~bash
mvn clean package
~~~

The output JAR is created in target/CustomRankJoinMessagesNK-1.0.0.jar.

## Support

Report bugs or request features through [GitHub Issues](https://github.com/BrunoJaegerK/CustomRankJoinMessagesNK/issues).

## License

This project is distributed under the MIT License. See LICENSE.
