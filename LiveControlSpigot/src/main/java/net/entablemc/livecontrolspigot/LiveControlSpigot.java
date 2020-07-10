package net.entablemc.livecontrolspigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class LiveControlSpigot extends JavaPlugin {

    PluginDescriptionFile pdffile = getDescription();
    public String version = pdffile.getVersion();
    public String author = "McYoinet";

    PluginUtils pluginUtils = new PluginUtils(this);

    @Override
    public void onEnable(){
        pluginUtils.loadConfiguration();
        pluginUtils.loadEvents();

        Bukkit.getConsoleSender().sendMessage(pluginUtils.format("&f[&9LiveControlSpigot&f] &7Plugin enabled."));
        Bukkit.getConsoleSender().sendMessage(pluginUtils.format("&f[&9LiveControlSpigot&f] &7Version: &e%version%&7."));
        Bukkit.getConsoleSender().sendMessage(pluginUtils.format("&f[&9LiveControlSpigot&f] &7Developed by: &e%author%&7."));
    }

    @Override
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(pluginUtils.format("&f[&9LiveControlSpigot&f] &7Plugin disabled."));
        Bukkit.getConsoleSender().sendMessage(pluginUtils.format("&f[&9LiveControlSpigot&f] &7Version: &e%version%&7."));
        Bukkit.getConsoleSender().sendMessage(pluginUtils.format("&f[&9LiveControlSpigot&f] &7Developed by: &e%author%&7."));
    }
}
