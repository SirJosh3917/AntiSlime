package com.sirjosh3917.java.antislime.listeners;

import com.sirjosh3917.java.antislime.SlimeSettings;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SlimeSpawnListener implements Listener {

    private final SlimeSettings _settings;

    public SlimeSpawnListener(SlimeSettings settings) {
        _settings = settings;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawnEvent(CreatureSpawnEvent spawnEvent) {
        if (spawnEvent.getEntity().getType() != EntityType.SLIME) return;

        if (_settings.shouldPreventSpawningFor(spawnEvent.getEntity().getWorld())) {
            spawnEvent.setCancelled(true);
        }
    }
}
