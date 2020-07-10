package net.entablemc.livecontrol;

import net.entablemc.livecontrol.listener.Join;
import net.entablemc.livecontrol.listener.Leave;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class PluginUtils {

    private LiveControl plugin;

    public PluginUtils(LiveControl plugin){
        PluginUtils.this.plugin = plugin;
    }

    public String format(String text){
        if(text.contains("%version%")) text = text.replaceAll("%version%", plugin.version);
        if(text.contains("%author%")) text = text.replaceAll("%author%", plugin.author);
        if(text.contains("%prefix%")) text = text.replaceAll("%prefix%", messages.getString("messages.prefix"));

        text = ChatColor.translateAlternateColorCodes('&', text);
        return text;
    }

    public Configuration messages;
    public Configuration host;

    public void loadConfiguration(){
        if(!plugin.getDataFolder().exists()) //noinspection ResultOfMethodCallIgnored
            plugin.getDataFolder().mkdir();

        File filemessages = new File(plugin.getDataFolder(), "messages.yml");

        if(!filemessages.exists()){
            try(InputStream original = plugin.getResourceAsStream("messages.yml")){
                Files.copy(original, filemessages.toPath());
            }catch(IOException e){
                plugin.getLogger().info(format("&4Couldn't load configuration file 'messages.yml':"));
                plugin.getLogger().info(format("&4" + e.toString()));
            }
        }

        try {
            messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "messages.yml"));
        } catch (IOException e) {
            plugin.getLogger().info(format("&4Couldn't load configuration file 'messages.yml':"));
            plugin.getLogger().info(format("&4" + e.toString()));
        }

        File filehost = new File(plugin.getDataFolder(), "host.yml");

        if(!filehost.exists()){
            try(InputStream original = plugin.getResourceAsStream("host.yml")){
                Files.copy(original, filehost.toPath());
            }catch(IOException e){
                plugin.getLogger().info(format("&4Couldn't load configuration file 'host.yml':"));
                plugin.getLogger().info(format("&4" + e.toString()));
            }
        }

        try {
            host = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "host.yml"));
        } catch (IOException e) {
            plugin.getLogger().info(format("&4Couldn't load configuration file 'host.yml':"));
            plugin.getLogger().info(format("&4" + e.toString()));
        }
    }

    public void loadListener(){
        plugin.getProxy().getPluginManager().registerListener(plugin, new Join(this, plugin));
        plugin.getProxy().getPluginManager().registerListener(plugin, new Leave(plugin, this));
    }
}
