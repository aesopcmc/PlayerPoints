package org.black_ixx.playerpoints.commands;

import dev.rosewood.rosegarden.utils.StringPlaceholders;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.manager.CommandManager;
import org.black_ixx.playerpoints.manager.LocaleManager;
import org.black_ixx.playerpoints.util.PointsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RwGiveCommand extends PointsCommand {

    public RwGiveCommand() {
        super("rwgive", CommandManager.CommandAliases.RWGIVE);
    }

    @Override
    public void execute(PlayerPoints plugin, CommandSender sender, String[] args) {
        LocaleManager localeManager = plugin.getManager(LocaleManager.class);
        if (args.length < 1) {
            localeManager.sendMessage(sender, "command-give-usage");
            return;
        }
        Player player = (Player) sender;
        UUID uniqueId = player.getUniqueId();

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            localeManager.sendMessage(sender, "invalid-amount");
            return;
        }

        if (amount <= 0) {
            localeManager.sendMessage(sender, "invalid-amount");
            return;
        }

        if (plugin.getAPI().give(uniqueId, amount)) {
            // Send message to receiver
            //Player onlinePlayer = Bukkit.getPlayer(uniqueId);
            localeManager.sendMessage(player, "command-rwgive-received", StringPlaceholders.builder("amount", PointsUtils.formatPoints(amount))
                    .addPlaceholder("currency", localeManager.getCurrencyName(amount))
                    .build());

            // Send message to sender
            //localeManager.sendMessage(sender, "command-give-success", StringPlaceholders.builder("amount", PointsUtils.formatPoints(amount))
            //        .addPlaceholder("currency", localeManager.getCurrencyName(amount))
            //        .addPlaceholder("player", player.getSecond())
            //        .build());
        }
    }

    @Override
    public List<String> tabComplete(PlayerPoints plugin, CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("<amount>");
        } else {
            return Collections.emptyList();
        }
    }

}
