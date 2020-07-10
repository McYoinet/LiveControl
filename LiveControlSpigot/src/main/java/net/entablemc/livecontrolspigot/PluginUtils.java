package net.entablemc.livecontrolspigot;

import com.google.common.io.Files;
import net.entablemc.livecontrolspigot.listener.Join;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PluginUtils {

    private LiveControlSpigot plugin;

    public PluginUtils(LiveControlSpigot plugin){
        this.plugin = plugin;
    }

    public String format(String text){
        if(text.contains("%version%")) text = text.replaceAll("%version%", plugin.version);
        if(text.contains("%author$")) text = text.replaceAll("%author%", plugin.author);
        if(text.contains("%prefix%")) text = text.replaceAll("%prefix%", messages.getString("messages.prefix"));

        text = ChatColor.translateAlternateColorCodes('&', text);
        return text;
    }

    public FileConfiguration messages;
    public FileConfiguration host;

    public void loadConfiguration(){
        if(!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();

        File filemessages = new File(plugin.getDataFolder(), "messages.yml");

        if(!filemessages.exists()){
            filemessages.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        messages = new YamlConfiguration();

        try{
            messages.load(filemessages);
        }catch(IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage(format("&f[&9LiveControlSpigot&f] &cCouldn't create configuration file 'messages.yml':"));
            Bukkit.getConsoleSender().sendMessage(format("&4" + e.toString()));
        }

        File filehost = new File(plugin.getDataFolder(), "host.yml");

        if(!filehost.exists()){
            filehost.getParentFile().mkdirs();
            plugin.saveResource("host.yml", false);
        }

        host = new YamlConfiguration();

        try{
            host.load(filehost);
        }catch(IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage(format("&f[&9LiveControlSpigot&f] &cCouldn't create configuration file 'host.yml':"));
            Bukkit.getConsoleSender().sendMessage(format("&4" + e.toString()));
        }

    }

    public void loadEvents(){
        plugin.getServer().getPluginManager().registerEvents(new Join(plugin, this), plugin);
    }
}
