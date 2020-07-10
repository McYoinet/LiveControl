package net.entablemc.livecontrolspigot.sql;

import net.entablemc.livecontrolspigot.LiveControlSpigot;
import net.entablemc.livecontrolspigot.PluginUtils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class Connect {
    private LiveControlSpigot plugin;
    private PluginUtils pluginUtils;

    public Connect(LiveControlSpigot plugin, PluginUtils pluginUtils){
        this.plugin = plugin;
        this.pluginUtils = pluginUtils;
    }

    public Connection con;

    public void connect() {
        String host = pluginUtils.host.getString("hostname");
        int port = pluginUtils.host.getInt("port");
        String user = pluginUtils.host.getString("username");
        String password = pluginUtils.host.getString("password");
        String database = pluginUtils.host.getString("database");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" +
                    database, user, password);

            }catch(Exception e){

            List<String> messages = pluginUtils.messages.getStringList("messages.errors.connection");
            for(String index : messages) {
                Bukkit.getConsoleSender().sendMessage(pluginUtils.format(index));
            }
            Bukkit.getConsoleSender().sendMessage(pluginUtils.format("&4" + e.toString()));
        }
    }
}
