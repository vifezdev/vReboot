package lol.vifez.vreboot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class vReboot extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("vReboot plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("vReboot plugin disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reboot")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /reboot <countdownInSeconds>");
                return false;
            }
            int countdown;
            try {
                countdown = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid number format for countdown time!");
                return false;
            }
            if (countdown <= 0) {
                sender.sendMessage(ChatColor.RED + "Countdown time must be a positive integer!");
                return false;
            }
            announceCountdown(countdown);
            scheduleReboot(countdown);
            return true;
        }
        return false;
    }

    private void announceCountdown(int countdown) {
        Bukkit.broadcastMessage(ChatColor.GREEN + "Server will reboot in " + countdown + " seconds!");
    }

    private void scheduleReboot(int countdown) {
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.broadcastMessage(ChatColor.RED + "Server is rebooting now!");
            Bukkit.shutdown();
        }, countdown * 20);
    }
}
