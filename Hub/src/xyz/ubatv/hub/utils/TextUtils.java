package xyz.ubatv.hub.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TextUtils {

    private final static int CENTER_PX = 154;

    public String serverName = "§5UbaNetwork";
    public String website = "ubatv.xyz";
    public String serverIP = "play.ubatv.xyz";

    public String right = "§a✓ §7";
    public String warning = "§e◬ §7";
    public String error = "§c◉ §7";

    public char coinsSymbol = '⛃';

    public String playerOnly = "Only players can execute this command";
    public String noPerms = error + "You don't have permission to execute this command.";

    public String listToString(String[] list){
        StringBuilder str = new StringBuilder();
        for(Object o : list){
            str.append(o).append("#");
        }
        return str.toString();
    }

    public String secondsToText(int seconds){
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int s = seconds % 60;

        return "§5" + hours + "§7:§5" + minutes + "§7:§5" + s;
    }

    public String[] stringToList(String str){
        return str.split("#");
    }

    public String getFirstSplit(String str){
        return str.length() > 16 ? str.substring(0, 16) : str;
    }

    public String getSecondSplit(String str){
        if(str.length() > 32){
            str = str.substring(0, 32);
        }
        return str.length() > 16 ? str.substring(16) : "";
    }

    public void sendCenteredMessage(Player player, String message){
        if(message == null || message.equals("")) player.sendMessage("");
        assert message != null;
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
            }else if(previousCode){
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }
}
