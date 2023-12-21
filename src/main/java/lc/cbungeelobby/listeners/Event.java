package lc.cbungeelobby.listeners;

import lc.cbungeelobby.LCBungeeLobby;
import lc.cbungeelobby.configuration.ConfigManager;
import lc.cbungeelobby.entities.TPMode;
import lc.cbungeelobby.entities.Type;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Event implements Listener {
    private ConfigManager config = new ConfigManager();
    @EventHandler
    public void pluginchanel(PluginMessageEvent e) {
        String[] bytedata = readData(e.getData());
        byte b;
        int i;
        String[] arrayOfString1;
        for (i = (arrayOfString1 = bytedata).length, b = 0; b < i; ) {
            String line = arrayOfString1[b];
             if (line.equalsIgnoreCase("LBTP")) {
                ProxiedPlayer pp = LCBungeeLobby.getInstance().getProxy().getPlayer(e.getReceiver().toString());
                if (pp != null) {
                    if(!pp.hasPermission(config.getConfig().getString("config.commands.lobby.permission"))){
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
            b++;

        }

    }


    private String[] readData(byte[] data) {
        List<String> readed = new ArrayList();
        DataInputStream di = new DataInputStream(new ByteArrayInputStream(data));
        for (int i = 0; i < 255; i++) {
            try {
                String dr = di.readUTF();
                readed.add(dr);
            } catch (IOException e) {
                if (readed.size() != 0) {
                    String[] arrayOfString1 = readed.<String>toArray(new String[readed.size()]);
                    return arrayOfString1;
                }
                String[] arrayOfString = { new String(data, Charset.forName("UTF-8")) };
                return arrayOfString;
            }
        }
        String[] out = readed.<String>toArray(new String[readed.size()]);
        return out;
    }




}






