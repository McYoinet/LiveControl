package net.entablemc.livecontrol.listener;

import net.entablemc.livecontrol.LiveControl;
import net.entablemc.livecontrol.PluginUtils;
import net.entablemc.livecontrol.sql.Connect;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Join implements Listener {

    private PluginUtils pluginUtils;
    private LiveControl plugin;

    public Join(PluginUtils pluginUtils, LiveControl plugin){
        this.pluginUtils = pluginUtils;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event){
        Connect connect = new Connect(plugin, pluginUtils);

        connect.connect();
        try {
            Statement stmt = connect.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Player FROM players");

            ArrayList<String> players = new ArrayList<>();

            while(rs.next()){
                players.add(rs.getString(1));
            }

            rs.close();

            if(players.contains(event.getPlayer().getName())){
                TextComponent message = new TextComponent();
                message.setText(pluginUtils.messages.getString("messages.errors.player-already-connected"));
                event.getPlayer().disconnect(message);
            }else{
                Statement addstmt = connect.con.createStatement();
                addstmt.execute("INSERT INTO players(Player) VALUES ('" +
                        event.getPlayer().getName() + "')");
                players.clear();
                connect.con.close();
            }

        } catch (SQLException throwables) {
            List<String> messages = pluginUtils.messages.getStringList("messages.errors.sql-exception");
            for(String index : messages){
                plugin.getLogger().info(pluginUtils.format(index));
            }
            plugin.getLogger().info(pluginUtils.format("&4" + throwables.toString()));
        }
    }
}
