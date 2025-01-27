package xyz.ubatv.kingdoms.commands.shop;

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
import xyz.ubatv.kingdoms.Main;

public class ShopGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    private BlockGUI blockGUI = new BlockGUI();
    private MiscGUI miscGUI = new MiscGUI();
    private MobDropsGUI mobDropsGUI = new MobDropsGUI();
    private OresGUI oresGUI = new OresGUI();
    private FoodGUI foodGUI = new FoodGUI();
    private FarmingGUI farmingGUI = new FarmingGUI();
    private RaidGUI raidGUI = new RaidGUI();

    public ShopGUI() {
        this.inv = Bukkit.createInventory(this, 9*3, "§5Kingdoms §7Shop");
    }

    public void createGUI(Player player){
        ItemStack blocks = main.itemAPI.item(Material.GRASS_BLOCK, "§eBlocks", "§7§oClick to open menu");
        ItemStack food = main.itemAPI.item(Material.COOKED_BEEF, "§6Food", "§7§oClick to open menu");
        ItemStack farming = main.itemAPI.item(Material.WHEAT_SEEDS, "§aFarming", "§7§oClick to open menu");
        ItemStack ores = main.itemAPI.item(Material.IRON_INGOT, "§fOres", "§7§oClick to open menu");
        ItemStack mobDrops = main.itemAPI.item(Material.SPIDER_EYE, "§bMob Drops", "§7§oClick to open menu");
        ItemStack misc = main.itemAPI.item(Material.HOPPER, "§7Misc", "§7§oClick to open menu");
        ItemStack raid = main.itemAPI.item(Material.TNT, "§4Raid", "§7§oClick to open menu");

        inv.setItem(10, blocks);
        inv.setItem(11, food);
        inv.setItem(12, farming);
        inv.setItem(13, raid);
        inv.setItem(14, ores);
        inv.setItem(15, mobDrops);
        inv.setItem(16, misc);
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

        if(slot == 10) blockGUI.openInventory(player);
        if(slot == 15) mobDropsGUI.openInventory(player);
        if(slot == 16) miscGUI.openInventory(player);
        if(slot == 14) oresGUI.openInventory(player);
        if(slot == 11) foodGUI.openInventory(player);
        if(slot == 12) farmingGUI.openInventory(player);
        if(slot == 13) raidGUI.openInventory(player);
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
