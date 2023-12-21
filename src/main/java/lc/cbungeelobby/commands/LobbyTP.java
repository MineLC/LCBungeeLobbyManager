package lc.cbungeelobby.commands;

import lc.bungeecore2.entities.Jugador;
import lc.cbungeelobby.LCBungeeLobby;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyTP extends Command {
    public LobbyTP(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if (Jugador.getJugador(pp).is_MODERADOR()) {
                if(args.length == 0){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.teleport.arguments"));
                    return;
                }
                ProxiedPlayer target = LCBungeeLobby.getInstance().getProxy().getPlayer(args[0]);
                if(target == null){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.teleport.error"));
                    return;
                }
                ServerInfo nsv = LCBungeeLobby.getRandomLobby();
                if(LCBungeeLobby.getInstance().getLobbys().contains(pp.getServer().getInfo().getName())){
                    target.connect(pp.getServer().getInfo());
                }else {
                    target.connect(nsv);
                }
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.teleport.sucess")
                .replaceAll("%player%" , target.getName())
                .replaceAll("%server%", nsv.getName()));
                target.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.teleport.player"));
                return;
            }
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.nopermission"));


        }
    }
}
