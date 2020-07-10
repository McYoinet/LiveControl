package net.entablemc.livecontrol.sql;

import net.entablemc.livecontrol.LiveControl;
import net.entablemc.livecontrol.PluginUtils;

import java.sql.*;
import java.util.List;

public class Connect {

    private LiveControl plugin;
    private PluginUtils pluginUtils;

    public Connect(LiveControl plugin, PluginUtils pluginUtils){
        this.plugin = plugin;
        this.pluginUtils = pluginUtils;
    }

    public Connection con;

    public void connect(){
        final String host = pluginUtils.host.getString("hostname");
        final int port = pluginUtils.host.getInt("port");
        final String user = pluginUtils.host.getString("username");
        final String password = pluginUtils.host.getString("password");
        final String database = pluginUtils.host.getString("database");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" +
                    database, user, password);
        }catch(Exception e){
            List<String> messages = pluginUtils.messages.getStringList("messages.errors.connection");
            for(String index : messages){
                plugin.getLogger().info(pluginUtils.format(index));
            }
            plugin.getLogger().info(pluginUtils.format("&4" + e.toString()));
        }
    }

    public void createTable(){
        connect();
        try {
            con.createStatement().execute("CREATE TABLE IF NOT EXISTS players(Player varchar(20))");
            con.close();
        } catch (SQLException throwables) {
            List<String> messages = pluginUtils.messages.getStringList("messages.errors.sql-exception");
            for(String index : messages){
                plugin.getLogger().info(pluginUtils.format(index));
            }
            plugin.getLogger().info(pluginUtils.format("&4" + throwables.toString()));
        }
    }
}
