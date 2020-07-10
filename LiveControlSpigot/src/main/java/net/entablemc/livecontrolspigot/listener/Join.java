package net.entablemc.livecontrolspigot.listener;

import net.entablemc.livecontrolspigot.LiveControlSpigot;
import net.entablemc.livecontrolspigot.PluginUtils;
import net.entablemc.livecontrolspigot.sql.Connect;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Join implements Listener {

    private LiveControlSpigot plugin;
    private PluginUtils pluginUtils;

    public Join(LiveControlSpigot plugin, PluginUtils pluginUtils){
        this.plugin = plugin;
        this.pluginUtils = pluginUtils;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Connect connect = new Connect(plugin, pluginUtils);

        connect.connect();

        try{
            Statement stmt = connect.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT player FROM players");

            ArrayList<String> players = new ArrayList<>();

            while(rs.next()){
                players.add(rs.getString(1));
            }
            rs.close();

            if(!players.contains(event.getPlayer().getName())){
                event.getPlayer().kickPlayer(pluginUtils.messages.getString("messages.errors.connect-from-bungeecord"));
            }
        }catch(SQLException ex){
            List<String> messages = pluginUtils.messages.getStringList("messages.errors.sql-exception");
            for(String index : messages){
                Bukkit.getConsoleSender().sendMessage(pluginUtils.format(index));
            }
            Bukkit.getConsoleSender().sendMessage(pluginUtils.format("&4" + ex.toString()));
        }
    }
}
