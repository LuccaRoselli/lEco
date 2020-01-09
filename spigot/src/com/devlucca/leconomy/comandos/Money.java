package com.devlucca.leconomy.comandos;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.devlucca.leconomy.Main;
import com.devlucca.leconomy.PMoney;
import com.devlucca.leconomy.utils.Formater;
import com.devlucca.leconomy.utils.StringManager;

public class Money implements CommandExecutor
{
    @SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender sender, final Command arg1, final String label, final String[] args) {
        Player p = null;
        if (sender instanceof Player) {
            p = (Player)sender;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("delete")) {
                if (p != null && !p.hasPermission("leconomy.admin")) {
                    p.sendMessage(StringManager.getPrefix() + "§cVoc\u00ea n\u00e3o tem permiss\u00e3o.");
                    return true;
                }
                final String player = args[1];
                if (Main.plugin.getPMoney(player) != null) {
                    final PMoney pm = Main.plugin.getPMoney(player);
                    pm.setMoney(0.0);
                    sender.sendMessage(StringManager.getPrefix() + "§7Conta de §f" + pm.getPlayer() + "§7 resetada.");
                }
                else {
                    sender.sendMessage(StringManager.getPrefix() + "§7Conta inexistente.");
                }
                return true;
            }
            else {
                sender.sendMessage(StringManager.getPrefix() + "§7Argumento inv\u00e1lido, utilize '/money help'");
            }
        }
        if (args.length == 0) {
            if (p != null) {
                final PMoney pm2 = Main.plugin.getPMoney(p.getName());
                if (pm2 == null) {
                    p.sendMessage(StringManager.getPrefix() + "§cVoc\u00ea n\u00e3o possui uma conta.");
                }
                else {
                    p.sendMessage(StringManager.getPrefix() + "§7Seu saldo é §2$§f" + Main.plugin.format(Main.plugin.getEconomy().getBalance((OfflinePlayer)p)) + " §7(§2$§f" + Formater.formatGerman(Main.plugin.getEconomy().getBalance((OfflinePlayer)p)) + "§7).");
                }
            }
            else {
                this.sendHelp(sender);
            }
            return true;
        }
        if (args.length != 1) {
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (p != null && !p.hasPermission("leconomy.admin")) {
                        p.sendMessage(StringManager.getPrefix() + "§cVoc\u00ea n\u00e3o tem permiss\u00e3o.");
                        return true;
                    }
                    final String player = args[1];
                    if (Main.plugin.getPMoney(player) != null) {
                        final PMoney pm = Main.plugin.getPMoney(player);
                        final String input = args[2];
                        String valor = "";
                        try {
                            if (input.matches(".*[a-zA-Z]+.*"))
                            	valor = Formater.deformat(input);
                            else
                            	valor = input;
						} catch (NumberFormatException e) {
							sender.sendMessage(StringManager.getPrefix() + "§cO valor do money deve ser um n\u00famero v\u00e1lido.");
							return true;
						}
                        if (this.isDouble(valor)) {
                            pm.setMoney(pm.getMoney() + Double.valueOf(valor));
                            sender.sendMessage(StringManager.getPrefix() + "§7Voc\u00ea givou §2$§f" + Main.plugin.format(Double.valueOf(valor)) + "§7 para §f" + pm.getPlayer() + "§7.");
                        }
                        else {
                            sender.sendMessage(StringManager.getPrefix() + "§cO valor do money deve ser um n\u00famero v\u00e1lido.");
                        }
                    }
                    else {
                        sender.sendMessage(StringManager.getPrefix() + "§cJogador inv\u00e1lido" + "§7.");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("take")) {
                    if (p != null && !p.hasPermission("leconomy.admin")) {
                        p.sendMessage(StringManager.getPrefix() + "§cVoc\u00ea n\u00e3o tem permiss\u00e3o.");
                        return true;
                    }
                    final String player = args[1];
                    if (Main.plugin.getPMoney(player) != null) {
                        final PMoney pm = Main.plugin.getPMoney(player);
                        final String input = args[2];
                        String valor = "";
                        try {
                            if (input.matches(".*[a-zA-Z]+.*"))
                            	valor = Formater.deformat(input);
                            else
                            	valor = input;
						} catch (NumberFormatException e) {
							sender.sendMessage(StringManager.getPrefix() + "§cO valor do money deve ser um n\u00famero v\u00e1lido.");
							return true;
						}
                        if (this.isDouble(valor)) {
                        	if ((pm.getMoney() - Double.valueOf(valor)) > 0)
                        		pm.setMoney(pm.getMoney() - Double.valueOf(valor));
                        	else
                        		pm.setMoney(0);
                            sender.sendMessage(StringManager.getPrefix() + "§7Voc\u00ea retirou §2$§f" + Main.plugin.format(Double.valueOf(valor)) + "§7 de §f" + pm.getPlayer() + "§7.");
                        }
                        else {
                            sender.sendMessage(StringManager.getPrefix() + "§cO valor do money deve ser um n\u00famero v\u00e1lido.");
                        }
                    }
                    else {
                        sender.sendMessage(StringManager.getPrefix() + "§cJogador inv\u00e1lido");
                    }
                    return true;
                }
                else if (args[0].equalsIgnoreCase("set")) {
                    if (p != null && !p.hasPermission("leconomy.admin")) {
                        p.sendMessage(StringManager.getPrefix() + "§cVoc\u00ea n\u00e3o tem permiss\u00e3o.");
                        return true;
                    }
                    final String player = args[1];
                    if (Main.plugin.getPMoney(player) != null) {
                        final PMoney pm = Main.plugin.getPMoney(player);
                        final String input = args[2];
                        String valor = "";
                        try {
                            if (input.matches(".*[a-zA-Z]+.*"))
                            	valor = Formater.deformat(input);
                            else
                            	valor = input;
						} catch (NumberFormatException e) {
							sender.sendMessage(StringManager.getPrefix() + "§cO valor do money deve ser um n\u00famero v\u00e1lido.");
							return true;
						}
                        if (this.isDouble(valor)) {
                            pm.setMoney(Double.valueOf(valor));
                            sender.sendMessage(StringManager.getPrefix() + "§7Voc\u00ea setou §2$§f" + Main.plugin.format(Double.valueOf(valor)) + "§7 para §f" + pm.getPlayer() + "§7.");
                        }
                        else {
                            sender.sendMessage(StringManager.getPrefix() + "§cO valor do money deve ser um n\u00famero v\u00e1lido.");
                        }
                    }
                    else {
                        sender.sendMessage(StringManager.getPrefix() + "§cJogador inv\u00e1lido");
                    }
                    return true;
                }
                else {
                    if (args[0].equalsIgnoreCase("enviar") || args[0].equalsIgnoreCase("pay")) {
                        if (p != null) {
                        	if (args.length == 1){
                        		p.sendMessage(StringManager.getPrefix() + "§cUse: /money pay <nick> <valor>.");
                        		return true;
                        	}
                            final String player = args[1];
                            if (player.equalsIgnoreCase(p.getName())) {
                                p.sendMessage(StringManager.getPrefix() + "§cVoc\u00ea n\u00e3o pode enviar money para si mesmo.");
                                return true;
                            }
                            final String input = args[2];
                            String valor2 = "";
                            try {
                                if (input.matches(".*[a-zA-Z]+.*"))
                                	valor2 = Formater.deformat(input);
                                else
                                	valor2 = input;
    						} catch (NumberFormatException e) {
    							sender.sendMessage(StringManager.getPrefix() + "§cO valor do money deve ser um n\u00famero v\u00e1lido.");
    							return true;
    						}
                            final Player o = Bukkit.getPlayer(player);
                            if (o == null) {
                                p.sendMessage(StringManager.getPrefix() + "§cJogador n\u00e3o encontrado.");
                                return true;
                            }
                            final PMoney pm3 = Main.plugin.getPMoney(p.getName());
                            final PMoney om = Main.plugin.getPMoney(player);
                            if (pm3 == null || om == null) {
                                p.sendMessage(StringManager.getPrefix() + "§cOcorreu um problema.");
                            }
                            else if (this.isDouble(valor2)) {
                                final double value = Double.valueOf(valor2);
                                if (pm3.getMoney() >= value) {
                                    pm3.setMoney(pm3.getMoney() - value);
                                    om.setMoney(om.getMoney() + value);
                                    p.sendMessage(StringManager.getPrefix() + "§7Voc\u00ea enviou §2$§f" + Main.plugin.format(value) + "§7 para §f" + om.getPlayer() + "§7.");
                                    o.sendMessage(StringManager.getPrefix() + "§7Voc\u00ea recebeu §2$§f" + Main.plugin.format(value) + "§7 de §f" + pm3.getPlayer() + "§7.");
                                }
                                else {
                                    p.sendMessage(StringManager.getPrefix() + "§cVoc\u00ea n\u00e3o possui §2$§f" + Main.plugin.format(value) + "§7 para enviar.");
                                }
                            }
                            else {
                                p.sendMessage(StringManager.getPrefix() + "§cO valor a ser enviado deve ser um n\u00famero maior que §f0" + "§7.");
                            }
                        }
                        else {
                            sender.sendMessage(StringManager.getPrefix() + "§cSem suporte.");
                        }
                        return true;
                    }
                    sender.sendMessage(StringManager.getPrefix() + "§cArgumento inv\u00e1lido, utilize '/money help'");
                }
            }
            sender.sendMessage(StringManager.getPrefix() + "§cArgumento inv\u00e1lido, utilize '/money help'");
            return false;
        }
        if (args[0].equalsIgnoreCase("help")) {
            if (p == null) {
                this.sendHelp(sender);
            }
            else if (!p.hasPermission("leconomy.admin")) {
                this.sendPHelp(p);
            }
            else {
                this.sendHelp(sender);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("top")) {
            sender.sendMessage("");
            sender.sendMessage("§a§lRANK DOS JOGADORES MAIS RICOS");
            sender.sendMessage("§7Atualiza a cada 5 minutos");
            sender.sendMessage("");
            final List<PMoney> pm4 = Main.tops;
            for (int a = 0; a < pm4.size(); ++a) {
                final PMoney b = pm4.get(a);
                sender.sendMessage("§f" + (a + 1) + ". §7" + b.getPlayer() + "§8 - §2$§f" + Main.plugin.format(Main.tops_money.get(b.getPlayer())) + "§7.");
            }
            sender.sendMessage("");
            return true;
        }
        final String player = args[0];
        boolean achou = false;
        for (final String pm5 : Main.plugin.accounts.keySet()) {
            if (pm5.equalsIgnoreCase(player)) {
                final PMoney mb = Main.plugin.getPMoney(pm5);
                sender.sendMessage(StringManager.getPrefix() + "§7Saldo de §f" + pm5 + "§7: §2$§f" + Main.plugin.format(mb.getMoney()) + " §7(§2$§f" + Formater.formatGerman(Main.plugin.getEconomy().getBalance(pm5)) + "§7).");
                achou = true;
                break;
            }
        }
        if (!achou) {
            sender.sendMessage(StringManager.getPrefix() + "§cConta n\u00e3o encontrada.");
        }
        return true;
    }
    
    public void sendHelp(final CommandSender sender) {
        sender.sendMessage("§a§lECONOMIA - COMANDOS");
        sender.sendMessage("§f /money help §7§o: Mostrar os comandos");
        sender.sendMessage("§f /money <player> §7§o: Mostrar o saldo de algu\u00e9m");
        sender.sendMessage("§f /money give <player> <quantidade> §7§o: Givar money para um jogador");
        sender.sendMessage("§f /money delete <player> §7§o: Zerar a conta de algum jogador");
        sender.sendMessage("§f /money set <jogador> <quantidade> §7§o: Setar o saldo de algu\u00e9m");
        sender.sendMessage("§f /money enviar <player> <quantidade> §7§o: Enviar uma quantia de money para algu\u00e9m");
    }
    
    public void sendPHelp(final Player p) {
        p.sendMessage("§5§lECONOMIA - COMANDOS");
        p.sendMessage(" §f/money help §7§o: Mostrar os comandos");
        p.sendMessage(" §f/money <player> §7§o: Mostrar o saldo de algu\u00e9m");
        p.sendMessage(" §f/money enviar <player> <quantidade> §7§o: Enviar uma quantia de money para algu\u00e9m");
    }
    
    public ChatColor getColor(int i){
    	if (i == 1)
    		return ChatColor.DARK_GREEN;
    	else if (i == 2)
    		return ChatColor.GREEN;
    	else if (i == 3)
    		return ChatColor.DARK_AQUA;
    	else
    		return ChatColor.GRAY;
    				
    }
    
    public boolean isDouble(final String valor) {
        try {
            final double d = Double.valueOf(valor);
            if (d <= 0.0) {
                return false;
            }
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
