package com.devlucca.leconomy.placeholders;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.devlucca.leconomy.Main;

import me.clip.placeholderapi.external.EZPlaceholderHook;

@SuppressWarnings("deprecation")
public class Normal extends EZPlaceholderHook {

    public Normal(Main ourPlugin) {
        super(ourPlugin, "leconomy");
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (identifier.equals("money")) {
            return String.valueOf(Main.plugin.format(Main.plugin.getEconomy().getBalance((OfflinePlayer)p)));
        }
        return null;
    }
}
