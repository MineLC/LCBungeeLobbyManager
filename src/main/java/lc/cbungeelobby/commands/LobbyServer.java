package lc.cbungeelobby.commands;

import lc.bungeecore2.entities.Jugador;
import lc.cbungeelobby.LCBungeeLobby;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyServer extends Command {
    public LobbyServer(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if (Jugador.getJugador(pp).is_Admin()) {
                if (args.length <= 0) {
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.server.arguments"));
                    return;
                }
                ServerInfo sv = LCBungeeLobby.getInstance().getProxy().getServerInfo(args[0]);
                if(sv == null){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.server.error"));
                    return;
                }
                if(LCBungeeLobby.getInstance().getLobbys().contains(sv.getName())){
                   pp.connect(sv);
                   pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.server.sucess").replaceAll("%server%", sv.getName()));
                }else {
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.server.error"));
                }

                return;
            }
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.nopermission"));

        }
    }
}
