package lc.cbungeelobby.commands;

import lc.cbungeelobby.LCBungeeLobby;
import lc.cbungeelobby.entities.TPMode;
import lc.cbungeelobby.entities.Type;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;


public class Lobby extends Command {
    public Lobby(String name, String permission, String[] aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(!pp.hasPermission(this.getPermission())){
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.nopermission"));
                return;
            }
            if(LCBungeeLobby.getInstance().getRlobbys().contains(pp.getServer().getInfo().getName())){
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.denied"));
                return;
            }
            if(LCBungeeLobby.getInstance().getMode() == Type.RANDOM) {
                TPMode.tpRandomly(pp);
            }else {
                TPMode.tpLessPlayers(pp);
            }

        }
    }

}
