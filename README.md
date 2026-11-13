# CustomRankJoinMessagesNK

Custom join and quit messages for **NukkitPetteriM1Edition**, selected using the player's primary group in **LuckPerms**.

Created by **BrunoJaegerK**.

## Features

- Custom join messages by LuckPerms primary group.
- Custom quit messages by LuckPerms primary group.
- Enable or disable join and quit messages independently.
- `{name}` placeholder for the player's username.
- Case-insensitive group matching.
- LuckPerms is optional: when it is unavailable, the plugin uses the `default` message.
- Unknown groups also fall back to `default`.

## Requirements

- NukkitPetteriM1Edition.
- Java 17.
- LuckPerms is optional and only needed for rank-specific messages.

## Installation

1. Download the latest `CustomRankJoinMessagesNK` JAR from the repository's **Actions** artifact or **Releases**.
2. Put the JAR in your server's `plugins` folder.
3. Start the server once to generate `plugins/CustomRankJoinMessagesNK/config.yml`.
4. Edit the configuration and restart the server.

## Configuration

```yaml
ModifyJoinMessages: true
ModifyLeaveMessages: true

joinMessages:
  owner: "§l§7[§a+§7] [§cOwner§7] {name}"
  developer: "§l§7[§a+§7] [§bDeveloper§7] {name}"
  moderator: "§l§7[§a+§7] [§9Moderator§7] {name}"
  vip++: "§l§7[§a+§7] [§eVIP++§7] {name}"
  default: "§l§7[§a+§7] {name}"

leaveMessages:
  owner: "§l§7[§c-§7] [§cOwner§7] {name}"
  developer: "§l§7[§c-§7] [§bDeveloper§7] {name}"
  moderator: "§l§7[§c-§7] [§dModerator§7] {name}"
  vip++: "§l§7[§c-§7] [§eVIP++§7] {name}"
  default: "§l§7[§c-§7] {name}"
```

### Configuration notes

- Group keys must match the primary group names configured in LuckPerms.
- Group matching ignores uppercase/lowercase differences.
- `{name}` is replaced with the player's username.
- If LuckPerms is missing, a player has no loaded LuckPerms user, or their group has no configured entry, the `default` message is used.
- Set `ModifyJoinMessages` or `ModifyLeaveMessages` to `false` to leave that event's default server message untouched.
- Use an empty string for a group message if you intentionally want that group's message to be blank.

## Building

Build with Maven and Java 17:

```bash
mvn clean package
```

The output JAR is created in `target/CustomRankJoinMessagesNK-1.0.0.jar`.

## Support

Report bugs or request features through [GitHub Issues](https://github.com/BrunoJaegerK/CustomRankJoinMessagesNK/issues).

## License

This project is distributed under the MIT License. See [LICENSE](LICENSE).
