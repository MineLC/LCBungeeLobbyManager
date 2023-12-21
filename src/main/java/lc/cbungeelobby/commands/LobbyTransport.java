package lc.cbungeelobby.commands;

import lc.bungeecore2.entities.Jugador;
import lc.cbungeelobby.LCBungeeLobby;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyTransport extends Command {
    public LobbyTransport(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(Jugador.getJugador(pp).is_Admin()){
                if(args.length <=1){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.transport.arguments"));
                    return;
                }
                ServerInfo target = LCBungeeLobby.getInstance().getProxy().getServerInfo(args[0]);
                ServerInfo teleport = LCBungeeLobby.getInstance().getProxy().getServerInfo(args[1]);
                if(!LCBungeeLobby.getInstance().getLobbys().contains(args[0])){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.transport.error").replaceAll("%server%", args[0]));
                    return;
                }
                if(!LCBungeeLobby.getInstance().getLobbys().contains(args[1])){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.transport.error").replaceAll("%server%", args[1]));
                    return;
                }

                if(args[0].equals(args[1])){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.transport.equals"));
                    return;
                }
                for(ProxiedPlayer ppall : target.getPlayers()){
                    ppall.connect(teleport);
                }
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.transport.sucess").replaceAll("%target_amount%", Integer.toString(target.getPlayers().size()))
                .replaceAll("%target%", target.getName())
                .replaceAll("%objetive%", teleport.getName()));



            } else {
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.nopermission"));
            }
        }
    }
}
