package pt.ubatv.kingdoms.commands.shop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Permissions;

public class ShopNPCCommand implements CommandExecutor, Listener {

    public Main main = Main.getInstance();

    private String npcName = "§7Kingdom §5§lShop";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.SHOP_NPC)){
                Location loc = player.getLocation();
                Villager shop = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
                shop.setProfession(Villager.Profession.LIBRARIAN);
                shop.setCustomName(npcName);
                shop.setCustomNameVisible(true);
                shop.setAI(false);
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if(entity.getCustomName().equalsIgnoreCase(npcName)){
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(PlayerInteractEntityEvent event){
        Entity entity = event.getRightClicked();
        if(entity.getCustomName().equalsIgnoreCase(npcName)){
            Player player = event.getPlayer();
            event.setCancelled(true);
            player.performCommand("shop");
        }
    }
}
