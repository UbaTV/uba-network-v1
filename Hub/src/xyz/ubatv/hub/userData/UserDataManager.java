package xyz.ubatv.hub.userData;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Rank;

import java.util.HashMap;
import java.util.UUID;

public class UserDataManager implements PluginMessageListener {

    private Main main = Main.getInstance();

    public HashMap<UUID, UserData> userData = new HashMap<>();

    public void loadUserData(Player player){
        UUID uuid = player.getUniqueId();
        userData.put(uuid,
                new UserData(main.mainUserData.getRank(uuid)));
    }

    public void saveUserData(Player player){
        UUID uuid = player.getUniqueId();
        UserData userData = this.userData.get(uuid);

        main.mainUserData.updateRank(uuid, userData.getRank());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(!channel.equalsIgnoreCase("ubanetwork:userdata")) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
        String subChannel = input.readUTF();
        if(subChannel.equalsIgnoreCase("RANK_CHANGE")){
            Player target = Bukkit.getServer().getPlayer(input.readUTF());
            Rank rank = Rank.valueOf(input.readUTF().toUpperCase());
            UserData userData = this.userData.get(target.getUniqueId());
            userData.setRank(rank);
            target.setPlayerListName("§7[" + main.rankManager.getRankName(rank, true) + "§7] §7" + target.getName());
        }
    }
}