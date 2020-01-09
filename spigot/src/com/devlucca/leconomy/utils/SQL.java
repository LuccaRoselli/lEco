package com.devlucca.leconomy.utils;

import java.sql.*;

import com.devlucca.leconomy.Main;

public class SQL
{
    public static String table;
    
    public SQL() {
        table = "Money";
        try {
           
            Connection c = null;
            if (!Main.plugin.getConfig().getBoolean("MySQL.ativo")){
            	Class.forName("org.sqlite.JDBC");
            	c = DriverManager.getConnection("jdbc:sqlite:plugins/lEconomy/database.db");
            } else {
            	Class.forName("com.mysql.jdbc.Driver");
            	String ip = Main.plugin.getConfig().getString("MySQL.ip");
            	int porta = Main.plugin.getConfig().getInt("MySQL.porta");
            	String usuario = Main.plugin.getConfig().getString("MySQL.usuario");
            	String senha = Main.plugin.getConfig().getString("MySQL.senha");
            	String database = Main.plugin.getConfig().getString("MySQL.database");
            	c = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + porta + "/" + database, usuario, senha);
            }
            final Statement stmt = c.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS " + table + " (player TEXT, money TEXT)");
            Main.plugin.debug("Conex\u00e3o ao SQL estabelecida.");
            c.close();
            stmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Connection getNewConnection() throws SQLException {
        Connection c = null;
        if (!Main.plugin.getConfig().getBoolean("MySQL.ativo")){
        	try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	c = DriverManager.getConnection("jdbc:sqlite:plugins/lEconomy/database.db");
        } else {
        	try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	String ip = Main.plugin.getConfig().getString("MySQL.ip");
        	int porta = Main.plugin.getConfig().getInt("MySQL.porta");
        	String usuario = Main.plugin.getConfig().getString("MySQL.usuario");
        	String senha = Main.plugin.getConfig().getString("MySQL.senha");
        	String database = Main.plugin.getConfig().getString("MySQL.database");
        	c = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + porta + "/" + database, usuario, senha);
        }
        return c;
    }
    
    public void insert(final String value, final String player) throws SQLException {
        final Connection c = this.getNewConnection();
        final Statement stmt = c.createStatement();
        stmt.execute("INSERT INTO " + table + " (player, money) VALUES ('" + player + "','" + value + "');");
        c.close();
        stmt.close();
    }
    
    public void update(final String value, final String player) throws SQLException {
        final Connection c = this.getNewConnection();
        final Statement stmt = c.createStatement();
        stmt.executeUpdate("UPDATE " + table + " SET money='" + value + "' WHERE player='" + player + "'");
        c.close();
        stmt.close();
    }
}
