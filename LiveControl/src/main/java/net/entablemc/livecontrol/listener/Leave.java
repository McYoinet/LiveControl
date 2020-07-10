package net.entablemc.livecontrol.listener;

import net.entablemc.livecontrol.LiveControl;
import net.entablemc.livecontrol.PluginUtils;
import net.entablemc.livecontrol.sql.Connect;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Leave implements Listener {

    private LiveControl plugin;
    private PluginUtils pluginUtils;

    public Leave(LiveControl plugin, PluginUtils pluginUtils){
        this.plugin = plugin;
        this.pluginUtils = pluginUtils;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event){
        try{
            Connect connect = new Connect(plugin, pluginUtils);

            connect.connect();

            Statement stmt = connect.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Player FROM players");

            ArrayList<String> players = new ArrayList<>();

            while(rs.next()){
                players.add(rs.getString(1));
            }

            rs.close();

            if(players.contains(event.getPlayer().getName())){
                Statement stmtremove = connect.con.createStatement();
                stmtremove.execute("DELETE FROM players WHERE player='" + event.getPlayer().getName() + "';");
            }
            connect.con.close();
        }catch(SQLException e){
            List<String> messages = pluginUtils.messages.getStringList("messages.errors.sql-exception");
            for(String index : messages){
                plugin.getLogger().info(pluginUtils.format(index));
            }
            plugin.getLogger().info(pluginUtils.format("&4" + e.toString()));
        }

    }
}
