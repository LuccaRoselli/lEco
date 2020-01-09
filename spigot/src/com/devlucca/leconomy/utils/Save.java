package com.devlucca.leconomy.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.devlucca.leconomy.Main;
import com.devlucca.leconomy.PMoney;

public class Save implements Runnable
{
    private double last_top_update;
    
    public Save() {
        this.last_top_update = 0.0;
    }
    
    @Override
    public void run() {
        new BukkitRunnable() {
            public void run() {
                try {
                    int i = 0;
                    final Connection c = Main.database.getNewConnection();
                    final Statement stmt = c.createStatement();
                    for (final String p : Main.plugin.accounts.keySet()) {
                        final PMoney pm = Main.plugin.getPMoney(p);
                        if (Main.plugin.value_modific.containsKey(pm)) {
                            if (Main.plugin.value_modific.get(pm) == pm.getMoney()) {
                                continue;
                            }
                            stmt.executeUpdate("UPDATE " + SQL.table + " SET money='" + pm.getMoney() + "' WHERE player='" + pm.getPlayer() + "'");
                            Main.plugin.value_modific.put(pm, pm.getMoney());
                            ++i;
                        }
                        else {
                            Main.plugin.value_modific.put(pm, pm.getMoney());
                        }
                    }
                    if (Save.this.last_top_update < System.currentTimeMillis()) {
                        Main.tops = Main.plugin.getTop();
                        Save.access$1(Save.this, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5L));
                    }
                    c.close();
                    stmt.close();
                    Main.plugin.debug("Foram salvas " + i + " contas.");
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    Main.plugin.debug("Falha ao salvar as contas.");
                }
            }
        }.runTaskTimerAsynchronously((Plugin)Main.plugin, 0L, 1200L);
    }
    
    static /* synthetic */ void access$1(final Save save, final double last_top_update) {
        save.last_top_update = last_top_update;
    }
}
