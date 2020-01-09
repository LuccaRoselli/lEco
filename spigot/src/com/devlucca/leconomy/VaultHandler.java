package com.devlucca.leconomy;

import net.milkbowl.vault.economy.*;
import org.bukkit.*;

import com.devlucca.leconomy.utils.*;

import java.sql.*;
import java.util.*;

public class VaultHandler implements Economy
{
    public EconomyResponse bankBalance(final String arg0) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public EconomyResponse bankDeposit(final String arg0, final double arg1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public EconomyResponse bankHas(final String arg0, final double arg1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public EconomyResponse bankWithdraw(final String arg0, final double arg1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public EconomyResponse createBank(final String arg0, final String arg1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public EconomyResponse createBank(final String arg0, final OfflinePlayer arg1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public boolean createPlayerAccount(final String arg0) {
        boolean sucess = false;
        try {
            MoneyData.createAccount(arg0);
            sucess = true;
        }
        catch (SQLException ex) {}
        return sucess;
    }
    
    public boolean createPlayerAccount(final OfflinePlayer arg0) {
        boolean sucess = false;
        try {
            MoneyData.createAccount(arg0.getName());
            sucess = true;
        }
        catch (SQLException ex) {}
        return sucess;
    }
    
    public boolean createPlayerAccount(final String player, final String arg1) {
        boolean sucess = false;
        try {
            MoneyData.createAccount(player);
            sucess = true;
        }
        catch (SQLException ex) {}
        return sucess;
    }
    
    public boolean createPlayerAccount(final OfflinePlayer arg0, final String arg1) {
        boolean sucess = false;
        try {
            MoneyData.createAccount(arg0.getName());
            sucess = true;
        }
        catch (SQLException ex) {}
        return sucess;
    }
    
    public String currencyNamePlural() {
        return "Money";
    }
    
    public String currencyNameSingular() {
        return "Money";
    }
    
    public EconomyResponse deleteBank(final String arg0) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public EconomyResponse depositPlayer(final String player, final double valor) {
        if (valor <= 0.0) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Valor negativo");
        }
        final PMoney p = Main.plugin.getPMoney(player);
        if (p == null) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Conta inexistente");
        }
        p.setMoney(p.getMoney() + valor);
        return new EconomyResponse(0.0, p.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }
    
    public EconomyResponse depositPlayer(final OfflinePlayer player, final double valor) {
        return this.depositPlayer(player.getName(), valor);
    }
    
    public EconomyResponse depositPlayer(final String arg0, final String arg1, final double arg2) {
        return this.depositPlayer(arg0, arg2);
    }
    
    public EconomyResponse depositPlayer(final OfflinePlayer player, final String arg1, final double valor) {
        return this.depositPlayer(player.getName(), valor);
    }
    
    public String format(final double valor) {
        return Main.plugin.format(new Double(valor).longValue());
    }
    
    public int fractionalDigits() {
        return -1;
    }
    
    public double getBalance(final String player) {
        if (Main.plugin.getPMoney(player) == null) {
            return 0.0;
        }
        return Main.plugin.getPMoney(player).getMoney();
    }
    
    public double getBalance(final OfflinePlayer player) {
        return this.getBalance(player.getName());
    }
    
    public double getBalance(final String player, final String arg1) {
        return this.getBalance(player);
    }
    
    public double getBalance(final OfflinePlayer player, final String arg1) {
        return this.getBalance(player.getName());
    }
    
    public List<String> getBanks() {
        return new ArrayList<String>();
    }
    
    public String getName() {
        return "SmartEconomy";
    }
    
    public boolean has(final String player, final double valor) {
        final PMoney p = Main.plugin.getPMoney(player);
        return p != null && valor >= 0.0 && p.getMoney() >= valor;
    }
    
    public boolean has(final OfflinePlayer player, final double valor) {
        return this.has(player.getName(), valor);
    }
    
    public boolean has(final String player, final String arg1, final double valor) {
        return this.has(player, valor);
    }
    
    public boolean has(final OfflinePlayer player, final String arg1, final double valor) {
        return this.has(player.getName(), valor);
    }
    
    public boolean hasAccount(final String player) {
        return Main.plugin.getPMoney(player) != null;
    }
    
    public boolean hasAccount(final OfflinePlayer arg0) {
        return this.hasAccount(arg0.getName());
    }
    
    public boolean hasAccount(final String arg0, final String arg1) {
        return this.hasAccount(arg0);
    }
    
    public boolean hasAccount(final OfflinePlayer arg0, final String arg1) {
        return this.hasAccount(arg0.getName());
    }
    
    public boolean hasBankSupport() {
        return false;
    }
    
    public EconomyResponse isBankMember(final String arg0, final String arg1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public EconomyResponse isBankMember(final String arg0, final OfflinePlayer arg1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public EconomyResponse isBankOwner(final String arg0, final String arg1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public EconomyResponse isBankOwner(final String arg0, final OfflinePlayer arg1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Este plugin n\u00e3o possui suporte para este tipo de acao.");
    }
    
    public boolean isEnabled() {
        return Main.plugin.isEnabled();
    }
    
    public EconomyResponse withdrawPlayer(final String player, final double valor) {
        final PMoney p = Main.plugin.getPMoney(player);
        if (p == null) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Conta inexistente");
        }
        if (valor < 0.0) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Nagative value");
        }
        p.setMoney(p.getMoney() - valor);
        return new EconomyResponse(0.0, p.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }
    
    public EconomyResponse withdrawPlayer(final OfflinePlayer player, final double valor) {
        return this.withdrawPlayer(player.getName(), valor);
    }
    
    public EconomyResponse withdrawPlayer(final String player, final String arg1, final double valor) {
        return this.withdrawPlayer(player, valor);
    }
    
    public EconomyResponse withdrawPlayer(final OfflinePlayer player, final String arg1, final double valor) {
        return this.withdrawPlayer(player.getName(), valor);
    }
}
