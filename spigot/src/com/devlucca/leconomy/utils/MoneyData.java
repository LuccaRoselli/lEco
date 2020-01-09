package com.devlucca.leconomy.utils;

import org.bukkit.*;

import com.devlucca.leconomy.*;

import java.sql.*;

public class MoneyData
{
    public static void createAccount(final String nick) throws SQLException {
        final PMoney pm = new PMoney(nick);
        pm.setMoney(Main.plugin.getConfig().getDouble("Configuracoes.Money_inicial"));
        pm.save();
        Main.plugin.accounts.put(nick, pm);
        Main.plugin.value_modific.put(pm, pm.getMoney());
        Main.plugin.debug("§eConta de " + nick + " criada com sucesso.");
    }
    
    public static void loadAccounts() {
        final Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    final Connection c = Main.database.getNewConnection();
                    final Statement stmt = c.createStatement();
                    final ResultSet rs = stmt.executeQuery("SELECT * FROM " + SQL.table + "");
                    while (rs.next()) {
                        final String player = rs.getString("player");
                        final double money = Double.valueOf(rs.getString("money"));
                        final PMoney pm = new PMoney(player);
                        pm.setMoney(money);
                        Main.plugin.accounts.put(player, pm);
                        Main.plugin.value_modific.put(pm, money);
                        ++i;
                    }
                    c.close();
                    stmt.close();
                    rs.close();
                    Main.plugin.debug("Foram carregadas " + i + " contas.");
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.shutdown();
                    Main.plugin.getLogger().warning("ERRO AO CARREGAR AS CONTAS DOS JOGADORES DA DATABASE!");
                }
            }
        });
        th.start();
    }
}
