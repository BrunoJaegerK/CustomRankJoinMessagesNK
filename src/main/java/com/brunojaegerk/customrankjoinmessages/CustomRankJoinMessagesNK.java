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

/** Rank-based join/quit messages and sounds using optional LuckPerms integration. */
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
        getLogger().info(luckPermsAvailable
                ? "LuckPerms detected. Rank-based messages and sounds are active."
                : "LuckPerms not detected. Using the default messages and sounds.");
    }

    private void initializeLuckPerms() {
        Plugin luckPermsPlugin = getServer().getPluginManager().getPlugin("LuckPerms");
        if (luckPermsPlugin == null) return;

        try {
            ClassLoader loader = luckPermsPlugin.getClass().getClassLoader();
            Class<?> providerClass = Class.forName("net.luckperms.api.LuckPermsProvider", true, loader);
            Object luckPerms = providerClass.getMethod("get").invoke(null);
            Object userManager = luckPerms.getClass().getMethod("getUserManager").invoke(luckPerms);
            Class<?> userManagerClass = Class.forName("net.luckperms.api.model.user.UserManager", true, loader);
            getUserMethod = userManagerClass.getMethod("getUser", UUID.class);
            Class<?> userClass = Class.forName("net.luckperms.api.model.user.User", true, loader);
            getPrimaryGroupMethod = userClass.getMethod("getPrimaryGroup");
            luckPermsUserManager = userManager;
            luckPermsAvailable = true;
        } catch (ReflectiveOperationException | LinkageError exception) {
            luckPermsAvailable = false;
            getLogger().warning("LuckPerms was found, but its API could not be accessed. Default entries will be used.");
            getLogger().debug(exception.toString());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String group = getPrimaryGroup(player);
        if (getConfig().getBoolean("ModifyJoinMessages", true)) {
            event.setJoinMessage(getConfiguredMessage("joinMessages", group, player.getName()));
        }
        playRankSound("joinSounds", group, player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String group = getPrimaryGroup(player);
        if (getConfig().getBoolean("ModifyLeaveMessages", true)) {
            event.setQuitMessage(getConfiguredMessage("leaveMessages", group, player.getName()));
        }
        playRankSound("leaveSounds", group, player);
    }

    private String getPrimaryGroup(Player player) {
        if (!luckPermsAvailable || luckPermsUserManager == null || getUserMethod == null) return "default";
        try {
            Object result = getUserMethod.invoke(luckPermsUserManager, player.getUniqueId());
            if (result == null) return "default";
            // LuckPerms UserManager#getUser returns CompletableFuture<User>.
            Object user = result instanceof java.util.concurrent.CompletableFuture<?> future
                    ? future.join() : result;
            if (user == null) return "default";
            String group = (String) getPrimaryGroupMethod.invoke(user);
            return group == null || group.isBlank() ? "default" : group.toLowerCase(Locale.ROOT);
        } catch (ReflectiveOperationException | LinkageError exception) {
            getLogger().debug("Could not read the LuckPerms group for " + player.getName() + ": " + exception);
            return "default";
        }
    }

    private String getConfiguredMessage(String section, String group, String playerName) {
        Object configured = findGroupValue(getConfig().getSection(section), group);
        return configured == null ? "" : String.valueOf(configured).replace("{name}", playerName);
    }

    private void playRankSound(String section, String group, Player subject) {
        Object entry = findGroupValue(getConfig().getSection(section), group);
        if (!(entry instanceof Map<?, ?> settings)) return;

        Object enabledValue = settings.get("enabled");
        if (enabledValue != null && !Boolean.parseBoolean(String.valueOf(enabledValue))) return;

        Object soundValue = settings.get("sound");
        if (soundValue == null) return;
        String sound = String.valueOf(soundValue).trim();
        if (sound.isEmpty()) return;

        // Play at each listener's position so everyone hears it, regardless of distance/world.
        // Exclude the player whose join/quit triggered the sound.
        for (Player listener : getServer().getOnlinePlayers().values()) {
            if (listener == null || !listener.isOnline()
                    || listener.getUniqueId().equals(subject.getUniqueId())) continue;
            try {
                listener.getLevel().addSound(listener, sound, listener);
            } catch (RuntimeException exception) {
                getLogger().debug("Could not play sound '" + sound + "' for " + listener.getName()
                        + ": " + exception.getMessage());
            }
        }
    }

    private Object findGroupValue(Map<String, Object> entries, String group) {
        if (entries == null) return null;
        for (Map.Entry<String, Object> entry : entries.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(group)) return entry.getValue();
        }
        for (Map.Entry<String, Object> entry : entries.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("default")) return entry.getValue();
        }
        return null;
    }
}
