package lc.cbungeelobby.commands;

import lc.cbungeelobby.LCBungeeLobby;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyRemove extends Command {
    public LobbyRemove(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission("lobby.remove")||pp.hasPermission("lobby.*")) {
                if(args.length <=0){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.remove.arguments"));
                    return;
                }
                ServerInfo sv = LCBungeeLobby.getInstance().getProxy().getServerInfo(args[0]);
                if(LCBungeeLobby.getInstance().getLobbys().contains(sv.getName())){
                    LCBungeeLobby.getInstance().getLobbys().remove(sv.getName());
                    LCBungeeLobby.getInstance().saveLobbysinConfig();
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.remove.message"));
                }else{
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.remove.error"));
                }

            }else{
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.nopermission"));
            }

        }
    }
}
