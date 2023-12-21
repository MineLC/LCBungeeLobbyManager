package lc.cbungeelobby.commands;

import lc.cbungeelobby.LCBungeeLobby;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyReload extends Command {
    public LobbyReload(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission("lobby.reload")|| pp.hasPermission("lobby.*")){
                LCBungeeLobby.getInstance().reloadConfig();

                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.reload"));
            } else{
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.nopermission"));
            }
        }
    }
}
