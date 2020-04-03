package pt.ubatv.kingdoms.commands.shop;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import pt.ubatv.kingdoms.Main;

public class ShopGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    private BlockGUI blockGUI = new BlockGUI();

    public ShopGUI() {
        this.inv = Bukkit.createInventory(this, 9*3, "§5Kingdoms §7Shop");
    }

    public void createGUI(Player player){
        ItemStack blocks = main.itemAPI.item(Material.GRASS_BLOCK, "§eBlocks", "§7§oClick to open menu");
        ItemStack food = main.itemAPI.item(Material.COOKED_BEEF, "§6Food", "§7§oClick to open menu");
        ItemStack farming = main.itemAPI.item(Material.WHEAT_SEEDS, "§aFarming", "§7§oClick to open menu");
        ItemStack ores = main.itemAPI.item(Material.IRON_INGOT, "§fOres", "§7§oClick to open menu");
        ItemStack mobDrops = main.itemAPI.item(Material.SPIDER_EYE, "§4Mob Drops", "§7§oClick to open menu");
        ItemStack misc = main.itemAPI.item(Material.HOPPER, "§7Misc", "§7§oClick to open menu");

        inv.setItem(4, blocks);
        inv.setItem(12, food);
        inv.setItem(14, farming);
        inv.setItem(20, ores);
        inv.setItem(22, mobDrops);
        inv.setItem(24, misc);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5Kingdoms §7Shop")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if(slot == 4) blockGUI.openInventory(player);
    }

    public void openInventory(Player player){
        createGUI(player);
        player.openInventory(inv);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }
}