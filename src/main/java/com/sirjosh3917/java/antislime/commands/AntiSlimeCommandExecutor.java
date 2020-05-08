package com.sirjosh3917.java.antislime.commands;

import com.sirjosh3917.java.antislime.SlimeSettings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AntiSlimeCommandExecutor implements CommandExecutor {

    private final String _permissionName;
    private final SlimeSettings _settings;

    public AntiSlimeCommandExecutor(String permissionName, SlimeSettings settings) {
        _permissionName = permissionName;
        _settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.YELLOW + "Sender must be a player!" + ChatColor.RESET);
            return true;
        }

        Player player = (Player)commandSender;
        if (!player.hasPermission(_permissionName)) {
            commandSender.sendMessage(ChatColor.RED + "You do not have the required permission, '" + _permissionName
                    + "'!"+ ChatColor.RESET);
            return true;
        }

        boolean newSetting;

        if (args.length == 0) {
            newSetting = _settings.shouldPreventSpawningFor(player.getWorld());
            newSetting = !newSetting;
        }
        else {
            newSetting = Boolean.parseBoolean(args[0]);
        }

        _settings.setEnabled(player.getWorld(), newSetting);
        commandSender.sendMessage(ChatColor.WHITE + "AntiSlime for world '" + player.getWorld().getName() + "' is: "
                + toFriendlyName(_settings.shouldPreventSpawningFor(player.getWorld())) + ChatColor.RESET);

        _settings.save();
        return true;
    }

    /**
     * Small helper function to ensure that the status of if slimes will spawn or not is effectively conveyed.
     * @param shouldPreventSlimeSpawning If slimes should be prevented from spawning.
     * @return A color coded {@link String} to send to the user.
     */
    private static String toFriendlyName(boolean shouldPreventSlimeSpawning) {
        if (shouldPreventSlimeSpawning) {
            return "enabled " + ChatColor.GREEN + "(Slimes will not spawn)";
        }
        else {
            return "disabled " + ChatColor.RED + "(Slimes will spawn)";
        }
    }
}
