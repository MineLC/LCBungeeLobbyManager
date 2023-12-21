package lc.cbungeelobby.commands;

import lc.cbungeelobby.LCBungeeLobby;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyCreate extends Command {
    public LobbyCreate(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission("lobby.create")|| pp.hasPermission("lobby.*")){
                ServerInfo sv = pp.getServer().getInfo();
                if(LCBungeeLobby.getInstance().getLobbys().contains(sv.getName())){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.islobby"));
                    return;
                }
                LCBungeeLobby.getInstance().getLobbys().add(sv.getName());
                LCBungeeLobby.getInstance().saveLobbysinConfig();
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.create"));
            } else {
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.nopermission"));
            }
        }
    }
}
