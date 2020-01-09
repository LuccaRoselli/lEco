package com.devlucca.leconomy;

import org.bukkit.*;

import com.devlucca.leconomy.utils.SQL;

import java.sql.*;

public class PMoney
{
    private String player;
    private double money;
    
    public PMoney(final String player) {
        this.player = "";
        this.money = 0.0;
        this.player = player;
    }
    
    public void loadData() {
        try {
            final Connection c = Main.database.getNewConnection();
            final Statement stmt = c.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT money FROM " + SQL.table + " WHERE player='" + this.player + "'");
            if (rs.next()) {
                this.money = Double.parseDouble(rs.getString("money"));
                Main.plugin.debug("Money de " + this.player + " retornado da database.");
            }
            c.close();
            stmt.close();
            rs.close();
        }
        catch (SQLException e) {
            Bukkit.getPlayer(this.player).kickPlayer("§cOcorreu um erro interno, tente relogar.");
        }
    }
    
    public double getMoney() {
        return this.money;
    }
    
    public String getPlayer() {
        return this.player;
    }
    
    public void setMoney(final double value) {
        this.money = value;
    }
    
    public void save() throws SQLException {
        final Connection c = Main.database.getNewConnection();
        final Statement stmt = c.createStatement();
        final ResultSet rs = stmt.executeQuery("SELECT player FROM " + SQL.table + " WHERE player='" + this.player + "'");
        if (rs.next()) {
            Main.database.update(String.valueOf(this.money), this.player);
        }
        else {
            Main.database.insert(String.valueOf(this.money), this.player);
        }
        c.close();
        stmt.close();
        rs.close();
        Main.plugin.debug("Money de " + this.player + " salvo.");
    }
}
