# LiveControl
Security Spigot and BungeeCord plugin.

This plugin is made to improve the security in Minecraft networks.
When a player connects through the proxy, the plugin will add this player to a MySQL Database.
When that player connects to the spigot server, the plugin will make a request to the database and will check if the player exists there.
If the player does not exist in the database, it means that he didn't connected through the proxy, and he will be kicked from the server.

Right now, the plugin is in beta version, so you could find issues.
