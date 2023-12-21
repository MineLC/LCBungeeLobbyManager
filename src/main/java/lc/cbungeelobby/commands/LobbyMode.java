package lc.cbungeelobby.commands;

import lc.bungeecore2.entities.Jugador;
import lc.cbungeelobby.LCBungeeLobby;
import lc.cbungeelobby.configuration.ConfigManager;
import lc.cbungeelobby.entities.Type;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LobbyMode extends Command {
    private ConfigurationProvider cp = ConfigurationProvider.getProvider(YamlConfiguration.class);
    private ConfigManager config = new ConfigManager();
    public LobbyMode(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(Jugador.getJugador(pp).is_Admin()){
                if(args.length <=0){
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.mode.arguments"));
                    return;
                }
                if(args[0].equalsIgnoreCase("random")|| args[0].equalsIgnoreCase("lessplayers")){
                    if(args[0].equalsIgnoreCase("random")){
                        LCBungeeLobby.getInstance().setMode(Type.RANDOM);
                    }else {
                        LCBungeeLobby.getInstance().setMode(Type.LESSPLAYERS);
                    }

                    File file = new File(LCBungeeLobby.getInstance().getDataFolder(), "config.yml");
                    try {
                        Configuration cgf = cp.load(file);
                        cgf.set("config.tp_mode", args[0].toLowerCase());
                        ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(LCBungeeLobby.getInstance().getDataFolder(), "config.yml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.lobby.mode.sucess").replaceAll("%mode%", args[0]));

                    return;
                }
                pp.sendMessage(ChatColor.GRAY + "You only can use this options: " + ChatColor.GREEN + "random , lessplayers");
            }else {
                pp.sendMessage(LCBungeeLobby.getInstance().getMessage("message.nopermission"));
            }

        }
    }
}
