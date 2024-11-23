package me.kermx.prismaUtils.Commands.OtherCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage("§cYou must specify a player name or use \"all\" when using this command from the console!");
            return true;
        }

        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("prismautils.command.feed")) {
                    player.sendMessage("§cYou don't have permission to use this command!");
                    return true;
                }
                feedPlayer(player);
                player.sendMessage("§aYour hunger has been refilled!");
            }
            return true;
        }

        if (args.length == 1) {
            String targetName = args[0];
            if (targetName.equalsIgnoreCase("all")) {
                if (!sender.hasPermission("prismautils.command.feed.all")) {
                    sender.sendMessage("§cYou don't have permission to feed all players!");
                    return true;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    feedPlayer(player);
                }
                sender.sendMessage("§aAll players' hunger has been refilled!");
            } else {
                Player target = Bukkit.getPlayerExact(targetName);
                if (target == null) {
                    sender.sendMessage("§cPlayer \"" + targetName + "\" is not online!");
                    return true;
                }
                if (!sender.hasPermission("prismautils.command.feed.others")) {
                    sender.sendMessage("§cYou don't have permission to feed other players!");
                    return true;
                }
                feedPlayer(target);
                sender.sendMessage("§aYou have refilled " + target.getName() + "'s hunger!");
                target.sendMessage("§aYour hunger has been refilled by " + sender.getName() + "!");
            }
            return true;
        }

        sender.sendMessage("§cUsage: /feed [player|all]");
        return true;
    }

    private void feedPlayer(Player player) {
        player.setFoodLevel(20);
        player.setSaturation(20.0F); // Max saturation
    }
}
