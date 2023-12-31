package lc.cbungeelobby.entities;

import lc.cbungeelobby.LCBungeeLobby;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class LobbyPlayer {
    private ProxiedPlayer pp;
    private boolean waiting = false;
    private ScheduledTask task;
    public static HashMap<ProxiedPlayer, LobbyPlayer> lobbyPlayer = new HashMap<ProxiedPlayer, LobbyPlayer>();
    private Integer time = 0;
    private boolean sendCommand = false;
    private Long timecommand;
    public LobbyPlayer(ProxiedPlayer pp) {
        this.pp = pp;
        lobbyPlayer.put(pp, this);
    }

    public static LobbyPlayer getLobbyPlayer(ProxiedPlayer pp){
        if(lobbyPlayer.containsKey(pp)){
            return lobbyPlayer.get(pp);
        }
        return new LobbyPlayer(pp);
    }

    public ScheduledTask getTask(){
        return this.task;
    }

    public void transportWithRunnable(ServerInfo sv){
        time = 0;
        task = LCBungeeLobby.getInstance().getProxy().getScheduler().schedule((Plugin) LCBungeeLobby.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(time > LCBungeeLobby.getInstance().time){
                    pp.connect(sv);
                    waiting = false;
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.tp"));
                    time = 0;
                    task.cancel();
                    sendCommand = false;
                }
                time++;
            }
        },0,1, TimeUnit.SECONDS);

    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public boolean isSendCommand() {
        return sendCommand;
    }

    public void setSendCommand(boolean sendCommand) {
        this.sendCommand = sendCommand;
    }

    public Long getTimecommand() {
        return timecommand;
    }

    public void setTimecommand(Long timecommand) {
        this.timecommand = timecommand;
    }
}
