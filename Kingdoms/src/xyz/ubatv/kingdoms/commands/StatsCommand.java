package xyz.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.userData.UserData;
import xyz.ubatv.kingdoms.userData.UserDataManager;

public class StatsCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length >= 2){
                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "/stats §5[player]");
                return false;
            }

            if(args.length == 1){
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(main.textUtils.error + "Invalid player.");
                    return false;
                }

                sendPlayerStats(player, target);
                return false;
            }

            sendPlayerStats(player, player);
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    private void sendPlayerStats(Player player, Player target){
        UserData targetData = UserDataManager.usersData.get(target.getUniqueId());
        if(player.getName().equalsIgnoreCase(target.getName())){
            main.textUtils.sendCenteredMessage(player, "§7§m========[§5Your §7Stats§7§m]========");
        }else{
            main.textUtils.sendCenteredMessage(player, "§7§m========[§5" + target.getName() + "§7's Stats§7§m]========");
        }
        player.sendMessage(" ");
        player.sendMessage("§7Rank: §5" + main.rankManager.getServerRankName(targetData.getServerRank(), true));
        player.sendMessage("§7Kingdom: §5" + targetData.getKingdom());
        player.sendMessage("§7Balance: §5" + targetData.getCoins());
        player.sendMessage("§7Kills: " + targetData.getKills());
        player.sendMessage("§7Deaths: " + targetData.getDeaths());
    }
}
