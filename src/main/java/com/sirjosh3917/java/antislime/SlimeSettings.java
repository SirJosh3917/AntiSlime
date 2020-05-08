package com.sirjosh3917.java.antislime;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class SlimeSettings {
    private final AntiSlime _plugin;
    private final ConfigurationSection _pluginConfiguration;

    public SlimeSettings(AntiSlime plugin, ConfigurationSection pluginConfiguration) {
        _plugin = plugin;
        _pluginConfiguration = pluginConfiguration;
    }

    public boolean shouldPreventSpawningFor(World world) {
        Object enabledValue = _pluginConfiguration.get(world.getName());

        if (enabledValue instanceof Boolean) {
            return (Boolean)enabledValue;
        }

        final boolean defaultShouldPrevent = false;

        if (enabledValue == null) {
            return defaultShouldPrevent;
        }

        Bukkit.getLogger().warning("Attempted to get if AntiSlime was enabled for world '" + world.getName() + "', " +
                "did not detect a boolean or lack of value in settings.");
        return defaultShouldPrevent;
    }

    public void setEnabled(World world, boolean isEnabled) {
        _pluginConfiguration.set(world.getName(), isEnabled);
    }

    public void save() {
        _plugin.saveConfig();
    }
}
