package lc.cbungeelobby.entities;

import lc.cbungeelobby.LCBungeeLobby;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import lc.cbungeelobby.entities.LobbyPlayer;

import java.util.ArrayList;

public class TPMode {
    public static void tpRandomly(ProxiedPlayer pp) {
        ServerInfo sv = LCBungeeLobby.getRandomLobby();
        if(sv == null) {
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.error"));
            return;
        }
        if(LCBungeeLobby.getInstance().getLobbys().contains(pp.getServer().getInfo().getName())){
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.already"));
            return;
        }
        LobbyPlayer lbp = LobbyPlayer.getLobbyPlayer(pp);
            if(!LCBungeeLobby.getInstance().isRunnable()){
                if((LCBungeeLobby.getInstance().isDelay() && lbp.isSendCommand()) && (System.currentTimeMillis() - lbp.getTimecommand() <= LCBungeeLobby.getInstance().getTimetowait())){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.delay.message"));
                    return;
                }
                lbp.setTimecommand(System.currentTimeMillis());
                lbp.setSendCommand(true);
                pp.connect(sv);
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.tp"));
                return;
            }
        if(LCBungeeLobby.getInstance().isDelay() && !lbp.isWaiting() && lbp.isSendCommand() && (System.currentTimeMillis() - lbp.getTimecommand() <= LCBungeeLobby.getInstance().getTimetowait())){
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.delay.message"));
            return;
        }
        lbp.setTimecommand(System.currentTimeMillis());
        lbp.setSendCommand(true);
            if(lbp.isWaiting()){
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.cancel"));
                lbp.setWaiting(false);
                lbp.getTask().cancel();
                return;
            }
            lbp.setWaiting(true);
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.waiting").replaceAll("%time%", Integer.toString(LCBungeeLobby.getInstance().time)));
            lbp.transportWithRunnable(sv);



    }

    public static void tpLessPlayers(ProxiedPlayer pp) {
        if(LCBungeeLobby.getInstance().getLobbys().size() <=0) {
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.error"));
            return;
        }
        if(LCBungeeLobby.getInstance().getLobbys().contains(pp.getServer().getInfo().getName())){
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.already"));
            return;
        }
        ArrayList<ServerInfo>lobbylist = new ArrayList<ServerInfo>();
        for(String sv : LCBungeeLobby.getInstance().getLobbys()){
            lobbylist.add(LCBungeeLobby.getInstance().getProxy().getServerInfo(sv));
        }
        lobbylist.sort(new ShortUtil());
        /// getting the server
        ServerInfo sv = lobbylist.get(0);
        if(sv == null) {
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.error"));
            return;
        }
        LobbyPlayer lbp = LobbyPlayer.getLobbyPlayer(pp);
            if(!LCBungeeLobby.getInstance().isRunnable()){
                if((LCBungeeLobby.getInstance().isDelay() && lbp.isSendCommand()) && (System.currentTimeMillis() - lbp.getTimecommand() <= LCBungeeLobby.getInstance().getTimetowait())){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.delay.message"));
                    return;
                }
                lbp.setTimecommand(System.currentTimeMillis());
                lbp.setSendCommand(true);
                pp.connect(sv);
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.tp"));
                return;
            }
             if(LCBungeeLobby.getInstance().isDelay() && !lbp.isWaiting() && lbp.isSendCommand() && (System.currentTimeMillis() - lbp.getTimecommand() <= LCBungeeLobby.getInstance().getTimetowait())){
                 pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.delay.message"));
                 return;
             }
        lbp.setTimecommand(System.currentTimeMillis());
        lbp.setSendCommand(true);
            if(lbp.isWaiting()){
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.cancel"));
                lbp.setWaiting(false);
                lbp.getTask().cancel();
                return;
            }
            lbp.setWaiting(true);
            pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.waiting").replaceAll("%time%", Integer.toString(LCBungeeLobby.getInstance().time)));
            lbp.transportWithRunnable(sv);



    }
}
