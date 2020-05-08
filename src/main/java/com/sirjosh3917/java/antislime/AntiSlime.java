package com.sirjosh3917.java.antislime;

import com.sirjosh3917.java.antislime.commands.AntiSlimeCommandExecutor;
import com.sirjosh3917.java.antislime.listeners.SlimeSpawnListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AntiSlime extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        Bukkit.getLogger().info("Loading default configuration file...");
        YamlConfiguration defaultConfiguration;
        try {
            defaultConfiguration = loadDefaultConfig();
        } catch (IOException ioException) {
            Bukkit.getLogger().severe("Unable to load default configuration file.");
            Bukkit.getLogger().throwing("com.sirjosh3917.java.antislime.AntiSlime", "onEnable", ioException);
            return;
        }

        Bukkit.getLogger().info("Loading permissions...");
        ConfigurationSection settingsConfiguration = this.getConfig().getConfigurationSection("settings");
        String antislimePermissionName = getPermissionName(settingsConfiguration, defaultConfiguration);

        Bukkit.getLogger().info("Loading settings...");
        ConfigurationSection slimeSettingsConfiguration = this.getConfig().getConfigurationSection("worlds");

        if (slimeSettingsConfiguration == null) {
            slimeSettingsConfiguration = this.getConfig().createSection("worlds");
        }

        assert slimeSettingsConfiguration != null;
        SlimeSettings settings = new SlimeSettings(this, slimeSettingsConfiguration);

        Bukkit.getLogger().info("Setting command executors...");
        this.getCommand("antislime").setExecutor(new AntiSlimeCommandExecutor(antislimePermissionName, settings));

        Bukkit.getLogger().info("Setting command listeners...");
        this.getServer().getPluginManager().registerEvents(new SlimeSpawnListener(settings), this);

        Bukkit.getLogger().info("AntiSlime initialized!");
        Bukkit.getLogger().info("AntiSlime permission name: " + antislimePermissionName);
        // TODO: log the worlds it's enabled/disabled in
    }

    private String getPermissionName(ConfigurationSection section, YamlConfiguration defaultConfiguration) {
        Object result = section.get("permission_name");

        if (result instanceof String) {
            return (String)result;
        }

        if (result == null && defaultConfiguration != null) {
            // we innocently assume default settings are correct
            return (String)defaultConfiguration.getConfigurationSection("settings")
                    .get("permission_name");
        }

        Bukkit.getLogger().warning("Cannot determine what permission name for AntiSlime is. Falling back to default," +
                "'antislime'");
        return "antislime";
    }

    private YamlConfiguration loadDefaultConfig() throws IOException {
        try (InputStream defaultConfigurationStream = this.getResource("config.yml")) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(defaultConfigurationStream)) {
                return YamlConfiguration.loadConfiguration(inputStreamReader);
            }
        }
    }
}
