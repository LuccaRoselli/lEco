package com.devlucca.leconomy.listeners;

import org.bukkit.event.player.*;

import com.devlucca.leconomy.*;
import com.devlucca.leconomy.utils.*;

import java.sql.*;
import org.bukkit.event.*;

public class PlayerJoin implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(final PlayerJoinEvent e) {
        if (Main.plugin.getPMoney(e.getPlayer().getName()) == null) {
        	if (Main.plugin.getPMoney(e.getPlayer().getName().toLowerCase()) != null){
                try {
                    final Connection c = Main.database.getNewConnection();
                    final Statement stmt = c.createStatement();
                    double money = Main.plugin.getPMoney(e.getPlayer().getName().toLowerCase()).getMoney();
                    stmt.executeUpdate("DELETE FROM " + SQL.table + " WHERE player='" + e.getPlayer().getName().toLowerCase() + "'");
            		if (Main.plugin.accounts.containsKey(e.getPlayer().getName().toLowerCase())) 
            			 Main.plugin.accounts.remove(e.getPlayer().getName().toLowerCase());
                    final PMoney pm = new PMoney(e.getPlayer().getName());
                    pm.setMoney(money);
                    pm.save();
                    Main.plugin.accounts.put(e.getPlayer().getName(), pm);
                    Main.plugin.value_modific.put(pm, pm.getMoney());
                    Main.plugin.debug("Conta de " + e.getPlayer().getName() + " atualizada!");
                } catch (SQLException e1) {
                    e.getPlayer().kickPlayer("§cOcorreu um problema interno, tente relogar!");
                    e1.printStackTrace();
				}
                return;
        	}
            try {
                MoneyData.createAccount(e.getPlayer().getName());
            }
            catch (SQLException e2) {
                e.getPlayer().kickPlayer("§cOcorreu um problema interno, tente relogar!");
                e2.printStackTrace();
            }
        }
    }
}
