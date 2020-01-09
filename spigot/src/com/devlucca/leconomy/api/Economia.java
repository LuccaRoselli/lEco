package com.devlucca.leconomy.api;

import org.bukkit.plugin.*;

import com.devlucca.leconomy.*;

public class Economia
{
    private Plugin plugin;
    
    public Economia(final Plugin plugin) {
        this.plugin = plugin;
        Main.plugin.debug("API sincronizada com " + plugin.getName() + " v" + plugin.getDescription().getVersion());
    }
    
    public Plugin getPlugin() {
        return this.plugin;
    }
    
    public double getBalance(final String player) {
        final PMoney p = Main.plugin.getPMoney(player);
        if (p != null) {
            return p.getMoney();
        }
        return 0.0;
    }
    
    public PMoney getPlayerEconomy(final String player) {
        return Main.plugin.getPMoney(player);
    }
    
    public Plugin getInstance() {
        return (Plugin)Main.plugin;
    }
}
