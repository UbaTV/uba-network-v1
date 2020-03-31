package pt.ubatv.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pt.ubatv.kingdoms.commands.SetLocationCommand;
import pt.ubatv.kingdoms.commands.SpawnCommand;
import pt.ubatv.kingdoms.configs.LocationYML;
import pt.ubatv.kingdoms.events.DeveloperMode;
import pt.ubatv.kingdoms.events.JoinQuitEvent;
import pt.ubatv.kingdoms.mysql.MySQLConnection;
import pt.ubatv.kingdoms.mysql.UserDataTable;
import pt.ubatv.kingdoms.rankSystem.RankManager;
import pt.ubatv.kingdoms.utils.ItemAPI;
import pt.ubatv.kingdoms.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;

    public TextUtils textUtils;
    public MySQLConnection mySQLConnection;
    public ItemAPI itemAPI;
    public UserDataTable userDataTable;
    public LocationYML locationYML;
    public RankManager rankManager;

    @Override
    public void onEnable() {
        setInstance(this);
        instanceClasses();

        loadConfig();
        locationYML.createConfig();
        mySQLConnection.runMySQLAsync();

        registerEvents();
        registerCommands();

        locationYML.setupSpawn();
        Bukkit.getOnlinePlayers().forEach(target -> userDataTable.loadUserData(target));
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands(){
        getCommand("setlocation").setExecutor(new SetLocationCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new DeveloperMode(), this);
        pluginManager.registerEvents(new JoinQuitEvent(), this);
    }

    private void instanceClasses(){
        textUtils = new TextUtils();
        mySQLConnection = new MySQLConnection();
        itemAPI = new ItemAPI();
        userDataTable = new UserDataTable();
        locationYML = new LocationYML();
        rankManager = new RankManager();
    }

    private void loadConfig(){
        getConfig().options().copyDefaults();
        saveConfig();
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
