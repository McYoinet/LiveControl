package net.entablemc.livecontrol;

import net.entablemc.livecontrol.sql.Connect;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;

public class LiveControl extends Plugin {

    PluginDescription pdffile = getDescription();
    public String version = pdffile.getVersion();
    public String author = pdffile.getAuthor();

    PluginUtils pluginUtils = new PluginUtils(this);
    Connect connect = new Connect(this, pluginUtils);

    @Override
    public void onEnable(){
        pluginUtils.loadConfiguration();
        pluginUtils.loadListener();
        connect.createTable();

        getLogger().info(pluginUtils.format("&7Plugin enabled."));
        getLogger().info(pluginUtils.format("&7Version: &e%version%&7."));
        getLogger().info(pluginUtils.format("&7Developed by: &e%author%&7."));
    }
}
