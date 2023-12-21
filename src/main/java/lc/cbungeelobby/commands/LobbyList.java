package lc.cbungeelobby.commands;

import lc.cbungeelobby.LCBungeeLobby;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyList extends Command {
    public LobbyList(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            pp.sendMessage(ChatColor.translateAlternateColorCodes('&', LCBungeeLobby.getInstance().getMessage("message.lobby.list.header")));
            for(String sv : LCBungeeLobby.getInstance().getLobbys()){
                ServerInfo svi = LCBungeeLobby.getInstance().getProxy().getServerInfo(sv);
                int amount = svi.getPlayers().size();
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', LCBungeeLobby.getInstance().getMessage("message.lobby.list.message")
                .replaceAll("%lobby%", sv).replaceAll("%amount%", Integer.toString(amount))));
            }
            pp.sendMessage(ChatColor.translateAlternateColorCodes('&', LCBungeeLobby.getInstance().getMessage("message.lobby.list.footer")));


        }
    }
}
