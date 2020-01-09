package com.devlucca.leconomy;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.devlucca.leconomy.comandos.Money;
import com.devlucca.leconomy.listeners.PlayerJoin;
import com.devlucca.leconomy.placeholders.Normal;
import com.devlucca.leconomy.utils.Formater;
import com.devlucca.leconomy.utils.MoneyData;
import com.devlucca.leconomy.utils.SQL;
import com.devlucca.leconomy.utils.Save;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	public static Main plugin;
	public HashMap<String, PMoney> accounts;
	public HashMap<PMoney, Double> value_modific;
	public static SQL database;
	public static List<PMoney> tops;
	public static HashMap<String, Double> tops_money;
	private Economy vault;
	public static boolean ligou = false;

	static {
		Main.tops = new ArrayList<PMoney>();
		Main.tops_money = new HashMap<String, Double>();
	}

	public Main() {
		this.accounts = new HashMap<String, PMoney>();
		this.value_modific = new HashMap<PMoney, Double>();
	}

	public Economy getEconomy() {
		return this.vault;
	}

	@SuppressWarnings("deprecation")
	public void onEnable() {
		Main.plugin = this;
		this.saveDefaultConfig();
		ligou = true;
		if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
			this.debug("§fO plugin est\u00e1 sendo desabilitado...");
			this.debug("§fO plugin 'Vault' n\u00e3o foi encontrado.");
			this.getServer().getPluginManager().disablePlugin((Plugin) this);
			return;
		}
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new Normal(this).hook();
			this.debug("Placeholder ativo (PlaceholderAPI).");
		}
		Main.database = new SQL();
		this.debug("Carregando contas...");
		MoneyData.loadAccounts();
		this.getCommand("money").setExecutor((CommandExecutor) new Money());
		this.getServer().getPluginManager().registerEvents((Listener) new PlayerJoin(), (Plugin) this);
		this.debug("lEconomy habilitado. v" + this.getDescription().getVersion());
		final Save save = new Save();
		final Thread th = new Thread(save);
		th.start();
		this.setupVault();
	}

	public void onDisable() {
		if (ligou) {
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
						stmt.executeUpdate("UPDATE " + SQL.table + " SET money='" + pm.getMoney() + "' WHERE player='"
								+ pm.getPlayer() + "'");
						Main.plugin.value_modific.put(pm, pm.getMoney());
						++i;
					} else {
						Main.plugin.value_modific.put(pm, pm.getMoney());
					}
				}
				c.close();
				stmt.close();
				Main.plugin.debug("Foram salvas " + i + " contas, desabilitando plugin.");
			} catch (SQLException e) {
				e.printStackTrace();
				Main.plugin.debug("Falha ao salvar as contas.");
			}
		}
	}

	public void debug(final String msg) {
		Bukkit.getConsoleSender().sendMessage("§6[DEBUG - lEconomy] §f" + msg);
	}

	public PMoney getPMoney(final String player) {
		if (this.accounts.containsKey(player)) {
			return this.accounts.get(player);
		}
		return null;
	}

	public String format(final double value) {
		return Formater.format(new Double(value).longValue());
	}

	public List<PMoney> getTop() {
		final List<PMoney> moneys = new ArrayList<PMoney>();
		for (final String a : this.accounts.keySet()) {
			moneys.add(this.accounts.get(a));
		}
		List<PMoney> convert = new ArrayList<PMoney>();
		for (final PMoney pms : moneys) {
			convert.add(pms);
			Main.tops_money.put(pms.getPlayer(), pms.getMoney());
		}
		Collections.sort(convert, new Comparator<PMoney>() {
			@Override
			public int compare(final PMoney pt1, final PMoney pt2) {
				final Float f1 = (float) pt1.getMoney();
				final Float f2 = (float) pt2.getMoney();
				return f2.compareTo(f1);
			}
		});
		if (convert.size() > 5) {
			convert = convert.subList(0, 5);
		}
		return convert;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setupVault() {
		this.vault = (Economy) new VaultHandler();
		this.getServer().getServicesManager().register((Class) Economy.class, (Object) this.vault, (Plugin) this,
				ServicePriority.Highest);
		this.debug("Vault suport ativado.");
	}
}
