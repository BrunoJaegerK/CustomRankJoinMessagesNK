package com.brunojaegerk.customrankjoinmessages;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Custom join and quit messages selected by a player's LuckPerms primary group.
 * LuckPerms is optional; the configured default message is used when unavailable.
 */
public final class CustomRankJoinMessagesNK extends PluginBase implements Listener {

    private Object luckPermsUserManager;
    private Method getUserMethod;
    private Method getPrimaryGroupMethod;
    private boolean luckPermsAvailable;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        initializeLuckPerms();

        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("CustomRankJoinMessagesNK enabled.");
        if (luckPermsAvailable) {
            getLogger().info("LuckPerms detected. Rank-based messages are active.");
        } else {
            getLogger().info("LuckPerms not detected. Using the default messages.");
        }
    }

    private void initializeLuckPerms() {
        Plugin luckPermsPlugin = getServer().getPluginManager().getPlugin("LuckPerms");
        if (luckPermsPlugin == null) {
            return;
        }

        try {
            ClassLoader luckPermsClassLoader = luckPermsPlugin.getClass().getClassLoader();
            Class<?> providerClass = Class.forName(
                    "net.luckperms.api.LuckPermsProvider", true, luckPermsClassLoader
            );
            Object luckPerms = providerClass.getMethod("get").invoke(null);
            Object userManager = luckPerms.getClass().getMethod("getUserManager").invoke(luckPerms);

            Class<?> userManagerClass = Class.forName(
                    "net.luckperms.api.model.user.UserManager", true, luckPermsClassLoader
            );
            getUserMethod = userManagerClass.getMethod("getUser", UUID.class);
            Class<?> userClass = Class.forName(
                    "net.luckperms.api.model.user.User", true, luckPermsClassLoader
            );
            getPrimaryGroupMethod = userClass.getMethod("getPrimaryGroup");
            luckPermsUserManager = userManager;
            luckPermsAvailable = true;
        } catch (ReflectiveOperationException | LinkageError exception) {
            luckPermsAvailable = false;
            getLogger().warning("LuckPerms was found, but its API could not be accessed. Default messages will be used.");
            getLogger().debug(exception.toString());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!getConfig().getBoolean("ModifyJoinMessages", true)) {
            return;
        }

        Player player = event.getPlayer();
        String group = getPrimaryGroup(player);
        String message = getConfiguredMessage("joinMessages", group, player.getName());
        event.setJoinMessage(message);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!getConfig().getBoolean("ModifyLeaveMessages", true)) {
            return;
        }

        Player player = event.getPlayer();
        String group = getPrimaryGroup(player);
        String message = getConfiguredMessage("leaveMessages", group, player.getName());
        event.setQuitMessage(message);
    }

    private String getPrimaryGroup(Player player) {
        if (!luckPermsAvailable || luckPermsUserManager == null || getUserMethod == null) {
            return "default";
        }

        try {
            Object userResult = getUserMethod.invoke(luckPermsUserManager, player.getUniqueId());
            if (userResult == null) {
                return "default";
            }

            // LuckPerms returns a CompletableFuture<User> from UserManager#getUser.
            Object user = userResult instanceof java.util.concurrent.CompletableFuture<?> future
                    ? future.join()
                    : userResult;
            if (user == null) {
                return "default";
            }

            String group = (String) getPrimaryGroupMethod.invoke(user);
            if (group == null || group.isBlank()) {
                return "default";
            }
            return group.toLowerCase(Locale.ROOT);
        } catch (ReflectiveOperationException | LinkageError exception) {
            getLogger().debug("Could not read the LuckPerms group for " + player.getName() + ": " + exception);
            return "default";
        }
    }

    private String getConfiguredMessage(String section, String group, String playerName) {
        Map<String, Object> messages = getConfig().getSection(section);
        Object configured = null;

        if (messages != null) {
            for (Map.Entry<String, Object> entry : messages.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(group)) {
                    configured = entry.getValue();
                    break;
                }
            }

            if (configured == null) {
                for (Map.Entry<String, Object> entry : messages.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase("default")) {
                        configured = entry.getValue();
                        break;
                    }
                }
            }
        }

        if (configured == null) {
            return "";
        }

        return String.valueOf(configured).replace("{name}", playerName);
    }
}
